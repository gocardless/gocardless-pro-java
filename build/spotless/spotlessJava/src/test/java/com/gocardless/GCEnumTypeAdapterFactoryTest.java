package com.gocardless;

import static org.assertj.core.api.Assertions.assertThat;

import com.gocardless.http.GCEnumTypeAdapterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import org.junit.Before;
import org.junit.Test;

public class GCEnumTypeAdapterFactoryTest {
    enum Scheme {
        @SerializedName("bacs")
        BACS, @SerializedName("becs")
        BECS, @SerializedName("mos")
        MY_OWN_SCHEME, @SerializedName("unknown")
        UNKNOWN, SCHEME_WITHOUT_ANNOTATION;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    GCEnumTypeAdapterFactory gcEnumTypeAdapterFactory = new GCEnumTypeAdapterFactory();
    Gson gson;

    @Before
    public void setUp() throws Exception {
        gson = new GsonBuilder().registerTypeAdapterFactory(gcEnumTypeAdapterFactory).create();
    }

    @Test
    public void toJson() throws Exception {
        assertThat(gson.toJson(Scheme.BACS)).isEqualTo("\"bacs\"");
    }

    @Test
    public void toJsonUnknown() throws Exception {
        assertThat(gson.toJson(Scheme.UNKNOWN)).isEqualTo("\"unknown\"");
    }

    @Test
    public void toJsonMyOwnScheme() throws Exception {
        assertThat(gson.toJson(Scheme.MY_OWN_SCHEME)).isEqualTo("\"mos\"");
    }

    @Test
    public void toJsonSchemeWithoutAnnotation() throws Exception {
        assertThat(gson.toJson(Scheme.SCHEME_WITHOUT_ANNOTATION))
                .isEqualTo("\"scheme_without_annotation\"");
    }

    @Test
    public void toJsonNull() throws Exception {
        assertThat(gson.toJson(null)).isEqualTo("null");
    }

    @Test
    public void fromJson() throws Exception {
        assertThat(gson.fromJson("becs", Scheme.class)).isEqualTo(Scheme.BECS);
    }

    @Test
    public void fromJsonUnknown() throws Exception {
        assertThat(gson.fromJson("autogiro", Scheme.class)).isEqualTo(Scheme.UNKNOWN);
    }

    @Test
    public void fromJsonNull() throws Exception {
        assertThat(gson.fromJson("null", Scheme.class)).isEqualTo(null);
    }

    @Test
    public void fromJsonMyOwnScheme() throws Exception {
        assertThat(gson.fromJson("mos", Scheme.class)).isEqualTo(Scheme.MY_OWN_SCHEME);
    }

    @Test
    public void fromJsonSchemeWithoutAnnotation() throws Exception {
        assertThat(gson.fromJson("scheme_without_annotation", Scheme.class))
                .isEqualTo(Scheme.SCHEME_WITHOUT_ANNOTATION);
    }
}
