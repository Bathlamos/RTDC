package rtdc.core.i18n;

import java.text.MessageFormat;
import java.util.ResourceBundle;
import java.util.logging.Logger;

/**
 * Uses a File Reader to access the .properties file
 * This is not a valid solution under GWT, which is why
 * the super path is use in the .gwt.xml file
 */
class JavaIOResBundle implements ResBundleInterface {

    private static final Logger LOGGER = Logger.getLogger(JavaIOResBundle.class.getName());

    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("rtdc.core.i18n.Bundle");

    private static final MessageFormat FORMATTER = new MessageFormat("");

    static {
        LOGGER.info("Finished loading Bundle.properties");
    }

    /** AUTOMATIC GENERATION -- DO NOT CHANGE */

	/**
	 * Action Deleted
	 */
	public String actionDeleted() {
		return BUNDLE.getString("actionDeleted");
	}

	/**
	 * Action Plan
	 */
	public String actionPlanTitle() {
		return BUNDLE.getString("actionPlanTitle");
	}

	/**
	 * Add Action
	 */
	public String addActionTitle() {
		return BUNDLE.getString("addActionTitle");
	}

	/**
	 * Add unit
	 */
	public String addUnitTitle() {
		return BUNDLE.getString("addUnitTitle");
	}

	/**
	 * Add User
	 */
	public String addUserTitle() {
		return BUNDLE.getString("addUserTitle");
	}

	/**
	 * Admin
	 */
	public String admin() {
		return BUNDLE.getString("admin");
	}

	/**
	 * Administrator
	 */
	public String administrator() {
		return BUNDLE.getString("administrator");
	}

	/**
	 * Call ended, duration {0}
	 */
	public String callEndedMessage(String arg0) {
		FORMATTER.applyPattern(BUNDLE.getString("callEndedMessage"));
		return FORMATTER.format(new Object[]{arg0});
	}

	/**
	 * Call ended
	 */
	public String callEndedTitle() {
		return BUNDLE.getString("callEndedTitle");
	}

	/**
	 * Call rejected: busy
	 */
	public String callRejectedBusy() {
		return BUNDLE.getString("callRejectedBusy");
	}

	/**
	 * Call rejected
	 */
	public String callRejectedGeneral() {
		return BUNDLE.getString("callRejectedGeneral");
	}

	/**
	 * Your call was rejected; user is busy or unavailable
	 */
	public String callRejectedUnavailable() {
		return BUNDLE.getString("callRejectedUnavailable");
	}

	/**
	 * Capacity Overview
	 */
	public String capacityOverviewTitle() {
		return BUNDLE.getString("capacityOverviewTitle");
	}

	/**
	 * Completed
	 */
	public String completed() {
		return BUNDLE.getString("completed");
	}

	/**
	 * Confirm
	 */
	public String confirm() {
		return BUNDLE.getString("confirm");
	}

	/**
	 * Confirmation doesn't match the password given
	 */
	public String confirmPasswordDoesntMatch() {
		return BUNDLE.getString("confirmPasswordDoesntMatch");
	}

	/**
	 * Are you sure you want to delete this action?
	 */
	public String deleteActionConfirmation() {
		return BUNDLE.getString("deleteActionConfirmation");
	}

	/**
	 * Delete current unit?
	 */
	public String deleteUnitConfirmation() {
		return BUNDLE.getString("deleteUnitConfirmation");
	}

	/**
	 * Delete current user?
	 */
	public String deleteUserConfirmation() {
		return BUNDLE.getString("deleteUserConfirmation");
	}

	/**
	 * Delivered
	 */
	public String delivered() {
		return BUNDLE.getString("delivered");
	}

	/**
	 * Edit Action
	 */
	public String editActionTitle() {
		return BUNDLE.getString("editActionTitle");
	}

	/**
	 * Edit Capacity - {0}
	 */
	public String editCapacityOfUnitTitle(String arg0) {
		FORMATTER.applyPattern(BUNDLE.getString("editCapacityOfUnitTitle"));
		return FORMATTER.format(new Object[]{arg0});
	}

	/**
	 * Edit Capacity
	 */
	public String editCapacityTitle() {
		return BUNDLE.getString("editCapacityTitle");
	}

	/**
	 * Edit Unit
	 */
	public String editUnitTitle() {
		return BUNDLE.getString("editUnitTitle");
	}

	/**
	 * Edit User
	 */
	public String editUserTitle() {
		return BUNDLE.getString("editUserTitle");
	}

	/**
	 * Error code {0} {1}
	 */
	public String errorCode(String arg0, String arg1) {
		FORMATTER.applyPattern(BUNDLE.getString("errorCode"));
		return FORMATTER.format(new Object[]{arg0, arg1});
	}

	/**
	 * Error
	 */
	public String errorGeneral() {
		return BUNDLE.getString("errorGeneral");
	}

