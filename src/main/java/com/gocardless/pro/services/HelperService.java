package com.gocardless.pro.services;

import java.util.Map;

import com.gocardless.pro.http.*;
import com.gocardless.pro.resources.Helper;

public class HelperService {
    private HttpClient httpClient;

    public HelperService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public HelperMandateRequest mandate() {
        return new HelperMandateRequest(httpClient);
    }

    public HelperModulusCheckRequest modulusCheck() {
        return new HelperModulusCheckRequest(httpClient);
    }

    public static final class HelperMandateRequest extends PostRequest<Helper> {
        private String accountHolderAddress;
        private String accountHolderName;
        private String accountNumber;
        private String bankCode;
        private String bic;
        private String branchCode;
        private String countryCode;
        private String iban;
        private Map<String, String> links;
        private String mandateReference;
        private String scheme;
        private String signedAt;
        private String sortCode;

        public HelperMandateRequest withAccountHolderAddress(String accountHolderAddress) {
            this.accountHolderAddress = accountHolderAddress;
            return this;
        }

        public HelperMandateRequest withAccountHolderName(String accountHolderName) {
            this.accountHolderName = accountHolderName;
            return this;
        }

        public HelperMandateRequest withAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }

        public HelperMandateRequest withBankCode(String bankCode) {
            this.bankCode = bankCode;
            return this;
        }

        public HelperMandateRequest withBic(String bic) {
            this.bic = bic;
            return this;
        }

        public HelperMandateRequest withBranchCode(String branchCode) {
            this.branchCode = branchCode;
            return this;
        }

        public HelperMandateRequest withCountryCode(String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        public HelperMandateRequest withIban(String iban) {
            this.iban = iban;
            return this;
        }

        public HelperMandateRequest withLinks(Map<String, String> links) {
            this.links = links;
            return this;
        }

        public HelperMandateRequest withMandateReference(String mandateReference) {
            this.mandateReference = mandateReference;
            return this;
        }

        public HelperMandateRequest withScheme(String scheme) {
            this.scheme = scheme;
            return this;
        }

        public HelperMandateRequest withSignedAt(String signedAt) {
            this.signedAt = signedAt;
            return this;
        }

        public HelperMandateRequest withSortCode(String sortCode) {
            this.sortCode = sortCode;
            return this;
        }

        private HelperMandateRequest(HttpClient httpClient) {
            super(httpClient);
        }

        @Override
        protected String getPathTemplate() {
            return "/helpers/mandate";
        }

        @Override
        protected String getEnvelope() {
            return "data";
        }

        @Override
        protected Class<Helper> getResponseClass() {
            return Helper.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }
    }

    public static final class HelperModulusCheckRequest extends PostRequest<Helper> {
        private String accountNumber;
        private String bankCode;
        private String branchCode;
        private String countryCode;
        private String iban;
        private String sortCode;

        public HelperModulusCheckRequest withAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }

        public HelperModulusCheckRequest withBankCode(String bankCode) {
            this.bankCode = bankCode;
            return this;
        }

        public HelperModulusCheckRequest withBranchCode(String branchCode) {
            this.branchCode = branchCode;
            return this;
        }

        public HelperModulusCheckRequest withCountryCode(String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        public HelperModulusCheckRequest withIban(String iban) {
            this.iban = iban;
            return this;
        }

        public HelperModulusCheckRequest withSortCode(String sortCode) {
            this.sortCode = sortCode;
            return this;
        }

        private HelperModulusCheckRequest(HttpClient httpClient) {
            super(httpClient);
        }

        @Override
        protected String getPathTemplate() {
            return "/helpers/modulus_check";
        }

        @Override
        protected String getEnvelope() {
            return "data";
        }

        @Override
        protected Class<Helper> getResponseClass() {
            return Helper.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }
    }
}
