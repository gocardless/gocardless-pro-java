package com.gocardless.helpers;

import static org.junit.Assert.*;

import com.google.gson.reflect.TypeToken;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;

public class MetadataHelperTest {
    @Test
    public void testToMetadataValue_string() {
        assertEquals("hello", MetadataHelper.toMetadataValue("hello"));
        assertEquals("", MetadataHelper.toMetadataValue(""));
    }

    @Test
    public void testToMetadataValue_integers() {
        assertEquals("12345", MetadataHelper.toMetadataValue(12345));
        assertEquals("0", MetadataHelper.toMetadataValue(0));
        assertEquals("-42", MetadataHelper.toMetadataValue(-42));
        assertEquals("9223372036854775807", MetadataHelper.toMetadataValue(9223372036854775807L));
    }

    @Test
    public void testToMetadataValue_floats() {
        assertEquals("3.14", MetadataHelper.toMetadataValue(3.14f));
        assertEquals("3.14159", MetadataHelper.toMetadataValue(3.14159));
        assertEquals("0.0", MetadataHelper.toMetadataValue(0.0));
    }

    @Test
    public void testToMetadataValue_booleans() {
        assertEquals("true", MetadataHelper.toMetadataValue(true));
        assertEquals("false", MetadataHelper.toMetadataValue(false));
    }

    @Test
    public void testToMetadataValue_null() {
        assertEquals("null", MetadataHelper.toMetadataValue(null));
    }

    @Test
    public void testToMetadataValue_list() {
        List<String> list = Arrays.asList("vip", "premium");
        String result = MetadataHelper.toMetadataValue(list);
        assertEquals("[\"vip\",\"premium\"]", result);
    }

    @Test
    public void testToMetadataValue_emptyList() {
        List<String> list = Arrays.asList();
        String result = MetadataHelper.toMetadataValue(list);
        assertEquals("[]", result);
    }

    @Test
    public void testToMetadataValue_map() {
        Map<String, String> map = new HashMap<>();
        map.put("theme", "dark");
        String result = MetadataHelper.toMetadataValue(map);
        assertEquals("{\"theme\":\"dark\"}", result);
    }

    @Test
    public void testToMetadataValue_emptyMap() {
        Map<String, String> map = new HashMap<>();
        String result = MetadataHelper.toMetadataValue(map);
        assertEquals("{}", result);
    }

    @Test
    public void testToMetadata_mixedTypes() {
        Map<String, Object> input = new HashMap<>();
        input.put("user_id", 12345);
        input.put("is_active", true);
        input.put("name", "John");
        Map<String, String> result = MetadataHelper.toMetadata(input);
        assertEquals(3, result.size());
        assertEquals("12345", result.get("user_id"));
        assertEquals("true", result.get("is_active"));
        assertEquals("John", result.get("name"));
    }

    @Test
    public void testToMetadata_withArraysAndObjects() {
        Map<String, Object> input = new HashMap<>();
        input.put("tags", Arrays.asList("vip", "premium"));
        Map<String, String> prefs = new HashMap<>();
        prefs.put("theme", "dark");
        input.put("prefs", prefs);
        Map<String, String> result = MetadataHelper.toMetadata(input);
        assertEquals(2, result.size());
        assertEquals("[\"vip\",\"premium\"]", result.get("tags"));
        assertEquals("{\"theme\":\"dark\"}", result.get("prefs"));
    }

    @Test
    public void testToMetadata_emptyMap() {
        Map<String, Object> input = new HashMap<>();
        Map<String, String> result = MetadataHelper.toMetadata(input);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testToMetadata_alreadyStrings() {
        Map<String, Object> input = new HashMap<>();
        input.put("key1", "value1");
        input.put("key2", "value2");
        Map<String, String> result = MetadataHelper.toMetadata(input);
        assertEquals(2, result.size());
        assertEquals("value1", result.get("key1"));
        assertEquals("value2", result.get("key2"));
    }

    @Test
    public void testToMetadata_nullValues() {
        Map<String, Object> input = new HashMap<>();
        input.put("nullable", null);
        Map<String, String> result = MetadataHelper.toMetadata(input);
        assertEquals(1, result.size());
        assertEquals("null", result.get("nullable"));
    }

    @Test
    public void testIsValidMetadata_valid() {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("key1", "value1");
        metadata.put("key2", "value2");
        assertTrue(MetadataHelper.isValidMetadata(metadata));
    }

    @Test
    public void testIsValidMetadata_invalidWithInt() {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("key1", "value1");
        metadata.put("key2", 12345);
        assertFalse(MetadataHelper.isValidMetadata(metadata));
    }

    @Test
    public void testIsValidMetadata_invalidWithBoolean() {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("key1", "value1");
        metadata.put("key2", true);
        assertFalse(MetadataHelper.isValidMetadata(metadata));
    }

    @Test
    public void testIsValidMetadata_invalidWithMap() {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("key1", "value1");
        metadata.put("key2", new HashMap<>());
        assertFalse(MetadataHelper.isValidMetadata(metadata));
    }

    @Test
    public void testIsValidMetadata_emptyMap() {
        Map<String, Object> metadata = new HashMap<>();
        assertTrue(MetadataHelper.isValidMetadata(metadata));
    }

    @Test
    public void testParseMetadataValue_string() {
        String result = MetadataHelper.parseMetadataValue("hello", String.class);
        assertEquals("hello", result);
    }

    @Test
    public void testParseMetadataValue_integer() {
        Integer result = MetadataHelper.parseMetadataValue("12345", Integer.class);
        assertEquals(Integer.valueOf(12345), result);
    }

    @Test
    public void testParseMetadataValue_long() {
        Long result = MetadataHelper.parseMetadataValue("9223372036854775807", Long.class);
        assertEquals(Long.valueOf(9223372036854775807L), result);
    }

    @Test
    public void testParseMetadataValue_double() {
        Double result = MetadataHelper.parseMetadataValue("3.14", Double.class);
        assertEquals(Double.valueOf(3.14), result);
    }

    @Test
    public void testParseMetadataValue_float() {
        Float result = MetadataHelper.parseMetadataValue("3.14", Float.class);
        assertEquals(Float.valueOf(3.14f), result);
    }

    @Test
    public void testParseMetadataValue_boolean() {
        Boolean result = MetadataHelper.parseMetadataValue("true", Boolean.class);
        assertEquals(Boolean.TRUE, result);
        result = MetadataHelper.parseMetadataValue("false", Boolean.class);
        assertEquals(Boolean.FALSE, result);
    }

    @Test
    public void testParseMetadataValue_list() {
        List<String> result = MetadataHelper.parseMetadataValue("[\"vip\",\"premium\"]",
                new TypeToken<List<String>>() {}.getType());
        assertEquals(2, result.size());
        assertEquals("vip", result.get(0));
        assertEquals("premium", result.get(1));
    }

    @Test
    public void testParseMetadataValue_map() {
        Map<String, String> result = MetadataHelper.parseMetadataValue("{\"theme\":\"dark\"}",
                new TypeToken<Map<String, String>>() {}.getType());
        assertEquals(1, result.size());
        assertEquals("dark", result.get("theme"));
    }

    @Test(expected = NumberFormatException.class)
    public void testParseMetadataValue_invalidInt() {
        MetadataHelper.parseMetadataValue("not-a-number", Integer.class);
    }

    @Test
    public void testParseMetadataValue_invalidBooleanDefaultsFalse() {
        // Boolean.valueOf returns false for invalid strings
        Boolean result = MetadataHelper.parseMetadataValue("not-a-bool", Boolean.class);
        assertEquals(Boolean.FALSE, result);
    }
}