	/**
	 * Unrecognized output from server: {0}
	 */
	public String errorUnrecognized(String arg0) {
		FORMATTER.applyPattern(BUNDLE.getString("errorUnrecognized"));
		return FORMATTER.format(new Object[]{arg0});
	}

	/**
	 * Failed
	 */
	public String failed() {
		return BUNDLE.getString("failed");
	}

	/**
	 * Hold
	 */
	public String hold() {
		return BUNDLE.getString("hold");
	}

	/**
	 * In call with {0}
	 */
	public String inCallWith(String arg0) {
		FORMATTER.applyPattern(BUNDLE.getString("inCallWith"));
		return FORMATTER.format(new Object[]{arg0});
	}

	/**
	 * In progress
	 */
	public String inProgress() {
		return BUNDLE.getString("inProgress");
	}

	/**
	 * Incoming call
	 */
	public String incomingAudioCall() {
		return BUNDLE.getString("incomingAudioCall");
	}

	/**
	 * Incoming video call
	 */
	public String incomingVideoCall() {
		return BUNDLE.getString("incomingVideoCall");
	}

	/**
	 * The description must have 2500 characters or less
	 */
	public String invalidActionDescription() {
		return BUNDLE.getString("invalidActionDescription");
	}

	/**
	 * The admits by deadline is greater then the total number of admits!
	 */
	public String invalidAdmitsByDeadline() {
		return BUNDLE.getString("invalidAdmitsByDeadline");
	}

	/**
	 * The discharge by deadline is greater then the potential discharge!
	 */
	public String invalidDischargeByDeadline() {
		return BUNDLE.getString("invalidDischargeByDeadline");
	}

	/**
	 * Invalid Email
	 */
	public String invalidEmail() {
		return BUNDLE.getString("invalidEmail");
	}

	/**
	 * Some fields are invalid. Please fix them before proceeding
	 */
	public String invalidFields() {
		return BUNDLE.getString("invalidFields");
	}

	/**
	 * Names may only contain letters and hyphens
	 */
	public String invalidName() {
		return BUNDLE.getString("invalidName");
	}

	/**
	 * The number of available beds is greater then the total number of beds!
	 */
	public String invalidNumberOfAvailableBeds() {
		return BUNDLE.getString("invalidNumberOfAvailableBeds");
	}

	/**
	 * The potential discharge is greater then the total number of beds!
	 */
	public String invalidPotentialDischarge() {
		return BUNDLE.getString("invalidPotentialDischarge");
	}

	/**
	 * Unit names may only contain letters, numbers and symbols
	 */
	public String invalidUnitName() {
		return BUNDLE.getString("invalidUnitName");
	}

	/**
	 * Given value is empty
	 */
	public String isEmpty() {
		return BUNDLE.getString("isEmpty");
	}

	/**
	 * Given value is not a number
	 */
	public String isNotNumber() {
		return BUNDLE.getString("isNotNumber");
	}

	/**
	 * Given value isn't a positive number
	 */
	public String isNotPositiveNumber() {
		return BUNDLE.getString("isNotPositiveNumber");
	}

	/**
	 * Given value is null
	 */
	public String isNull() {
		return BUNDLE.getString("isNull");
	}

	/**
	 * Login
	 */
	public String loginTitle() {
		return BUNDLE.getString("loginTitle");
	}

	/**
	 * Manage Units
	 */
	public String manageUnitsTitle() {
		return BUNDLE.getString("manageUnitsTitle");
	}

	/**
	 * Manage Users
	 */
	public String manageUsersTitle() {
		return BUNDLE.getString("manageUsersTitle");
	}

	/**
	 * Manager
	 */
	public String manager() {
		return BUNDLE.getString("manager");
	}

	/**
	 * Me
	 */
	public String me() {
		return BUNDLE.getString("me");
	}

	/**
	 * Message from {0}
	 */
	public String messageFrom(String arg0) {
		FORMATTER.applyPattern(BUNDLE.getString("messageFrom"));
		return FORMATTER.format(new Object[]{arg0});
	}

	/**
	 * Messages
	 */
	public String messagesTitle() {
		return BUNDLE.getString("messagesTitle");
	}

	/**
	 * Missed call from {0}
	 */
	public String missedCallFrom(String arg0) {
		FORMATTER.applyPattern(BUNDLE.getString("missedCallFrom"));
		return FORMATTER.format(new Object[]{arg0});
	}

	/**
	 * Missed call
	 */
	public String missedCallTitle() {
		return BUNDLE.getString("missedCallTitle");
	}

	/**
	 * Call unanswered
	 */
	public String missedCallUnanswered() {
		return BUNDLE.getString("missedCallUnanswered");
	}

	/**
	 * Network error {0}
	 */
	public String networkError(String arg0) {
		FORMATTER.applyPattern(BUNDLE.getString("networkError"));
		return FORMATTER.format(new Object[]{arg0});
	}

