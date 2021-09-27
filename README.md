# Java Client for GoCardless Pro API

This library provides a simple wrapper around the [GoCardless API](http://developer.gocardless.com/api-reference).

- ["Getting started" guide](https://developer.gocardless.com/getting-started/api/introduction/?lang=java) with copy and paste Java code samples
- [API Reference](https://developer.gocardless.com/api-reference/2015-07-06)
- [Example application](https://github.com/gocardless/gocardless-pro-java-example)

## Getting started

With Maven:

```xml
<dependency>
    <groupId>com.gocardless</groupId>
    <artifactId>gocardless-pro</artifactId>
    <version>5.0.1</version>
</dependency>
```

With Gradle:

```
compile 'com.gocardless:gocardless-pro:5.0.1'
```

## Initializing the client

The client is initialised with an access token, and is configured to use GoCardless' live environment by default:

```java
import com.gocardless.GoCardlessClient;

String accessToken = "AO00000123";

GoCardlessClient client = GoCardlessClient.newBuilder(accessToken).build();
```

Optionally, the client can be customised with an environment, base URL, proxy and/or SSL socket factory
by calling `.withX` methods on the `Builder`:

```java
import com.gocardless.GoCardlessClient;
import java.net.InetSocketAddress;
import java.net.Proxy;

String accessToken = "AO00000123";
Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 8080));

GoCardlessClient client = GoCardlessClient.newBuilder(accessToken)
    .withEnvironment(GoCardlessClient.Environment.SANDBOX)
    .withProxy(proxy)
    .build();
```

To see the configurable options in full, see the documentation for `GoCardlessClient.Builder`.

If you're upgrading from v2.x, you'll need to update your code for initialising `GoCardlessClient`. See the
"Upgrading from v2.x to v3.x or above" section below.

## Examples

### Fetching resources

To fetch a single item, use the `get` method:

```java
Mandate mandate = client.mandates().get("MD123").execute();
System.out.println(mandate.getReference());
```

### Listing resources

To fetch items in a collection, there are two options:

* Fetching items one page at a time:

```java
ListResponse<Customer> firstPage = client.customers().list().execute();
for (Customer customer : firstPage.getItems()) {
    System.out.println(customer.getGivenName());
}

String cursor = firstPage.getAfter();
ListResponse<Customer> nextPage = client.customers().list().withAfter(cursor).execute();
```

* Iterating through all of the items in a collection:

```java
for (Customer customer : client.customers().all().execute()) {
    System.out.println(customer.getGivenName());
}
```

### Creating resources

Resources can be created with the `create` method:

```java
Creditor creditor = client.creditors().create()
    .withName("The Wine Club")
    .withAddressLine1("9 Acer Gardens")
    .withCity("Birmingham")
    .withPostalCode("B4 7NJ")
    .withCountryCode("GB")
    .execute();
```

### Updating resources

Resources can be updates with the `update` method:

```java
Subscription subscription = client.subscriptions().update("SU123")
    .withName("New name")
    .execute();
```

### Retrying requests

The library will attempt to retry most failing requests automatically (with the exception
of those which are not safe to retry).

`GET` requests are considered safe to retry, and will be retried automatically. Certain
`POST` requests are made safe to retry by the use of an idempotency key, generated
automatically by the library, so we'll automatically retry these too.

If you want to override this behaviour
(for example, to provide your own retry mechanism), then you can use the `executeWrapped`
method in place of `execute`.  This returns an `ApiResponse` object, which also gives
access to the response status code and headers.

### Setting custom headers

You shouldn't generally need to customise the headers sent by the library, but you wish to
in some cases (for example if you want to send an `Accept-Language` header when
[creating a mandate PDF](https://developer.gocardless.com/api-reference/#mandate-pdfs-create-a-mandate-pdf)).

To do this, the library's request objects have a
`withHeader(String headerName, String headerValue)` method which you can chain before you
call `execute()` to actually make the request:

```java
MandatePdf mandatePdf = client.mandatePdfs.create()
  .withIban("FR14BARC20000055779911")
  .withHeader("Accept-Language", "fr-FR")
  .execute();
```

Custom headers you specify will override any headers generated by the library itself (for
example, an `Authorization` header with your configured access token or an
`Idempotency-Key` header with a randomly-generated value or one you've configured
manually). Custom headers always take precedence.

When you use the `execute()` method to create a resource, if your idempotency key has
already been used, we'll automatically fetch the already-created resource from the API
using the same headers, and will return it to you.

### Handling errors

Any errors will result in a `GoCardlessException` being thrown.  If the error is due to an error response from the API, then an appropriate subclass of `GoCardlessApiException` will be thrown, providing more information about the nature of the error.  This will be one of:

* GoCardlessInternalException
* InvalidApiUsageException
* InvalidStateException
* ValidationFailedException

See the [documentation](http://gocardless.github.io/gocardless-pro-java/com/gocardless/errors/package-summary.html) for more details.

### Handling webhooks

GoCardless supports webhooks, allowing you to receive real-time notifications when things happen in your account, so you can take automatic actions in response, for example:

* When a customer cancels their mandate with the bank, suspend their club membership
* When a payment fails due to lack of funds, mark their invoice as unpaid
* When a customer’s subscription generates a new payment, log it in their “past payments” list

The client allows you to validate that a webhook you receive is genuinely from GoCardless, and to parse it into `com.gocardless.resources.Event` objects which are easy to work with:

```java
package myintegration;

// Use the POM file at
// https://raw.githubusercontent.com/gocardless/gocardless-pro-java-maven-example/master/pom.xml

import java.util.List;
import com.gocardless.Webhook;
import com.gocardless.errors.InvalidSignatureException;
import com.gocardless.resources.Event;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.http.ResponseEntity;

public class WebhookHandler {
    @PostMapping("/")
    public ResponseEntity<String> handlePost(
            @RequestHeader("Webhook-Signature") String signatureHeader,
            @RequestBody String requestBody) {
        /*
         * When you create a webhook endpoint, you can specify a secret. When GoCardless
         * sends you a webhook, it'll sign the body using that secret. Since only you and
         * GoCardless know the secret, you can check the signature and ensure that the
         * webhook is truly from GoCardless.
         *
         * We recommend storing your webhook endpoint secret in an environment variable
         * for security, but you could include it as a string directly in your code.
         */
        String webhookEndpointSecret = System.getenv("GOCARDLESS_WEBHOOK_ENDPOINT_SECRET");

        try {
            List<Event> events = Webhook.parse(requestBody, signatureHeader, webhookEndpointSecret);

            // Work through your list of events...

            return new ResponseEntity<String>("OK", HttpStatus.OK);
        } catch(InvalidSignatureException e) {
            return new ResponseEntity<String>("Incorrect Signature", HttpStatus.BAD_REQUEST);
        }
    }
}
```

For more details on working with webhooks, see our ["Getting started" guide](https://developer.gocardless.com/getting-started/api/introduction/?lang=java).

## Upgrading from v4.x to v5.x or above

### Breaking Changes

- Stop support for apps using Java 7, supports apps using only Java 8 and above
- Response Header names are all in lower case now
- GoCardless client initiation method `withsslSocketFactory` replaced with `withSslSocketFactoryAndTrustManager`

```java
import com.gocardless.GoCardlessClient;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import java.security.KeyStore;

String accessToken = "AO00000123";
TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
TrustManagerFactory.getDefaultAlgorithm());
trustManagerFactory.init((KeyStore) null);
TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
    throw new IllegalStateException("Unexpected default trust managers:"
        + Arrays.toString(trustManagers));
}
X509TrustManager trustManager = (X509TrustManager) trustManagers[0];

SSLContext sslContext = SSLContext.getInstance("TLS");
sslContext.init(null, new TrustManager[] { trustManager }, null);
SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();


GoCardlessClient client = GoCardlessClient.newBuilder(accessToken)
    .withSslSocketFactoryAndTrustManager(sslSocketFactory, trustManager)
    .build();
```

## Upgrading from v2.x to v3.x or above

To upgrade from v2.x, you will need to switch from calling `GoCardlessClient.create` with arguments to
using the `Builder` returned by `GoCardlessClient.newBuilder()` and its `withX()` and `build()` methods.

If you're only setting an access token and using the default GoCardless environment (live):

```java
import com.gocardless.GoCardlessClient;

String accessToken = "foo";

// Before
GoCardlessClient client = GoCardlessClient.create(accessToken)

// After
GoCardlessClient client = GoCardlessClient.newBuilder(accessToken).build();
```

If you're customising the environment as well:

```java
import com.gocardless.GoCardlessClient;

String accessToken = "foo";

// Before
GoCardlessClient client = GoCardlessClient.create(accessToken, GoCardlessClient.Environment.SANDBOX)

// After
GoCardlessClient client = GoCardlessClient.newBuilder(accessToken)
    .withEnvironment(GoCardlessClient.Environment.SANDBOX)
    .build();
```

Or, if you're customising the base URL:

```java
import com.gocardless.GoCardlessClient;

String accessToken = "foo";
String baseUrl = "https://api.gocardless.com";

// Before
GoCardlessClient client = GoCardlessClient.create(accessToken, baseUrl)

// After
GoCardlessClient client = GoCardlessClient.newBuilder(accessToken)
    .withBaseUrl(baseUrl)
    .build();
```

If you were instantiating your own `com.gocardless.http.HttpClient` (which is very unlikely unless you
were patching the internals of the library), you'll now need to supply an `OkHttpClient` as well as the
access token and base URL.

```java
String accessToken = "foo";
String baseUrl = "https://api.gocardless.com";

// Before
HttpClient rawClient = new HttpClient(accessToken, baseUrl);

// After
HttpClient rawClient = new HttpClient(accessToken, baseUrl, new OkHttpClient());
```

Once you've instantiated the client, everything works exactly the same as before, so you won't need to
change any of your other code.

## Compatibility

This library requires JDK version 8 or above.

## Logging

All requests are logged at `INFO` level using [SLF4J](http://www.slf4j.org/).  Logs will only be sent if you have an SLF4J binding on your classpath - we recommend using [Logback](http://logback.qos.ch/).

## Documentation

Full Javadoc can be found [here](http://gocardless.github.io/gocardless-pro-java/com/gocardless/package-summary.html).

## Contributing

This client is auto-generated from Crank, a toolchain that we hope to soon open source. Issues should for now be reported on this repository.  __Please do not modify the source code yourself, your changes will be overridden!__
