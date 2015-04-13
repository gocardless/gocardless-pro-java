package com.gocardless.pro.http;

import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static com.xebialabs.restito.builder.stub.StubHttp.whenHttp;
import static com.xebialabs.restito.semantics.Action.resourceContent;
import static com.xebialabs.restito.semantics.Action.status;
import static com.xebialabs.restito.semantics.Condition.get;
import static com.xebialabs.restito.semantics.Condition.parameter;
import static org.assertj.core.api.Assertions.assertThat;

public class ListRequestTest extends HttpRequestTest {
    @Test
    public void shouldPerformListRequest() throws IOException {
        whenHttp(server).match(get("/dummy"), parameter("id", "123")).then(
                status(HttpStatus.OK_200), resourceContent("fixtures/multiple.json"));
        DummyListRequest request = new DummyListRequest();
        List<DummyItem> result = request.execute();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).stringField).isEqualTo("foo");
        assertThat(result.get(0).intField).isEqualTo(123);
        assertThat(result.get(1).stringField).isEqualTo("bar");
        assertThat(result.get(1).intField).isEqualTo(456);
    }

    private class DummyListRequest extends ListRequest<DummyItem> {
        public DummyListRequest() {
            super(client);
        }

        @Override
        protected ImmutableMap<String, Object> getQueryParams() {
            return ImmutableMap.<String, Object>of("id", "123");
        }

        @Override
        protected String getPathTemplate() {
            return "/dummy";
        }

        @Override
        protected String getEnvelope() {
            return "items";
        }

        @Override
        protected TypeToken<List<DummyItem>> getTypeToken() {
            return new TypeToken<List<DummyItem>>() {};
        }
    }
}
