package com.gocardless.pro.http;

import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.util.List;

import com.gocardless.pro.TestUtil.DummyItem;
import com.gocardless.pro.exceptions.*;
import com.gocardless.pro.exceptions.ApiErrorResponse.ApiError;

import com.google.gson.reflect.TypeToken;

import org.junit.Before;
import org.junit.Test;

import static com.gocardless.pro.exceptions.ApiErrorResponse.ErrorType.*;

import static com.google.common.base.Charsets.UTF_8;
import static com.google.common.io.Resources.asCharSource;
import static com.google.common.io.Resources.getResource;

import static org.assertj.core.api.Assertions.assertThat;

public class ResponseParserTest {
    private ResponseParser parser;

    @Before
    public void setUp() {
        parser = new ResponseParser(GsonFactory.build());
    }

    @Test
    public void shouldParseSingle() throws IOException {
        URL resource = getResource("fixtures/single.json");
        Reader reader = asCharSource(resource, UTF_8).openStream();
        DummyItem result = parser.parseSingle(reader, "items", DummyItem.class);
        assertThat(result.stringField).isEqualTo("foo");
        assertThat(result.intField).isEqualTo(123);
    }

    @Test
    public void shouldParsePage() throws IOException {
        URL resource = getResource("fixtures/page.json");
        Reader reader = asCharSource(resource, UTF_8).openStream();
        ListResponse<DummyItem> result =
                parser.parsePage(reader, "items", new TypeToken<List<DummyItem>>() {});
        assertThat(result.getItems()).hasSize(2);
        assertThat(result.getItems().get(0).stringField).isEqualTo("foo");
        assertThat(result.getItems().get(0).intField).isEqualTo(123);
        assertThat(result.getItems().get(1).stringField).isEqualTo("bar");
        assertThat(result.getItems().get(1).intField).isEqualTo(456);
        assertThat(result.getAfter()).isEqualTo("ID123");
        assertThat(result.getBefore()).isEqualTo("ID456");
        assertThat(result.getLimit()).isEqualTo(50);
    }

    @Test
    public void shouldParseInvalidApiUsageError() throws IOException {
        URL resource = getResource("fixtures/invalid_api_usage.json");
        Reader reader = asCharSource(resource, UTF_8).openStream();
        GoCardlessApiException exception = parser.parseError(reader);
        assertThat(exception).isInstanceOf(InvalidApiUsageException.class);
        assertThat(exception.getType()).isEqualTo(INVALID_API_USAGE);
        assertThat(exception.getMessage()).isEqualTo("Invalid document structure");
        assertThat(exception.getDocumentationUrl()).isEqualTo(
                "https://developer.gocardless.com/pro#invalid_document_structure");
        assertThat(exception.getRequestId()).isEqualTo("bd271b37-a2f5-47c8-b461-040dfe0e9cb1");
        assertThat(exception.getCode()).isEqualTo(400);
        assertThat(exception.getErrors()).hasSize(1);
        ApiError error = exception.getErrors().get(0);
        assertThat(error.getMessage()).isEqualTo("Invalid document structure");
        assertThat(error.getReason()).isEqualTo("invalid_document_structure");
    }

    @Test
    public void shouldParseInvalidStateError() throws IOException {
        URL resource = getResource("fixtures/invalid_state.json");
        Reader reader = asCharSource(resource, UTF_8).openStream();
        GoCardlessApiException exception = parser.parseError(reader);
        assertThat(exception).isInstanceOf(InvalidStateException.class);
        assertThat(exception.getType()).isEqualTo(INVALID_STATE);
        assertThat(exception.getMessage()).isEqualTo("Bank account already exists");
        assertThat(exception.getDocumentationUrl()).isEqualTo(
                "https://developer.gocardless.com/pro#bank_account_exists");
        assertThat(exception.getRequestId()).isEqualTo("bd271b37-a2f5-47c8-b461-040dfe0e9cb1");
        assertThat(exception.getCode()).isEqualTo(409);
        assertThat(exception.getErrors()).hasSize(1);
        ApiError error = exception.getErrors().get(0);
        assertThat(error.getMessage()).isEqualTo("Bank account already exists");
        assertThat(error.getReason()).isEqualTo("bank_account_exists");
        assertThat(error.getLinks()).hasSize(1).containsEntry("creditor_bank_account", "BA123");
    }

    @Test
    public void shouldParseValidationFailedError() throws IOException {
        URL resource = getResource("fixtures/validation_failed.json");
        Reader reader = asCharSource(resource, UTF_8).openStream();
        GoCardlessApiException exception = parser.parseError(reader);
        assertThat(exception).isInstanceOf(ValidationFailedException.class);
        assertThat(exception.getType()).isEqualTo(VALIDATION_FAILED);
        assertThat(exception.getMessage()).isEqualTo("Validation failed");
        assertThat(exception.getDocumentationUrl()).isEqualTo(
                "https://developer.gocardless.com/pro#validation_failed");
        assertThat(exception.getRequestId()).isEqualTo("dd50eaaf-8213-48fe-90d6-5466872efbc4");
        assertThat(exception.getCode()).isEqualTo(422);
        assertThat(exception.getErrors()).hasSize(2);
        ApiError error1 = exception.getErrors().get(0);
        assertThat(error1.getMessage()).isEqualTo("must be a number");
        assertThat(error1.getField()).isEqualTo("branch_code");
        ApiError error2 = exception.getErrors().get(1);
        assertThat(error2.getMessage()).isEqualTo("is the wrong length (should be 8 characters)");
        assertThat(error2.getField()).isEqualTo("branch_code");
    }

    @Test
    public void shouldParseInternalError() throws IOException {
        URL resource = getResource("fixtures/internal_error.json");
        Reader reader = asCharSource(resource, UTF_8).openStream();
        GoCardlessApiException exception = parser.parseError(reader);
        assertThat(exception).isInstanceOf(GoCardlessInternalException.class);
        assertThat(exception.getType()).isEqualTo(GOCARDLESS);
        assertThat(exception.getMessage()).isEqualTo("THE BEES THEY'RE IN MY EYES");
        assertThat(exception.getDocumentationUrl()).isEqualTo(
                "https://developer.gocardless.com/pro#internal_error");
        assertThat(exception.getRequestId()).isEqualTo("41a59cf8-ca4c-474c-9931-7f01fb547bc7");
        assertThat(exception.getCode()).isEqualTo(500);
        assertThat(exception.getErrors()).isEmpty();
    }
}
