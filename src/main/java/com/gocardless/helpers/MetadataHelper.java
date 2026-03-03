package com.gocardless.helpers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.HashMap;
import java.util.Map;

/**
 * Helper utilities for working with metadata fields.
 *
 * <p>
 * The GoCardless API requires metadata values to be strings. These helpers make it easy to convert
 * other types to strings.
 *
 * @since 7.0.0
 */
public class MetadataHelper {
    private static final Gson GSON = new GsonBuilder().create();

    /**
     * Converts any value to a string suitable for metadata.
     *
     * <p>
     * Examples:
     * 
     * <pre>
     * toMetadataValue(12345)                  // "12345"
     * toMetadataValue(true)                   // "true"
     * toMetadataValue(Arrays.asList("a","b")) // "[\"a\",\"b\"]"
     * </pre>
     *
     * @param value the value to convert
     * @return the string representation of the value
     */
    public static String toMetadataValue(Object value) {
        if (value == null) {
            return "null";
        }
        if (value instanceof String) {
            return (String) value;
        }
        if (value instanceof Number || value instanceof Boolean) {
            return value.toString();
        }
        // For complex types (Collections, Maps, custom objects), serialize to JSON
        return GSON.toJson(value);
    }

    /**
     * Converts a map with mixed value types to a metadata-compatible format.
     *
     * <p>
     * This is useful for converting {@code Map<String, Object>} to {@code Map<String, String>}
     * where all values are automatically converted to strings.
     *
     * <p>
     * Example:
     * 
     * <pre>
     * Map&lt;String, Object&gt; data = new HashMap&lt;&gt;();
     * data.put("user_id", 12345);
     * data.put("is_active", true);
     * data.put("tags", Arrays.asList("vip", "premium"));
     * Map&lt;String, String&gt; metadata = MetadataHelper.toMetadata(data);
     * // Result: {
     * // "user_id": "12345",
     * // "is_active": "true",
     * // "tags": "[\"vip\",\"premium\"]"
     * // }
     * </pre>
     *
     * @param obj the map to convert
     * @return a map with all values converted to strings
     */
    public static Map<String, String> toMetadata(Map<String, Object> obj) {
        Map<String, String> result = new HashMap<>(obj.size());
        for (Map.Entry<String, Object> entry : obj.entrySet()) {
            result.put(entry.getKey(), toMetadataValue(entry.getValue()));
        }
        return result;
    }

    /**
     * Checks if a map contains only string values.
     *
     * <p>
     * This can be used to verify that metadata is in the correct format before sending it to the
     * API.
     *
     * @param obj the map to check
     * @return true if all values are strings, false otherwise
     */
    public static boolean isValidMetadata(Map<String, Object> obj) {
        return obj.values().stream().allMatch(String.class::isInstance);
    }

    /**
     * Parses a metadata string value back to a specific type.
     *
     * <p>
     * Examples:
     * 
     * <pre>
     * Integer userId = MetadataHelper.parseMetadataValue("12345", Integer.class); // 12345
     * Boolean isActive = MetadataHelper.parseMetadataValue("true", Boolean.class); // true
     * List&lt;String&gt; tags = MetadataHelper.parseMetadataValue("[\"vip\"]",
     *         new TypeToken&lt;List&lt;String&gt;&gt;() {}.getType()); // ["vip"]
     * </pre>
     *
     * @param value the string value to parse
     * @param type the target type class
     * @param <T> the type to parse to
     * @return the parsed value
     * @throws com.google.gson.JsonSyntaxException if the value cannot be parsed
     */
    public static <T> T parseMetadataValue(String value, Class<T> type) {
        if (type == String.class) {
            return type.cast(value);
        }
        if (type == Integer.class) {
            return type.cast(Integer.valueOf(value));
        }
        if (type == Long.class) {
            return type.cast(Long.valueOf(value));
        }
        if (type == Double.class) {
            return type.cast(Double.valueOf(value));
        }
        if (type == Float.class) {
            return type.cast(Float.valueOf(value));
        }
        if (type == Boolean.class) {
            return type.cast(Boolean.valueOf(value));
        }
        // For complex types, parse from JSON
        return GSON.fromJson(value, type);
    }

    /**
     * Parses a metadata string value back to a specific type using a Gson TypeToken.
     *
     * <p>
     * This is useful for parsing generic types like {@code List<String>}.
     *
     * <p>
     * Example:
     * 
     * <pre>
     * List&lt;String&gt; tags = MetadataHelper.parseMetadataValue("[\"vip\",\"premium\"]",
     *         new TypeToken&lt;List&lt;String&gt;&gt;() {}.getType());
     * </pre>
     *
     * @param value the string value to parse
     * @param type the target type (use {@code new TypeToken<T>(){}.getType()})
     * @param <T> the type to parse to
     * @return the parsed value
     * @throws com.google.gson.JsonSyntaxException if the value cannot be parsed
     */
    public static <T> T parseMetadataValue(String value, java.lang.reflect.Type type) {
        if (type == String.class) {
            return (T) value;
        }
        return GSON.fromJson(value, type);
    }
}
