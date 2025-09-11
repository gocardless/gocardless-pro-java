package com.gocardless.http;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * This implementation is pretty much taken from Gson, except we default to Unknown enum
 * in case the non null value sent by server is not present in list of existing enums.
 *
 * Ref: https://github.com/google/gson/blob/ceae88bd6667f4263bbe02e6b3710b8a683906a2/gson/src/main/java/com/google/gson/internal/bind/TypeAdapters.java#L773-L806
 */
public class GCEnumTypeAdapterFactory implements TypeAdapterFactory {
    private static final String UNKNOWN = "unknown";

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        Class<T> rawType = (Class<T>) type.getRawType();
        if (!rawType.isEnum()) {
            return null;
        }

        final Map<String, T> nameToConstant = new HashMap<String, T>();
        final Map<T, String> constantToName = new HashMap<T, String>();

        try {
            for (T constant : rawType.getEnumConstants()) {
                String name = ((Enum)constant).name();
                SerializedName annotation = rawType.getField(name).getAnnotation(SerializedName.class);
                if(annotation != null) {
                    name = annotation.value();
                    for (String alternate : annotation.alternate()) {
                        nameToConstant.put(alternate, constant);
                    }
                } else {
                    name = constant.toString();
                }

                nameToConstant.put(name, constant);
                constantToName.put(constant, name);
            }
        } catch (NoSuchFieldException e) {
            throw new AssertionError(e);
        }

        return new TypeAdapter<T>() {
            @Override
            public void write(JsonWriter out, T value) throws IOException {
                out.value(value == null ? null : constantToName.get(value));
            }

            @Override
            public T read(JsonReader in) throws IOException {
                // if server sends null, show it as null
                if(in.peek() == JsonToken.NULL) {
                    in.nextNull();
                    return null;
                }

                /*
                    if server sends new string which is not in enum then give Unknown
                    else the default value which is a valid/existing enum
                */
                String input = in.nextString();
                if(!nameToConstant.containsKey(input)) {
                    return nameToConstant.get(UNKNOWN);
                } else {
                    return nameToConstant.get(input);
                }
            }
        };
    }
}