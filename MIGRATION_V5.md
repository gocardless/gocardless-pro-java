# Upgrading from v4.x or earlier to v5.x or above

## Breaking Changes

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
