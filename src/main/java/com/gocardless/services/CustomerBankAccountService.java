package com.gocardless.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gocardless.http.*;
import com.gocardless.resources.CustomerBankAccount;

import com.google.common.collect.ImmutableMap;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

/**
 * Service class for working with customer bank account resources.
 *
 * Customer Bank Accounts hold the bank details of a [customer](#core-endpoints-customers). They
 * always belong to a [customer](#core-endpoints-customers), and may be linked to several Direct
 * Debit [mandates](#core-endpoints-mandates).
 * 
 * Note that customer bank accounts must be unique,
 * and so you will encounter a `bank_account_exists` error if you try to create a duplicate bank
 * account. You may wish to handle this by updating the existing record instead, the ID of which will
 * be provided as links[customer_bank_account] in the error response.
 */
public class CustomerBankAccountService {
    private HttpClient httpClient;

    /**
     * Constructor.  Users of this library should have no need to call this - an instance
     * of this class can be obtained by calling
      {@link com.gocardless.GoCardlessClient#customerBankAccounts() }.
     */
    public CustomerBankAccountService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Creates a new customer bank account object.
     * 
     * There are three different ways to supply bank
     * account details:
     * 
     * - [Local details](#ui-local-bank-details)
     * 
     * - IBAN
     * 
     * - [Customer Bank
     * Account Tokens](#js-flow-create-a-customer-bank-account-token)
     * 
     * For more information on the
     * different fields required in each country, see [local bank details](#ui-local-bank-details).
     */
    public CustomerBankAccountCreateRequest create() {
        return new CustomerBankAccountCreateRequest(httpClient);
    }

    /**
     * Returns a [cursor-paginated](#overview-cursor-pagination) list of your bank accounts.
     */
    public CustomerBankAccountListRequest<ListResponse<CustomerBankAccount>> list() {
        return new CustomerBankAccountListRequest<>(httpClient,
                ListRequest.<CustomerBankAccount>pagingExecutor());
    }

    public CustomerBankAccountListRequest<Iterable<CustomerBankAccount>> all() {
        return new CustomerBankAccountListRequest<>(httpClient,
                ListRequest.<CustomerBankAccount>iteratingExecutor());
    }

    /**
     * Retrieves the details of an existing bank account.
     */
    public CustomerBankAccountGetRequest<CustomerBankAccount> get(String identity) {
        return new CustomerBankAccountGetRequest<>(httpClient,
                GetRequest.<CustomerBankAccount>jsonExecutor(), identity);
    }

    /**
     * Updates a customer bank account object. Only the metadata parameter is allowed.
     */
    public CustomerBankAccountUpdateRequest update(String identity) {
        return new CustomerBankAccountUpdateRequest(httpClient, identity);
    }

    /**
     * Immediately cancels all associated mandates and cancellable payments.
     * 
     * This will return a
     * `disable_failed` error if the bank account has already been disabled.
     * 
     * A disabled bank account
     * can be re-enabled by creating a new bank account resource with the same details.
     */
    public CustomerBankAccountDisableRequest disable(String identity) {
        return new CustomerBankAccountDisableRequest(httpClient, identity);
    }

    /**
     * Request class for {@link CustomerBankAccountService#create }.
     *
     * Creates a new customer bank account object.
     * 
     * There are three different ways to supply bank
     * account details:
     * 
     * - [Local details](#ui-local-bank-details)
     * 
     * - IBAN
     * 
     * - [Customer Bank
     * Account Tokens](#js-flow-create-a-customer-bank-account-token)
     * 
     * For more information on the
     * different fields required in each country, see [local bank details](#ui-local-bank-details).
     */
    public static final class CustomerBankAccountCreateRequest extends
            PostRequest<CustomerBankAccount> {
        private String accountHolderName;
        private String accountNumber;
        private String bankCode;
        private String branchCode;
        private String countryCode;
        private String currency;
        private String iban;
        private Links links;
        private Map<String, String> metadata;

        /**
         * Name of the account holder, as known by the bank. Usually this matches the name of the linked
         * [customer](#core-endpoints-customers). This field will be transliterated, upcased and truncated to
         * 18 characters.
         */
        public CustomerBankAccountCreateRequest withAccountHolderName(String accountHolderName) {
            this.accountHolderName = accountHolderName;
            return this;
        }

        /**
         * Bank account number - see [local details](#ui-local-bank-details) for more information.
         * Alternatively you can provide an `iban`.
         */
        public CustomerBankAccountCreateRequest withAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }

        /**
         * Bank code - see [local details](#ui-local-bank-details) for more information.  Alternatively you
         * can provide an `iban`.
         */
        public CustomerBankAccountCreateRequest withBankCode(String bankCode) {
            this.bankCode = bankCode;
            return this;
        }

        /**
         * Branch code - see [local details](#ui-local-bank-details) for more information. Alternatively you
         * can provide an `iban`.
         */
        public CustomerBankAccountCreateRequest withBranchCode(String branchCode) {
            this.branchCode = branchCode;
            return this;
        }

        /**
         * [ISO 3166-1](http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements)
         * alpha-2 code. Defaults to the country code of the `iban` if supplied, otherwise is required.
         */
        public CustomerBankAccountCreateRequest withCountryCode(String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        /**
         * [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217#Active_codes) currency code, defaults to national
         * currency of `country_code`.
         */
        public CustomerBankAccountCreateRequest withCurrency(String currency) {
            this.currency = currency;
            return this;
        }

        /**
         * International Bank Account Number. Alternatively you can provide [local
         * details](#ui-local-bank-details).
         */
        public CustomerBankAccountCreateRequest withIban(String iban) {
            this.iban = iban;
            return this;
        }

        public CustomerBankAccountCreateRequest withLinks(Links links) {
            this.links = links;
            return this;
        }

        /**
         * ID of the [customer](#core-endpoints-customers) that owns this bank account.
         */
        public CustomerBankAccountCreateRequest withLinksCustomer(String customer) {
            if (links == null) {
                links = new Links();
            }
            links.withCustomer(customer);
            return this;
        }

        /**
         * ID of a [customer bank account token](#core-endpoints-customer-bank-account-tokens) to use in
         * place of bank account parameters.
         */
        public CustomerBankAccountCreateRequest withLinksCustomerBankAccountToken(
                String customerBankAccountToken) {
            if (links == null) {
                links = new Links();
            }
            links.withCustomerBankAccountToken(customerBankAccountToken);
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50 characters and
         * values up to 200 characters.
         */
        public CustomerBankAccountCreateRequest withMetadata(Map<String, String> metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50 characters and
         * values up to 200 characters.
         */
        public CustomerBankAccountCreateRequest withMetadata(String key, String value) {
            if (metadata == null) {
                metadata = new HashMap<>();
            }
            metadata.put(key, value);
            return this;
        }

        private CustomerBankAccountCreateRequest(HttpClient httpClient) {
            super(httpClient);
        }

        @Override
        protected String getPathTemplate() {
            return "/customer_bank_accounts";
        }

        @Override
        protected String getEnvelope() {
            return "customer_bank_accounts";
        }

        @Override
        protected Class<CustomerBankAccount> getResponseClass() {
            return CustomerBankAccount.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }

        public static class Links {
            private String customer;
            private String customerBankAccountToken;

            /**
             * ID of the [customer](#core-endpoints-customers) that owns this bank account.
             */
            public Links withCustomer(String customer) {
                this.customer = customer;
                return this;
            }

            /**
             * ID of a [customer bank account token](#core-endpoints-customer-bank-account-tokens) to use in
             * place of bank account parameters.
             */
            public Links withCustomerBankAccountToken(String customerBankAccountToken) {
                this.customerBankAccountToken = customerBankAccountToken;
                return this;
            }
        }
    }

    /**
     * Request class for {@link CustomerBankAccountService#list }.
     *
     * Returns a [cursor-paginated](#overview-cursor-pagination) list of your bank accounts.
     */
    public static final class CustomerBankAccountListRequest<S> extends
            ListRequest<S, CustomerBankAccount> {
        private String customer;
        private Enabled enabled;

        /**
         * Cursor pointing to the start of the desired set.
         */
        public CustomerBankAccountListRequest<S> withAfter(String after) {
            setAfter(after);
            return this;
        }

        /**
         * Cursor pointing to the end of the desired set.
         */
        public CustomerBankAccountListRequest<S> withBefore(String before) {
            setBefore(before);
            return this;
        }

        /**
         * Unique identifier, beginning with "CU".
         */
        public CustomerBankAccountListRequest<S> withCustomer(String customer) {
            this.customer = customer;
            return this;
        }

        /**
         * Get enabled or disabled customer bank accounts.
         */
        public CustomerBankAccountListRequest<S> withEnabled(Enabled enabled) {
            this.enabled = enabled;
            return this;
        }

        /**
         * Number of records to return.
         */
        public CustomerBankAccountListRequest<S> withLimit(Integer limit) {
            setLimit(limit);
            return this;
        }

        private CustomerBankAccountListRequest(HttpClient httpClient,
                ListRequestExecutor<S, CustomerBankAccount> executor) {
            super(httpClient, executor);
        }

        @Override
        protected Map<String, Object> getQueryParams() {
            ImmutableMap.Builder<String, Object> params = ImmutableMap.builder();
            params.putAll(super.getQueryParams());
            if (customer != null) {
                params.put("customer", customer);
            }
            if (enabled != null) {
                params.put("enabled", enabled);
            }
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "/customer_bank_accounts";
        }

        @Override
        protected String getEnvelope() {
            return "customer_bank_accounts";
        }

        @Override
        protected TypeToken<List<CustomerBankAccount>> getTypeToken() {
            return new TypeToken<List<CustomerBankAccount>>() {};
        }

        public enum Enabled {
            @SerializedName("true")
            TRUE, @SerializedName("false")
            FALSE;
            @Override
            public String toString() {
                return name().toLowerCase();
            }
        }
    }

    /**
     * Request class for {@link CustomerBankAccountService#get }.
     *
     * Retrieves the details of an existing bank account.
     */
    public static final class CustomerBankAccountGetRequest<S> extends
            GetRequest<S, CustomerBankAccount> {
        @PathParam
        private final String identity;

        private CustomerBankAccountGetRequest(HttpClient httpClient,
                GetRequestExecutor<S, CustomerBankAccount> executor, String identity) {
            super(httpClient, executor);
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
            return "/customer_bank_accounts/:identity";
        }

        @Override
        protected String getEnvelope() {
            return "customer_bank_accounts";
        }

        @Override
        protected Class<CustomerBankAccount> getResponseClass() {
            return CustomerBankAccount.class;
        }
    }

    /**
     * Request class for {@link CustomerBankAccountService#update }.
     *
     * Updates a customer bank account object. Only the metadata parameter is allowed.
     */
    public static final class CustomerBankAccountUpdateRequest extends
            PutRequest<CustomerBankAccount> {
        @PathParam
        private final String identity;
        private Map<String, String> metadata;

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50 characters and
         * values up to 200 characters.
         */
        public CustomerBankAccountUpdateRequest withMetadata(Map<String, String> metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * Key-value store of custom data. Up to 3 keys are permitted, with key names up to 50 characters and
         * values up to 200 characters.
         */
        public CustomerBankAccountUpdateRequest withMetadata(String key, String value) {
            if (metadata == null) {
                metadata = new HashMap<>();
            }
            metadata.put(key, value);
            return this;
        }

        private CustomerBankAccountUpdateRequest(HttpClient httpClient, String identity) {
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
            return "/customer_bank_accounts/:identity";
        }

        @Override
        protected String getEnvelope() {
            return "customer_bank_accounts";
        }

        @Override
        protected Class<CustomerBankAccount> getResponseClass() {
            return CustomerBankAccount.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }
    }

    /**
     * Request class for {@link CustomerBankAccountService#disable }.
     *
     * Immediately cancels all associated mandates and cancellable payments.
     * 
     * This will return a
     * `disable_failed` error if the bank account has already been disabled.
     * 
     * A disabled bank account
     * can be re-enabled by creating a new bank account resource with the same details.
     */
    public static final class CustomerBankAccountDisableRequest extends
            PostRequest<CustomerBankAccount> {
        @PathParam
        private final String identity;

        private CustomerBankAccountDisableRequest(HttpClient httpClient, String identity) {
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
            return "/customer_bank_accounts/:identity/actions/disable";
        }

        @Override
        protected String getEnvelope() {
            return "customer_bank_accounts";
        }

        @Override
        protected Class<CustomerBankAccount> getResponseClass() {
            return CustomerBankAccount.class;
        }

        @Override
        protected boolean hasBody() {
            return false;
        }
    }
}
