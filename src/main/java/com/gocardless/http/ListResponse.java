package com.gocardless.http;

import java.util.List;

/**
 * Represents a page of items returned from the API.
 *
 * @param <T> the type of an item returned from the API.
 */
public class ListResponse<T> {
    private final List<T> items;
    private final Meta meta;

    ListResponse(List<T> items, Meta meta) {
        this.items = items;
        this.meta = meta;
    }

    /**
     * Returns the items on this page.
     */
    public List<T> getItems() {
        return items;
    }

    /**
     * Returns a cursor that can be used to get the page after this one. If null, then this is the
     * last page.
     */
    public String getAfter() {
        return meta.getCursors().getAfter();
    }

    /**
     * Returns a cursor that can be used to get the page before this one. If null, then this is the
     * first page.
     */
    public String getBefore() {
        return meta.getCursors().getBefore();
    }

    /**
     * Returns the upper bound placed on the number of items returned.
     */
    public int getLimit() {
        return meta.getLimit();
    }

    static class Meta {
        private final Cursors cursors;
        private final int limit;

        Meta(Cursors cursors, int limit) {
            this.cursors = cursors;
            this.limit = limit;
        }

        private Cursors getCursors() {
            return cursors;
        }

        private int getLimit() {
            return limit;
        }

        static class Cursors {
            private final String before;
            private final String after;

            Cursors(String before, String after) {
                this.before = before;
                this.after = after;
            }

            private String getBefore() {
                return before;
            }

            private String getAfter() {
                return after;
            }
        }
    }
}
