package com.gocardless.pro.services;

import java.io.IOException;

import com.gocardless.pro.http.*;
import com.gocardless.pro.resources.Helper;

public class HelperService {
    private HttpClient httpClient;

    public HelperService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public HelperMandateRequest mandate() throws IOException {
        return new HelperMandateRequest(httpClient);
    }

    public HelperModulusCheckRequest modulusCheck() throws IOException {
        return new HelperModulusCheckRequest(httpClient);
    }

    public static final class HelperMandateRequest extends PostRequest<Helper> {
        private String accountHolderAddress;

        public HelperMandateRequest withAccountHolderAddress(String accountHolderAddress) {
            this.accountHolderAddress = accountHolderAddress;
            return this;
        }

        private String accountHolderName;

        public HelperMandateRequest withAccountHolderName(String accountHolderName) {
            this.accountHolderName = accountHolderName;
            return this;
        }

        private String accountNumber;

        public HelperMandateRequest withAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }

        private String bankCode;

        public HelperMandateRequest withBankCode(String bankCode) {
            this.bankCode = bankCode;
            return this;
        }

        private String bic;

        public HelperMandateRequest withBic(String bic) {
            this.bic = bic;
            return this;
        }

        private String branchCode;

        public HelperMandateRequest withBranchCode(String branchCode) {
            this.branchCode = branchCode;
            return this;
        }

        private String countryCode;

        public HelperMandateRequest withCountryCode(String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        private String iban;

        public HelperMandateRequest withIban(String iban) {
            this.iban = iban;
            return this;
        }

        private Object links;

        public HelperMandateRequest withLinks(Object links) {
            this.links = links;
            return this;
        }

        private String mandateReference;

        public HelperMandateRequest withMandateReference(String mandateReference) {
            this.mandateReference = mandateReference;
            return this;
        }

        private String scheme;

        public HelperMandateRequest withScheme(String scheme) {
            this.scheme = scheme;
            return this;
        }

        private String signedAt;

        public HelperMandateRequest withSignedAt(String signedAt) {
            this.signedAt = signedAt;
            return this;
        }

        private String sortCode;

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

        public HelperModulusCheckRequest withAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }

        private String bankCode;

        public HelperModulusCheckRequest withBankCode(String bankCode) {
            this.bankCode = bankCode;
            return this;
        }

        private String branchCode;

        public HelperModulusCheckRequest withBranchCode(String branchCode) {
            this.branchCode = branchCode;
            return this;
        }

        private String countryCode;

        public HelperModulusCheckRequest withCountryCode(String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        private String iban;

        public HelperModulusCheckRequest withIban(String iban) {
            this.iban = iban;
            return this;
        }

        private String sortCode;

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
