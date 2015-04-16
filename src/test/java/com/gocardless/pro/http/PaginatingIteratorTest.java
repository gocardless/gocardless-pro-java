package com.gocardless.pro.http;

import java.util.Iterator;
import java.util.List;

import com.gocardless.pro.http.HttpTestUtil.DummyItem;
import com.gocardless.pro.http.ListRequestTest.DummyListRequest;

import com.google.common.collect.Lists;

import org.junit.Rule;
import org.junit.Test;

import static com.xebialabs.restito.builder.stub.StubHttp.whenHttp;
import static com.xebialabs.restito.semantics.Action.resourceContent;
import static com.xebialabs.restito.semantics.Action.status;
import static com.xebialabs.restito.semantics.Condition.get;
import static com.xebialabs.restito.semantics.Condition.parameter;

import static org.assertj.core.api.Assertions.assertThat;

import static org.glassfish.grizzly.http.util.HttpStatus.OK_200;

public class PaginatingIteratorTest {
    @Rule
    public MockHttp http = new MockHttp();

    @Test
    public void shouldIterateThroughPages() {
        whenHttp(http.server()).match(get("/dummy"), parameter("id", "123"),
                parameter("limit", "2")).then(status(OK_200),
                resourceContent("fixtures/first-page.json"));
        whenHttp(http.server()).match(get("/dummy"), parameter("id", "123"),
                parameter("limit", "2"), parameter("after", "ID123")).then(status(OK_200),
                resourceContent("fixtures/last-page.json"));
        DummyListRequest request = new DummyListRequest(http.client());
        request.setLimit(2);
        Iterator<DummyItem> iterator = new PaginatingIterator<>(request);
        List<DummyItem> items = Lists.newArrayList(iterator);
        assertThat(items).hasSize(3);
        assertThat(items.get(0).stringField).isEqualTo("foo");
        assertThat(items.get(0).intField).isEqualTo(111);
        assertThat(items.get(1).stringField).isEqualTo("bar");
        assertThat(items.get(1).intField).isEqualTo(222);
        assertThat(items.get(2).stringField).isEqualTo("baz");
        assertThat(items.get(2).intField).isEqualTo(333);
    }

    @Test
    public void shouldCopeWithEmptyLastPage() {
        whenHttp(http.server()).match(get("/dummy"), parameter("id", "123"),
                parameter("limit", "2")).then(status(OK_200),
                resourceContent("fixtures/first-page.json"));
        whenHttp(http.server()).match(get("/dummy"), parameter("id", "123"),
                parameter("limit", "2"), parameter("after", "ID123")).then(status(OK_200),
                resourceContent("fixtures/empty-page.json"));
        DummyListRequest request = new DummyListRequest(http.client());
        request.setLimit(2);
        Iterator<DummyItem> iterator = new PaginatingIterator<>(request);
        List<DummyItem> items = Lists.newArrayList(iterator);
        assertThat(items).hasSize(2);
        assertThat(items.get(0).stringField).isEqualTo("foo");
        assertThat(items.get(0).intField).isEqualTo(111);
        assertThat(items.get(1).stringField).isEqualTo("bar");
        assertThat(items.get(1).intField).isEqualTo(222);
    }
}
