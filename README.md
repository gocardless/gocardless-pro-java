# Java Client for GoCardless Pro API

This library provides a simple wrapper around the [GoCardless Pro API](http://developer.gocardless.com/pro).

This client is still in beta and is subject to change. Until a stable major version is released you should expect breaking changes.

An example application using this library can be found [here](https://github.com/gocardless/gocardless-pro-java-example).

## Getting started

With Maven:

```xml
<dependency>
    <groupId>com.gocardless</groupId>
    <artifactId>gocardless-pro</artifactId>
    <version>0.0.4</version>
</dependency>
```

With Gradle:

```
compile 'com.gocardless:gocardless-pro:0.0.4'
```

## Initializing the client

The client is initialised with the API Key ID and the API Key `key` property.

```java
GoCardlessClient client = GoCardlessClient.create(apiKey, apiSecret);
```

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
for (Customer customer : client.customers().list().execute()) {
    System.out.println(customer.getGivenName());
}

String cursor = firstPage.getAfter();
ListResponse<Customer> nextPage = client.customers().list.withAfter(cursor).execute();
```

* Iterating through all of the items in a collection:

```java
for (Customer customer : client.customers().list()) {
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

### Handling errors

Any errors will result in a `GoCardlessException` being thrown.  If the error is due to an error response from the API, then an appropriate subclass of `GoCardlessApiException` will be thrown, providing more information about the nature of the error.  This will be one of:

* GoCardlessInternalException
* InvalidApiUsageException
* InvalidStateException
* ValidationFailedException

See the [documentation](http://gocardless.github.io/gocardless-pro-java/com/gocardless/errors/package-summary.html) for more details.

## Compatibility

This library requires JDK version 7 or above.

## Documentation

Full Javadoc can be found [here](http://gocardless.github.io/gocardless-pro-java/com/gocardless/package-summary.html).

## Contributing

This client is auto-generated from Crank, a toolchain that we hope to soon open source. Issues should for now be reported on this repository.  __Please do not modify the source code yourself, your changes will be overriden!__