	/**
	 * Not started
	 */
	public String notStarted() {
		return BUNDLE.getString("notStarted");
	}

	/**
	 * Nurse
	 */
	public String nurse() {
		return BUNDLE.getString("nurse");
	}

	/**
	 * Off servicing
	 */
	public String offServicing() {
		return BUNDLE.getString("offServicing");
	}

	/**
	 * Other user isn't showing video
	 */
	public String otherUserNotShowingVideo() {
		return BUNDLE.getString("otherUserNotShowingVideo");
	}

	/**
	 * Passwords must have at least one letter and one number
	 */
	public String passwordAsIllegalCharacters() {
		return BUNDLE.getString("passwordAsIllegalCharacters");
	}

	/**
	 * Password cannot be empty
	 */
	public String passwordCantBeEmpty() {
		return BUNDLE.getString("passwordCantBeEmpty");
	}

	/**
	 * Password must be minimum {0} characters long
	 */
	public String passwordToShort(String arg0) {
		FORMATTER.applyPattern(BUNDLE.getString("passwordToShort"));
		return FORMATTER.format(new Object[]{arg0});
	}

	/**
	 * Paused
	 */
	public String paused() {
		return BUNDLE.getString("paused");
	}

	/**
	 * Profile
	 */
	public String profileTitle() {
		return BUNDLE.getString("profileTitle");
	}

	/**
	 * Push for discharge
	 */
	public String pushForDischarge() {
		return BUNDLE.getString("pushForDischarge");
	}

	/**
	 * Read
	 */
	public String read() {
		return BUNDLE.getString("read");
	}

	/**
	 * Ringing
	 */
	public String ringing() {
		return BUNDLE.getString("ringing");
	}

	/**
	 * RTDC
	 */
	public String rtdcTitle() {
		return BUNDLE.getString("rtdcTitle");
	}

	/**
	 * Select Time
	 */
	public String selectTime() {
		return BUNDLE.getString("selectTime");
	}

	/**
	 * Sent
	 */
	public String sent() {
		return BUNDLE.getString("sent");
	}

	/**
	 * Stakeholder
	 */
	public String stakeHolder() {
		return BUNDLE.getString("stakeHolder");
	}

	/**
	 * Changing number of total beds will reset all other quantities. Are you sure?
	 */
	public String totalBedsWarning() {
		return BUNDLE.getString("totalBedsWarning");
	}

	/**
	 * Total number of beds: {0}
	 */
	public String totalNumberOfBeds(String arg0) {
		FORMATTER.applyPattern(BUNDLE.getString("totalNumberOfBeds"));
		return FORMATTER.format(new Object[]{arg0});
	}

	/**
	 * Unit Deleted
	 */
	public String unitDeleted() {
		return BUNDLE.getString("unitDeleted");
	}

	/**
	 * Unit Manager
	 */
	public String unitManager() {
		return BUNDLE.getString("unitManager");
	}

	/**
	 * Unit Modified
	 */
	public String unitModified() {
		return BUNDLE.getString("unitModified");
	}

	/**
	 * Units
	 */
	public String unitsTitle() {
		return BUNDLE.getString("unitsTitle");
	}

	/**
	 * Unknown
	 */
	public String unknownCaller() {
		return BUNDLE.getString("unknownCaller");
	}

	/**
	 * Message type not recognized: {0}
	 */
	public String unknownMessageType(String arg0) {
		FORMATTER.applyPattern(BUNDLE.getString("unknownMessageType"));
		return FORMATTER.format(new Object[]{arg0});
	}

	/**
	 * Event has not been registered under Event.java/fire: {0}
	 */
	public String unregisteredEvent(String arg0) {
		FORMATTER.applyPattern(BUNDLE.getString("unregisteredEvent"));
		return FORMATTER.format(new Object[]{arg0});
	}

	/**
	 * user
	 */
	public String user() {
		return BUNDLE.getString("user");
	}

	/**
	 * User Deleted
	 */
	public String userDeleted() {
		return BUNDLE.getString("userDeleted");
	}

	/**
	 * Username contains illegal characters. Only allowed characters are letters, number, _ and -
	 */
	public String usernameAsIllegalCharacters() {
		return BUNDLE.getString("usernameAsIllegalCharacters");
	}

	/**
	 * Username cannot be empty
	 */
	public String usernameCantBeEmpty() {
		return BUNDLE.getString("usernameCantBeEmpty");
	}

	/**
	 * Username must be minimum {0} characters long
	 */
	public String usernameToShort(String arg0) {
		FORMATTER.applyPattern(BUNDLE.getString("usernameToShort"));
		return FORMATTER.format(new Object[]{arg0});
	}

	/**
	 * Users
	 */
	public String usersTitle() {
		return BUNDLE.getString("usersTitle");
	}

}