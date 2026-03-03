# Migration Guide from v7 or earlier to v8

## Breaking Changes

### Metadata values must be strings

**Why**: The GoCardless API only accepts string values for metadata, but the Java types previously allowed `Map<String, Object>`. This caused confusing runtime errors. The new version fixes the types to match the API requirements.

**Impact**: Code that passes non-string values to `metadata` fields will fail to compile.

---

## Quick Migration

### Option 1: Use MetadataHelper (Recommended) ✅

The easiest way to migrate is using the new `MetadataHelper` class:

```java
import com.gocardless.helpers.MetadataHelper;
import java.util.HashMap;
import java.util.Map;

// ❌ BEFORE - Compiled but failed at runtime
Map<String, Object> metadata = new HashMap<>();
metadata.put("user_id", 12345);           // Integer
metadata.put("is_active", true);          // Boolean
metadata.put("tags", Arrays.asList("vip", "premium"));  // List

client.customers().create()
    .withEmail("user@example.com")
    .withMetadata(metadata)  // Runtime error!
    .execute();

// ✅ AFTER - One function call
client.customers().create()
    .withEmail("user@example.com")
    .withMetadata(MetadataHelper.toMetadata(metadata))  // Converts all values
    .execute();
```

### Option 2: Manual Conversion

If you prefer explicit control:

```java
Map<String, String> metadata = new HashMap<>();
metadata.put("user_id", String.valueOf(12345));              // "12345"
metadata.put("is_active", String.valueOf(true));             // "true"

Gson gson = new Gson();
metadata.put("tags", gson.toJson(Arrays.asList("vip", "premium")));  // JSON

client.customers().create()
    .withEmail("user@example.com")
    .withMetadata(metadata)
    .execute();
```

---

## Helper Functions Reference

### `MetadataHelper.toMetadata(obj)`

Converts a map with mixed value types to metadata format:

```java
Map<String, Object> data = new HashMap<>();
data.put("user_id", 12345);
data.put("is_premium", true);
data.put("signup_date", new Date());
data.put("preferences", Map.of("theme", "dark"));

Map<String, String> metadata = MetadataHelper.toMetadata(data);
// Result: {
//   "user_id": "12345",
//   "is_premium": "true",
//   "signup_date": "Mon Jan 15 10:30:00 GMT 2024",
//   "preferences": "{\"theme\":\"dark\"}"
// }
```

### `MetadataHelper.toMetadataValue(value)`

Converts a single value:

```java
MetadataHelper.toMetadataValue(12345);                      // "12345"
MetadataHelper.toMetadataValue(true);                       // "true"
MetadataHelper.toMetadataValue(Arrays.asList("vip"));       // "[\"vip\"]"
MetadataHelper.toMetadataValue(Map.of("theme", "dark"));    // "{\"theme\":\"dark\"}"
```

### `MetadataHelper.isValidMetadata(obj)`

Check if metadata is valid:

```java
Map<String, Object> metadata = new HashMap<>();
metadata.put("key", "value");

if (MetadataHelper.isValidMetadata(metadata)) {
    // All values are strings
    client.customers().create()
        .withMetadata((Map<String, String>) (Object) metadata)
        .execute();
}
```

### `MetadataHelper.parseMetadataValue(value, type)`

Parse metadata values back to their original types:

```java
Customer customer = client.customers().get("CU123").execute();
Map<String, String> metadata = customer.getMetadata();

// Parse back to original types
Integer userId = MetadataHelper.parseMetadataValue(
    metadata.get("user_id"),
    Integer.class
);  // 12345

Boolean isActive = MetadataHelper.parseMetadataValue(
    metadata.get("is_active"),
    Boolean.class
);  // true

// For generic types, use TypeToken
List<String> tags = MetadataHelper.parseMetadataValue(
    metadata.get("tags"),
    new TypeToken<List<String>>(){}.getType()
);  // ["vip", "premium"]
```
