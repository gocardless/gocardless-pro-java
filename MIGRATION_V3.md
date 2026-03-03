# Upgrading from v2.x to v3.x or above

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