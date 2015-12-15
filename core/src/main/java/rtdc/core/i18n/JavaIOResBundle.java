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
	 * Call ended, duration {duration}
	 */
	public String callEndedMessage(String duration) {
		String string = BUNDLE.getString("callEndedMessage"); 
		if(duration != null) string = string.replace("{duration}", duration); 
		return string; 
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
	 * Edit Capacity - {capacityName}
	 */
	public String editCapacityOfUnitTitle(String capacityName) {
		String string = BUNDLE.getString("editCapacityOfUnitTitle"); 
		if(capacityName != null) string = string.replace("{capacityName}", capacityName); 
		return string; 
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
	 * Error code {errorCode} {errorMessage}
	 */
	public String errorCode(String errorCode, String errorMessage) {
		String string = BUNDLE.getString("errorCode"); 
		if(errorCode != null) string = string.replace("{errorCode}", errorCode); 
		if(errorMessage != null) string = string.replace("{errorMessage}", errorMessage); 
		return string; 
	}

	/**
	 * Error
	 */
	public String errorGeneral() {
		return BUNDLE.getString("errorGeneral");
	}

	/**
	 * Unrecognized output from server: {output}
	 */
	public String errorUnrecognized(String output) {
		String string = BUNDLE.getString("errorUnrecognized"); 
		if(output != null) string = string.replace("{output}", output); 
		return string; 
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
	 * In call with {caller}
	 */
	public String inCallWith(String caller) {
		String string = BUNDLE.getString("inCallWith"); 
		if(caller != null) string = string.replace("{caller}", caller); 
		return string; 
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
	 * Message from {user}
	 */
	public String messageFrom(String user) {
		String string = BUNDLE.getString("messageFrom"); 
		if(user != null) string = string.replace("{user}", user); 
		return string; 
	}

	/**
	 * Messages
	 */
	public String messagesTitle() {
		return BUNDLE.getString("messagesTitle");
	}

	/**
	 * Missed call from {caller}
	 */
	public String missedCallFrom(String caller) {
		String string = BUNDLE.getString("missedCallFrom"); 
		if(caller != null) string = string.replace("{caller}", caller); 
		return string; 
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
	 * Network error {errorMessage}
	 */
	public String networkError(String errorMessage) {
		String string = BUNDLE.getString("networkError"); 
		if(errorMessage != null) string = string.replace("{errorMessage}", errorMessage); 
		return string; 
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
	 * Password must be minimum {minLength} characters long
	 */
	public String passwordToShort(String minLength) {
		String string = BUNDLE.getString("passwordToShort"); 
		if(minLength != null) string = string.replace("{minLength}", minLength); 
		return string; 
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
	 * Total number of beds: {numBeds}
	 */
	public String totalNumberOfBeds(String numBeds) {
		String string = BUNDLE.getString("totalNumberOfBeds"); 
		if(numBeds != null) string = string.replace("{numBeds}", numBeds); 
		return string; 
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
	 * Message type not recognized: {type}
	 */
	public String unknownMessageType(String type) {
		String string = BUNDLE.getString("unknownMessageType"); 
		if(type != null) string = string.replace("{type}", type); 
		return string; 
	}

	/**
	 * Event has not been registered under Event.java/fire: {eventType}
	 */
	public String unregisteredEvent(String eventType) {
		String string = BUNDLE.getString("unregisteredEvent"); 
		if(eventType != null) string = string.replace("{eventType}", eventType); 
		return string; 
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
	 * Username must be minimum {minLength} characters long
	 */
	public String usernameToShort(String minLength) {
		String string = BUNDLE.getString("usernameToShort"); 
		if(minLength != null) string = string.replace("{minLength}", minLength); 
		return string; 
	}

	/**
	 * Users
	 */
	public String usersTitle() {
		return BUNDLE.getString("usersTitle");
	}

}