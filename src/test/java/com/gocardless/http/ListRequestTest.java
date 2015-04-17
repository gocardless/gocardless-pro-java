package com.gocardless.http;

import java.util.List;

import com.gocardless.http.HttpTestUtil.DummyItem;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.gson.reflect.TypeToken;

import org.junit.Rule;
import org.junit.Test;

import static com.xebialabs.restito.builder.stub.StubHttp.whenHttp;
import static com.xebialabs.restito.semantics.Action.resourceContent;
import static com.xebialabs.restito.semantics.Action.status;
import static com.xebialabs.restito.semantics.Condition.get;
import static com.xebialabs.restito.semantics.Condition.parameter;

import static org.assertj.core.api.Assertions.assertThat;

import static org.glassfish.grizzly.http.util.HttpStatus.OK_200;

public class ListRequestTest {
    @Rule
    public MockHttp http = new MockHttp();

    @Test
    public void shouldPerformListRequest() {
        whenHttp(http.server()).match(get("/dummy"), parameter("id", "123")).then(status(OK_200),
                resourceContent("fixtures/page.json"));
        DummyListRequest request = new DummyListRequest(http.client());
        ListResponse<DummyItem> result = request.execute();
        assertThat(result.getItems()).hasSize(2);
        assertThat(result.getItems().get(0).stringField).isEqualTo("foo");
        assertThat(result.getItems().get(0).intField).isEqualTo(123);
        assertThat(result.getItems().get(1).stringField).isEqualTo("bar");
        assertThat(result.getItems().get(1).intField).isEqualTo(456);
    }

    @Test
    public void shouldBeAbleToIterateThroughList() {
        whenHttp(http.server()).match(get("/dummy"), parameter("id", "123")).then(status(OK_200),
                resourceContent("fixtures/first-page.json"));
        whenHttp(http.server()).match(get("/dummy"), parameter("id", "123"),
                parameter("after", "ID123")).then(status(OK_200),
                resourceContent("fixtures/last-page.json"));
        DummyListRequest request = new DummyListRequest(http.client());
        List<DummyItem> result = Lists.newArrayList(request);
        assertThat(result).hasSize(3);
        assertThat(result.get(0).stringField).isEqualTo("foo");
        assertThat(result.get(0).intField).isEqualTo(111);
        assertThat(result.get(1).stringField).isEqualTo("bar");
        assertThat(result.get(1).intField).isEqualTo(222);
        assertThat(result.get(2).stringField).isEqualTo("baz");
        assertThat(result.get(2).intField).isEqualTo(333);
    }

    static class DummyListRequest extends ListRequest<DummyItem> {
        public DummyListRequest(HttpClient client) {
            super(client);
        }

        @Override
        protected ImmutableMap<String, Object> getQueryParams() {
            return ImmutableMap.<String, Object>builder().putAll(super.getQueryParams())
                    .put("id", "123").build();
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
