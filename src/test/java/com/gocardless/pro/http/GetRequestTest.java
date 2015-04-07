package com.gocardless.pro.http;

import com.google.common.collect.ImmutableMap;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.junit.Test;

import java.io.IOException;

import static com.xebialabs.restito.builder.stub.StubHttp.whenHttp;
import static com.xebialabs.restito.semantics.Action.resourceContent;
import static com.xebialabs.restito.semantics.Action.status;
import static com.xebialabs.restito.semantics.Condition.get;
import static org.assertj.core.api.Assertions.assertThat;

public class GetRequestTest extends HttpRequestTest {
    @Test
    public void shouldPerformGetRequest() throws IOException {
        whenHttp(server).
                match(get("/dummy/123")).
                then(status(HttpStatus.OK_200), resourceContent("fixtures/single.json"));

        DummyGetRequest request = new DummyGetRequest();
        DummyItem result = request.execute();

        assertThat(result.stringField).isEqualTo("foo");
        assertThat(result.intField).isEqualTo(123);
    }

    private class DummyGetRequest extends GetRequest<DummyItem> {
        public DummyGetRequest() {
            super(client, "/dummy/:id", "items", DummyItem.class);
        }

        @Override
        protected ImmutableMap<String, String> getParams() {
            return ImmutableMap.of("id", "123");
        }
    }
}
