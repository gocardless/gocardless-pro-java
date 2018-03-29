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
    <version>3.0.0</version>
</dependency>
```

With Gradle:

```
compile 'com.gocardless:gocardless-pro:3.0.0'
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
"Upgrading from v2.x to v3.x" section below.

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

The library will attempt to retry most failing requests automatically (with the exception of some that are not safe to retry).  If you want to override this behaviour (for example, to provide your own retry mechanism), then you can use the `executeWrapped` method in place of `execute`.  This returns an `ApiResponse` object, which also gives access to the response status code and headers.

### Handling errors

Any errors will result in a `GoCardlessException` being thrown.  If the error is due to an error response from the API, then an appropriate subclass of `GoCardlessApiException` will be thrown, providing more information about the nature of the error.  This will be one of:

* GoCardlessInternalException
* InvalidApiUsageException
* InvalidStateException
* ValidationFailedException

See the [documentation](http://gocardless.github.io/gocardless-pro-java/com/gocardless/errors/package-summary.html) for more details.

## Upgrading from v2.x to v3.x

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

This library requires JDK version 7 or above.

## Logging

All requests are logged at `INFO` level using [SLF4J](http://www.slf4j.org/).  Logs will only be sent if you have an SLF4J binding on your classpath - we recommend using [Logback](http://logback.qos.ch/).

## Documentation

Full Javadoc can be found [here](http://gocardless.github.io/gocardless-pro-java/com/gocardless/package-summary.html).

## Contributing

This client is auto-generated from Crank, a toolchain that we hope to soon open source. Issues should for now be reported on this repository.  __Please do not modify the source code yourself, your changes will be overridden!__
