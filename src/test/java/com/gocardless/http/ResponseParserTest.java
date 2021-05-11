package com.gocardless.http;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import com.gocardless.errors.*;
import com.gocardless.http.HttpTestUtil.DummyItem;

import com.google.common.collect.ImmutableList;
import com.google.common.io.Resources;
import com.google.gson.reflect.TypeToken;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static com.gocardless.errors.ErrorType.*;

import static com.google.common.base.Charsets.UTF_8;

import static org.assertj.core.api.Assertions.assertThat;

public class ResponseParserTest {
    private ResponseParser parser;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() {
        parser = new ResponseParser(GsonFactory.build());
    }

    @Test
    public void shouldParseSingle() throws IOException {
        URL resource = Resources.getResource("fixtures/single.json");
        String responseBody = Resources.toString(resource, UTF_8);

        DummyItem result = parser.parseSingle(responseBody, "items", DummyItem.class);

        assertThat(result.stringField).isEqualTo("foo");
        assertThat(result.intField).isEqualTo(123);
    }

    @Test
    public void shouldParseMultiple() throws IOException {
        URL resource = Resources.getResource("fixtures/page.json");
        String responseBody = Resources.toString(resource, UTF_8);

        ImmutableList<DummyItem> result = parser.parseMultiple(responseBody, "items", new TypeToken<List<DummyItem>>() {});

        assertThat(result).hasSize(2);

        assertThat(result.get(0).stringField).isEqualTo("foo");
        assertThat(result.get(0).intField).isEqualTo(123);

        assertThat(result.get(1).stringField).isEqualTo("bar");
        assertThat(result.get(1).intField).isEqualTo(456);
    }

    @Test
    public void shouldParsePage() throws IOException {
        URL resource = Resources.getResource("fixtures/page.json");
        String responseBody = Resources.toString(resource, UTF_8);

        ListResponse<DummyItem> result = parser.parsePage(responseBody, "items", new TypeToken<List<DummyItem>>() {});

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
        URL resource = Resources.getResource("fixtures/invalid_api_usage.json");
        String responseBody = Resources.toString(resource, UTF_8);

        GoCardlessApiException exception = parser.parseError(responseBody);
        assertThat(exception).isInstanceOf(InvalidApiUsageException.class);
        assertThat(exception.getType()).isEqualTo(INVALID_API_USAGE);

        assertThat(exception.getMessage()).isEqualTo("Invalid document structure");
        assertThat(exception.getErrorMessage()).isEqualTo("Invalid document structure");
        assertThat(exception.getDocumentationUrl()).isEqualTo("https://developer.gocardless.com/pro#invalid_document_structure");
        assertThat(exception.getRequestId()).isEqualTo("bd271b37-a2f5-47c8-b461-040dfe0e9cb1");
        assertThat(exception.getCode()).isEqualTo(400);

        assertThat(exception.getErrors()).hasSize(1);

        ApiError error = exception.getErrors().get(0);
        assertThat(error.getMessage()).isEqualTo("Invalid document structure");
        assertThat(error.getReason()).isEqualTo("invalid_document_structure");
    }

    @Test
    public void shouldParseInvalidStateError() throws IOException {
        URL resource = Resources.getResource("fixtures/invalid_state.json");
        String responseBody = Resources.toString(resource, UTF_8);

        GoCardlessApiException exception = parser.parseError(responseBody);
        assertThat(exception).isInstanceOf(InvalidStateException.class);
        assertThat(exception.getType()).isEqualTo(INVALID_STATE);

        assertThat(exception.getMessage()).isEqualTo("Bank account already exists");
        assertThat(exception.getErrorMessage()).isEqualTo("Bank account already exists");
        assertThat(exception.getDocumentationUrl()).isEqualTo("https://developer.gocardless.com/pro#bank_account_exists");
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
        URL resource = Resources.getResource("fixtures/validation_failed.json");
        String responseBody = Resources.toString(resource, UTF_8);

        GoCardlessApiException exception = parser.parseError(responseBody);
        assertThat(exception).isInstanceOf(ValidationFailedException.class);
        assertThat(exception.getType()).isEqualTo(VALIDATION_FAILED);

        assertThat(exception.getMessage()).isEqualTo("branch_code must be a number, branch_code is the wrong length (should be 8 characters)");
        assertThat(exception.getErrorMessage()).isEqualTo("Validation failed");
        assertThat(exception.getDocumentationUrl()).isEqualTo("https://developer.gocardless.com/pro#validation_failed");
        assertThat(exception.getRequestId()).isEqualTo("dd50eaaf-8213-48fe-90d6-5466872efbc4");
        assertThat(exception.getCode()).isEqualTo(422);

        assertThat(exception.getErrors()).hasSize(2);

        ApiError error1 = exception.getErrors().get(0);
        assertThat(error1.getMessage()).isEqualTo("must be a number");
        assertThat(error1.getField()).isEqualTo("branch_code");
        assertThat(error1.getRequestPointer()).isEqualTo("/customer_bank_accounts/branch_code");

        ApiError error2 = exception.getErrors().get(1);
        assertThat(error2.getMessage()).isEqualTo("is the wrong length (should be 8 characters)");
        assertThat(error2.getField()).isEqualTo("branch_code");
        assertThat(error2.getRequestPointer()).isEqualTo("/customer_bank_accounts/branch_code");
    }

    @Test
    public void shouldParseInternalError() throws IOException {
        URL resource = Resources.getResource("fixtures/internal_error.json");
        String responseBody = Resources.toString(resource, UTF_8);

        GoCardlessApiException exception = parser.parseError(responseBody);
        assertThat(exception).isInstanceOf(GoCardlessInternalException.class);
        assertThat(exception.getType()).isEqualTo(GOCARDLESS);

        assertThat(exception.getMessage()).isEqualTo("THE BEES THEY'RE IN MY EYES");
        assertThat(exception.getErrorMessage()).isEqualTo("THE BEES THEY'RE IN MY EYES");
        assertThat(exception.getDocumentationUrl()).isEqualTo("https://developer.gocardless.com/pro#internal_error");
        assertThat(exception.getRequestId()).isEqualTo("41a59cf8-ca4c-474c-9931-7f01fb547bc7");
        assertThat(exception.getCode()).isEqualTo(500);

        assertThat(exception.getErrors()).isEmpty();
    }

    @Test
    public void shouldHandleNonJsonResponse() throws IOException {
        exception.expect(MalformedResponseException.class);

        URL resource = Resources.getResource("fixtures/non_json_response.html");
        String responseBody = Resources.toString(resource, UTF_8);

        parser.parseError(responseBody);
    }
}
