package com.gocardless.services;

import com.gocardless.http.*;
import com.gocardless.resources.BankDetailsLookup;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service class for working with bank details lookup resources.
 *
 * Look up the name and reachability of a bank account.
 */
public class BankDetailsLookupService {
    private final HttpClient httpClient;

    /**
     * Constructor. Users of this library should have no need to call this - an instance of this
     * class can be obtained by calling
     * {@link com.gocardless.GoCardlessClient#bankDetailsLookups() }.
     */
    public BankDetailsLookupService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Performs a bank details lookup. As part of the lookup, a modulus check and reachability check
     * are performed.
     * 
     * If your request returns an [error](#api-usage-errors) or the `available_debit_schemes`
     * attribute is an empty array, you will not be able to collect payments from the specified bank
     * account. GoCardless may be able to collect payments from an account even if no `bic` is
     * returned.
     * 
     * Bank account details may be supplied using [local details](#appendix-local-bank-details) or
     * an IBAN.
     * 
     * _Note:_ Usage of this endpoint is monitored. If your organisation relies on GoCardless for
     * modulus or reachability checking but not for payment collection, please get in touch.
     */
    public BankDetailsLookupCreateRequest create() {
        return new BankDetailsLookupCreateRequest(httpClient);
    }

    /**
     * Request class for {@link BankDetailsLookupService#create }.
     *
     * Performs a bank details lookup. As part of the lookup, a modulus check and reachability check
     * are performed.
     * 
     * If your request returns an [error](#api-usage-errors) or the `available_debit_schemes`
     * attribute is an empty array, you will not be able to collect payments from the specified bank
     * account. GoCardless may be able to collect payments from an account even if no `bic` is
     * returned.
     * 
     * Bank account details may be supplied using [local details](#appendix-local-bank-details) or
     * an IBAN.
     * 
     * _Note:_ Usage of this endpoint is monitored. If your organisation relies on GoCardless for
     * modulus or reachability checking but not for payment collection, please get in touch.
     */
    public static final class BankDetailsLookupCreateRequest
            extends PostRequest<BankDetailsLookup> {
        private String accountNumber;
        private String bankCode;
        private String branchCode;
        private String countryCode;
        private String iban;

        /**
         * Bank account number - see [local details](#appendix-local-bank-details) for more
         * information. Alternatively you can provide an `iban`.
         */
        public BankDetailsLookupCreateRequest withAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }

        /**
         * Bank code - see [local details](#appendix-local-bank-details) for more information.
         * Alternatively you can provide an `iban`.
         */
        public BankDetailsLookupCreateRequest withBankCode(String bankCode) {
            this.bankCode = bankCode;
            return this;
        }

        /**
         * Branch code - see [local details](#appendix-local-bank-details) for more information.
         * Alternatively you can provide an `iban`.
         */
        public BankDetailsLookupCreateRequest withBranchCode(String branchCode) {
            this.branchCode = branchCode;
            return this;
        }

        /**
         * [ISO
         * 3166-1](http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements)
         * alpha-2 code. Must be provided if specifying local details.
         */
        public BankDetailsLookupCreateRequest withCountryCode(String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        /**
         * International Bank Account Number. Alternatively you can provide [local
         * details](#appendix-local-bank-details).
         */
        public BankDetailsLookupCreateRequest withIban(String iban) {
            this.iban = iban;
            return this;
        }

        private BankDetailsLookupCreateRequest(HttpClient httpClient) {
            super(httpClient);
        }

        public BankDetailsLookupCreateRequest withHeader(String headerName, String headerValue) {
            this.addHeader(headerName, headerValue);
            return this;
        }

        @Override
        protected String getPathTemplate() {
            return "bank_details_lookups";
        }

        @Override
        protected String getEnvelope() {
            return "bank_details_lookups";
        }

        @Override
        protected Class<BankDetailsLookup> getResponseClass() {
            return BankDetailsLookup.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }
    }
}
