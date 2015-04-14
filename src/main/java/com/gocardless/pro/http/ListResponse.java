package com.gocardless.pro.http;

import java.util.List;

public class ListResponse<T> {
    private final List<T> items;
    private final Meta meta;

    ListResponse(List<T> items, Meta meta) {
        this.items = items;
        this.meta = meta;
    }

    public List<T> getItems() {
        return items;
    }

    public String getAfter() {
        return meta.getCursors().getAfter();
    }

    public String getBefore() {
        return meta.getCursors().getBefore();
    }

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
