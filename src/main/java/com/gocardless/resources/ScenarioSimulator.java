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

    private String description;
    private String id;
    private String name;
    private String resourceType;

    /**
     * A short description of what the simulator does and anything you should know about using it (e.g.
     * what state the resource should be in).
     */
    public String getDescription() {
        return description;
    }

    /**
     * The unique identifier of the simulator, used to initiate simulations. One of:
     * <ul>
     * <li>`creditor_verification_status_action_required`</li>
     * <li>`creditor_verification_status_in_review`</li>
     * <li>`creditor_verification_status_successful`</li>
     * <li>`payment_paid_out`</li>
     * <li>`payment_failed`</li>
     * <li>`payment_charged_back`</li>
     * <li>`payment_late_failure`</li>
     * <li>`payment_late_failure_settled`</li>
     * <li>`payment_submitted`</li>
     * <li>`mandate_activated`</li>
     * <li>`mandate_failed`</li>
     * <li>`mandate_expired`</li>
     * <li>`mandate_transferred`</li>
     * <li>`refund_paid`</li>
     * <li>`payout_bounced`</li>
     * </ul>
     */
    public String getId() {
        return id;
    }

    /**
     * The name of the simulator, suitable for displaying to the user, translated into their language
     */
    public String getName() {
        return name;
    }

    /**
     * The type of resource which the simulator operates on
     */
    public String getResourceType() {
        return resourceType;
    }
}
