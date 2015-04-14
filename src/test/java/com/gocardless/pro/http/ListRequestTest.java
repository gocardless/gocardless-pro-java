package com.gocardless.pro.http;

import java.util.List;

import com.gocardless.pro.TestUtil.DummyItem;

import com.google.common.collect.ImmutableMap;
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
        DummyListRequest request = new DummyListRequest();
        ListResponse<DummyItem> result = request.execute();
        assertThat(result.getItems()).hasSize(2);
        assertThat(result.getItems().get(0).stringField).isEqualTo("foo");
        assertThat(result.getItems().get(0).intField).isEqualTo(123);
        assertThat(result.getItems().get(1).stringField).isEqualTo("bar");
        assertThat(result.getItems().get(1).intField).isEqualTo(456);
    }

    private class DummyListRequest extends ListRequest<DummyItem> {
        public DummyListRequest() {
            super(http.client());
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
