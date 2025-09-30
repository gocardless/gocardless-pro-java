package com.gocardless.http;

import com.gocardless.resources.*;
import java.util.List;

/**
 * Represents a page of items returned from the API.
 *
 * @param <T> the type of an item returned from the API.
 */
public class ListResponse<T> {
    private final List<T> items;
    private final Meta meta;
    private final Linked linked;

    ListResponse(List<T> items, Meta meta, Linked linked) {
        this.items = items;
        this.meta = meta;
        this.linked = linked;
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

    static class Linked {
        private final List<BillingRequest> billingRequests;

        public List<BillingRequest> getBillingRequests() {
            return billingRequests;
        }

        private final List<Creditor> creditors;

        public List<Creditor> getCreditors() {
            return creditors;
        }

        private final List<Customer> customers;

        public List<Customer> getCustomers() {
            return customers;
        }

        private final List<InstalmentSchedule> instalmentSchedules;

        public List<InstalmentSchedule> getInstalmentSchedules() {
            return instalmentSchedules;
        }

        private final List<Mandate> mandates;

        public List<Mandate> getMandates() {
            return mandates;
        }

        private final List<OutboundPayment> outboundPayments;

        public List<OutboundPayment> getOutboundPayments() {
            return outboundPayments;
        }

        private final List<PayerAuthorisation> payerAuthorisations;

        public List<PayerAuthorisation> getPayerAuthorisations() {
            return payerAuthorisations;
        }

        private final List<Payment> payments;

        public List<Payment> getPayments() {
            return payments;
        }

        private final List<Payout> payouts;

        public List<Payout> getPayouts() {
            return payouts;
        }

        private final List<Refund> refunds;

        public List<Refund> getRefunds() {
            return refunds;
        }

        private final List<SchemeIdentifier> schemeIdentifiers;

        public List<SchemeIdentifier> getSchemeIdentifiers() {
            return schemeIdentifiers;
        }

        private final List<Subscription> subscriptions;

        public List<Subscription> getSubscriptions() {
            return subscriptions;
        }

        Linked(List<InstalmentSchedule> instalmentSchedules, List<Customer> customers,
                List<OutboundPayment> outboundPayments, List<Payment> payments,
                List<Payout> payouts, List<Refund> refunds, List<BillingRequest> billingRequests,
                List<Creditor> creditors, List<Mandate> mandates,
                List<SchemeIdentifier> schemeIdentifiers,
                List<PayerAuthorisation> payerAuthorisations, List<Subscription> subscriptions) {
            this.billingRequests = billingRequests;
            this.creditors = creditors;
            this.customers = customers;
            this.instalmentSchedules = instalmentSchedules;
            this.mandates = mandates;
            this.outboundPayments = outboundPayments;
            this.payerAuthorisations = payerAuthorisations;
            this.payments = payments;
            this.payouts = payouts;
            this.refunds = refunds;
            this.schemeIdentifiers = schemeIdentifiers;
            this.subscriptions = subscriptions;
        }
    }
}
