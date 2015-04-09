package com.gocardless.pro.resources;

import java.util.List;

public class Mandate {



    private String createdAt;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }



    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    private Object links;

    public Object getLinks() {
        return links;
    }

    public void setLinks(Object links) {
        this.links = links;
    }



    private Object metadata;

    public Object getMetadata() {
        return metadata;
    }

    public void setMetadata(Object metadata) {
        this.metadata = metadata;
    }



    private String nextPossibleChargeDate;

    public String getNextPossibleChargeDate() {
        return nextPossibleChargeDate;
    }

    public void setNextPossibleChargeDate(String nextPossibleChargeDate) {
        this.nextPossibleChargeDate = nextPossibleChargeDate;
    }



    private String reference;

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }



    private String scheme;

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }



    public enum Status {

        PENDING_SUBMISSION,

        SUBMITTED,

        ACTIVE,

        FAILED,

        CANCELLED,

        EXPIRED,

    }

    private Status status;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


}
