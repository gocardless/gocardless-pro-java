package com.gocardless.resources;

/**
 * Represents a scenario simulator resource returned from the API.
 *
 * Scenario Simulators allow you to manually trigger and test certain paths that your
 * integration will encounter in the real world. These endpoints are only active in the
 * sandbox environment.
 */
public class ScenarioSimulator {
    private ScenarioSimulator() {
        // blank to prevent instantiation
    }

    private String id;

    /**
     * The unique identifier of the simulator, used to initiate simulations. One of:
     * <ul>
     * <li>`creditor_verification_status_action_required`: Sets a creditor's `verification status` to
     * `action required`, meaning that the creditor must provide further information to GoCardless in
     * order to verify their account to receive payouts.</li>
     * <li>`creditor_verification_status_in_review`: Sets a creditor's `verification status` to `in
     * review`, meaning that the creditor has provided all of the information requested by GoCardless to
     * verify their account, and is now awaiting review.</li>
     * <li>`creditor_verification_status_successful`: Sets a creditor's `verification status` to
     * `successful`, meaning that the creditor is fully verified and can receive payouts.</li>
     * <li>`payment_paid_out`: Transitions a payment through to `paid_out`, having been collected
     * successfully and paid out to you. It must start in the `pending_submission` state, and its mandate
     * must be in the `activated` state (unless it is a payment for ACH, BECS, BECS_NZ or SEPA, in which
     * cases the mandate may be `pending_submission`, since their mandates are submitted with their first
     * payment).</li>
     * <li>`payment_failed`: Transitions a payment through to `failed`. It must start in the
     * `pending_submission` state, and its mandate must be in the `activated` state (unless it is a
     * payment for ACH, BECS, BECS_NZ or SEPA, in which cases the mandate may be `pending_submission`,
     * since their mandates are submitted with their first payment).</li>
     * <li>`payment_charged_back`: Behaves the same as the `payout_paid_out` simulator, except that the
     * payment is transitioned to `charged_back` after it is paid out, having been charged back by the
     * customer.</li>
     * <li>`payment_chargeback_settled`: Behaves the same as the `payment_charged_back` simulator, except
     * that the charged back payment is additionally included as a debit item in a payout, thereby
     * settling the charged back payment.</li>
     * <li>`payment_late_failure`: Transitions a payment through to `late_failure`, having been
     * apparently collected successfully beforehand. It must start in the `pending_submission` state, and
     * its mandate must be in the `activated` state (unless it is a payment for ACH, BECS, BECS_NZ or
     * SEPA, in which cases the mandate may be `pending_submission`, since their mandates are submitted
     * with their first payment). Not compatible with Autogiro mandates.</li>
     * <li>`payment_late_failure_settled`: Behaves the same as the `payment_late_failure` simulator,
     * except that the late failure is additionally included as a debit item in a payout, thereby
     * settling the late failure.</li>
     * <li>`payment_submitted`: Transitions a payment to `submitted`, without proceeding any further. It
     * must start in the `pending_submission` state.</li>
     * <li>`mandate_activated`: Transitions a mandate through to `activated`, having been submitted to
     * the banks and set up successfully. It must start in the `pending_submission` state. Not compatible
     * with ACH, BECS, BECS_NZ and SEPA mandates, which are submitted and activated with their first
     * payment.</li>
     * <li>`mandate_failed`: Transitions a mandate through to `failed`, having been submitted to the
     * banks but found to be invalid (for example due to invalid bank details). It must start in the
     * `pending_submission` or `submitted` states. Not compatible with ACH, BECS, BECS_NZ and SEPA
     * mandates, which are submitted with their first payment.</li>
     * <li>`mandate_expired`: Transitions a mandate through to `expired`, having been submitted to the
     * banks, set up successfully and then expired because no collection attempts were made against it
     * for longer than the scheme's dormancy period (13 months for Bacs, 3 years for SEPA, 15 months for
     * ACH, Betalingsservice, and BECS). It must start in the `pending_submission` state. Not compatible
     * with Autogiro, BECS NZ, and PAD mandates, which do not expire.</li>
     * <li>`mandate_transferred`: Transitions a mandate through to `transferred`, having been submitted
     * to the banks, set up successfully and then moved to a new bank account due to the customer using
     * the UK's Current Account Switching Service (CASS). It must start in the `pending_submission`
     * state. Only compatible with Bacs mandates.</li>
     * <li>`mandate_transferred_with_resubmission`: Transitions a mandate through `transferred` and
     * resubmits it to the banks, can be caused be the UK's Current Account Switching Service (CASS) or
     * when a customer contacts GoCardless to change their bank details. It must start in the
     * `pending_submission` state. Only compatible with Bacs, SEPA and Autogiro mandates.</li>
     * <li>`refund_paid`: Transitions a refund to `paid`. It must start in either the
     * `pending_submission` or `submitted` state.</li>
     * <li>`refund_bounced`: Transitions a refund to `bounced`. It must start in either the
     * `pending_submission`, `submitted`, or `paid` state.</li>
     * <li>`payout_bounced`: Transitions a payout to `bounced`. It must start in the `paid` state.</li>
     * </ul>
     */
    public String getId() {
        return id;
    }
}
