package com.gocardless.pro.services;

import java.util.List;
import java.util.Map;

import com.gocardless.pro.http.*;
import com.gocardless.pro.resources.CreditorBankAccount;

import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;

/**
 * Service class for working with creditor bank account resources.
 *
 * Creditor Bank Accounts hold the bank details of a
 * [creditor](https://developer.gocardless.com/pro/#api-endpoints-creditor). These are the bank
 * accounts which your [payouts](https://developer.gocardless.com/pro/#api-endpoints-payouts) will be
 * sent to.
 * 
 * Note that creditor bank accounts must be unique, and so you will encounter a
 * `bank_account_exists` error if you try to create a duplicate bank account. You may wish to handle
 * this by updating the existing record instead, the ID of which will be provided as
 * `links[creditor_bank_account]` in the error response.
 */
public class CreditorBankAccountService {
    private HttpClient httpClient;

    /**
     * Constructor.  Users of this library should have no need to call this - an instance
     * of this class can be obtained by calling
      {@link com.gocardless.pro.GoCardlessClient#creditorBankAccounts() }.
     */
    public CreditorBankAccountService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Creates a new creditor bank account object.
     * 
     * Bank account details may be supplied using the
     * IBAN (international bank account number) or [local details](#ui-compliance-local-bank-details).
     */
    public CreditorBankAccountCreateRequest create() {
        return new CreditorBankAccountCreateRequest(httpClient);
    }

    /**
     * Returns a [cursor-paginated](https://developer.gocardless.com/pro/#overview-cursor-pagination)
     * list of your creditor bank accounts.
     */
    public CreditorBankAccountListRequest list() {
        return new CreditorBankAccountListRequest(httpClient);
    }

    /**
     * Retrieves the details of an existing creditor bank account.
     */
    public CreditorBankAccountGetRequest get(String identity) {
        return new CreditorBankAccountGetRequest(httpClient, identity);
    }

    /**
     * Immediately disables the bank account, no money can be paid out to a disabled account.
     * 
     * This
     * will return a `disable_failed` error if the bank account has already been disabled.
     * 
     * A
     * disabled bank account can be re-enabled by creating a new bank account resource with the same
     * details.
     */
    public CreditorBankAccountDisableRequest disable(String identity) {
        return new CreditorBankAccountDisableRequest(httpClient, identity);
    }

    /**
     * Request class for {@link CreditorBankAccountService#create }.
     *
     * Creates a new creditor bank account object.
     * 
     * Bank account details may be supplied using the
     * IBAN (international bank account number) or [local details](#ui-compliance-local-bank-details).
     */
    public static final class CreditorBankAccountCreateRequest extends
            PostRequest<CreditorBankAccount> {
        private String accountHolderName;
        private String accountNumber;
        private String bankCode;
        private String branchCode;
        private String countryCode;
        private String currency;
        private String iban;
        private Links links;
        private Map<String, String> metadata;
        private Boolean setAsDefaultPayoutAccount;

        /**
         * Name of the account holder, as known by the bank. Usually this is the same as the name stored with
         * the linked [creditor](https://developer.gocardless.com/pro/#api-endpoints-creditors). This field
         * cannot exceed 18 characters.
         */
        public CreditorBankAccountCreateRequest withAccountHolderName(String accountHolderName) {
            this.accountHolderName = accountHolderName;
            return this;
        }

        /**
         * 8 digit, valid UK bank account number.
         */
        public CreditorBankAccountCreateRequest withAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }

        /**
         * Bank identifier code
         */
        public CreditorBankAccountCreateRequest withBankCode(String bankCode) {
            this.bankCode = bankCode;
            return this;
        }

        /**
         * Branch identifier code
         */
        public CreditorBankAccountCreateRequest withBranchCode(String branchCode) {
            this.branchCode = branchCode;
            return this;
        }

        /**
         * [ISO 3166-1](http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements)
         * alpha-2 code. Currently only GB is supported.
         */
        public CreditorBankAccountCreateRequest withCountryCode(String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        /**
         * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code, defaults to national
         * currency of `country_code`.
         */
        public CreditorBankAccountCreateRequest withCurrency(String currency) {
            this.currency = currency;
            return this;
        }

        /**
         * International Bank Account Number
         */
        public CreditorBankAccountCreateRequest withIban(String iban) {
            this.iban = iban;
            return this;
        }

        public CreditorBankAccountCreateRequest withLinks(Links links) {
            this.links = links;
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50 characters and
         * values up to 200 characters.
         */
        public CreditorBankAccountCreateRequest withMetadata(Map<String, String> metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * Defaults to `false`.
         */
        public CreditorBankAccountCreateRequest withSetAsDefaultPayoutAccount(
                Boolean setAsDefaultPayoutAccount) {
            this.setAsDefaultPayoutAccount = setAsDefaultPayoutAccount;
            return this;
        }

        private CreditorBankAccountCreateRequest(HttpClient httpClient) {
            super(httpClient);
        }

        @Override
        protected String getPathTemplate() {
            return "/creditor_bank_accounts";
        }

        @Override
        protected String getEnvelope() {
            return "creditor_bank_accounts";
        }

        @Override
        protected Class<CreditorBankAccount> getResponseClass() {
            return CreditorBankAccount.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }

        public static class Links {
            private String creditor;

            /**
             * ID of the [creditor](https://developer.gocardless.com/pro/#api-endpoints-creditors) that owns this
             * bank account.
             */
            public Links withCreditor(String creditor) {
                this.creditor = creditor;
                return this;
            }
        }
    }

    /**
     * Request class for {@link CreditorBankAccountService#list }.
     *
     * Returns a [cursor-paginated](https://developer.gocardless.com/pro/#overview-cursor-pagination)
     * list of your creditor bank accounts.
     */
    public static final class CreditorBankAccountListRequest extends
            ListRequest<CreditorBankAccount> {
        private String creditor;
        private Enabled enabled;

        /**
         * Cursor pointing to the start of the desired set.
         */
        public CreditorBankAccountListRequest withAfter(String after) {
            setAfter(after);
            return this;
        }

        /**
         * Cursor pointing to the end of the desired set.
         */
        public CreditorBankAccountListRequest withBefore(String before) {
            setBefore(before);
            return this;
        }

        /**
         * Unique identifier, beginning with "CR".
         */
        public CreditorBankAccountListRequest withCreditor(String creditor) {
            this.creditor = creditor;
            return this;
        }

        /**
         * Get enabled or disabled creditor bank accounts.
         */
        public CreditorBankAccountListRequest withEnabled(Enabled enabled) {
            this.enabled = enabled;
            return this;
        }

        /**
         * Number of records to return.
         */
        public CreditorBankAccountListRequest withLimit(Integer limit) {
            setLimit(limit);
            return this;
        }

        private CreditorBankAccountListRequest(HttpClient httpClient) {
            super(httpClient);
        }

        @Override
        protected Map<String, Object> getQueryParams() {
            ImmutableMap.Builder<String, Object> params = ImmutableMap.builder();
            params.putAll(super.getQueryParams());
            if (creditor != null) {
                params.put("creditor", creditor);
            }
            if (enabled != null) {
                params.put("enabled", enabled);
            }
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "/creditor_bank_accounts";
        }

        @Override
        protected String getEnvelope() {
            return "creditor_bank_accounts";
        }

        @Override
        protected TypeToken<List<CreditorBankAccount>> getTypeToken() {
            return new TypeToken<List<CreditorBankAccount>>() {};
        }

        public enum Enabled {
            TRUE, FALSE;
            @Override
            public String toString() {
                return name().toLowerCase();
            }
        }
    }

    /**
     * Request class for {@link CreditorBankAccountService#get }.
     *
     * Retrieves the details of an existing creditor bank account.
     */
    public static final class CreditorBankAccountGetRequest extends GetRequest<CreditorBankAccount> {
        @PathParam
        private final String identity;

        private CreditorBankAccountGetRequest(HttpClient httpClient, String identity) {
            super(httpClient);
            this.identity = identity;
        }

        @Override
        protected Map<String, String> getPathParams() {
            ImmutableMap.Builder<String, String> params = ImmutableMap.builder();
            params.put("identity", identity);
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "/creditor_bank_accounts/:identity";
        }

        @Override
        protected String getEnvelope() {
            return "creditor_bank_accounts";
        }

        @Override
        protected Class<CreditorBankAccount> getResponseClass() {
            return CreditorBankAccount.class;
        }
    }

    /**
     * Request class for {@link CreditorBankAccountService#disable }.
     *
     * Immediately disables the bank account, no money can be paid out to a disabled account.
     * 
     * This
     * will return a `disable_failed` error if the bank account has already been disabled.
     * 
     * A
     * disabled bank account can be re-enabled by creating a new bank account resource with the same
     * details.
     */
    public static final class CreditorBankAccountDisableRequest extends
            PostRequest<CreditorBankAccount> {
        @PathParam
        private final String identity;

        private CreditorBankAccountDisableRequest(HttpClient httpClient, String identity) {
            super(httpClient);
            this.identity = identity;
        }

        @Override
        protected Map<String, String> getPathParams() {
            ImmutableMap.Builder<String, String> params = ImmutableMap.builder();
            params.put("identity", identity);
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "/creditor_bank_accounts/:identity/actions/disable";
        }

        @Override
        protected String getEnvelope() {
            return "creditor_bank_accounts";
        }

        @Override
        protected Class<CreditorBankAccount> getResponseClass() {
            return CreditorBankAccount.class;
        }

        @Override
        protected boolean hasBody() {
            return false;
        }
    }
}
