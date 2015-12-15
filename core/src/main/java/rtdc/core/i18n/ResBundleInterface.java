package rtdc.core.i18n;

public interface ResBundleInterface {

	/**
	 * Action Deleted
	 */
	String actionDeleted();

	/**
	 * Action Plan
	 */
	String actionPlanTitle();

	/**
	 * Add Action
	 */
	String addActionTitle();

	/**
	 * Add unit
	 */
	String addUnitTitle();

	/**
	 * Add User
	 */
	String addUserTitle();

	/**
	 * Admin
	 */
	String admin();

	/**
	 * Administrator
	 */
	String administrator();

	/**
	 * Call ended, duration {duration}
	 */
	String callEndedMessage(String duration);

	/**
	 * Call ended
	 */
	String callEndedTitle();

	/**
	 * Call rejected: busy
	 */
	String callRejectedBusy();

	/**
	 * Call rejected
	 */
	String callRejectedGeneral();

	/**
	 * Your call was rejected; user is busy or unavailable
	 */
	String callRejectedUnavailable();

	/**
	 * Capacity Overview
	 */
	String capacityOverviewTitle();

	/**
	 * Completed
	 */
	String completed();

	/**
	 * Confirm
	 */
	String confirm();

	/**
	 * Confirmation doesn't match the password given
	 */
	String confirmPasswordDoesntMatch();

	/**
	 * Are you sure you want to delete this action?
	 */
	String deleteActionConfirmation();

	/**
	 * Delete current unit?
	 */
	String deleteUnitConfirmation();

	/**
	 * Delete current user?
	 */
	String deleteUserConfirmation();

	/**
	 * Delivered
	 */
	String delivered();

	/**
	 * Edit Action
	 */
	String editActionTitle();

	/**
	 * Edit Capacity - {capacityName}
	 */
	String editCapacityOfUnitTitle(String capacityName);

	/**
	 * Edit Capacity
	 */
	String editCapacityTitle();

	/**
	 * Edit Unit
	 */
	String editUnitTitle();

	/**
	 * Edit User
	 */
	String editUserTitle();

	/**
	 * Error code {errorCode} {errorMessage}
	 */
	String errorCode(String errorCode, String errorMessage);

	/**
	 * Error
	 */
	String errorGeneral();

	/**
	 * Unrecognized output from server: {output}
	 */
	String errorUnrecognized(String output);

	/**
	 * Failed
	 */
	String failed();

	/**
	 * Hold
	 */
	String hold();

	/**
	 * In call with {caller}
	 */
	String inCallWith(String caller);

	/**
	 * In progress
	 */
	String inProgress();

	/**
	 * Incoming call
	 */
	String incomingAudioCall();

	/**
	 * Incoming video call
	 */
	String incomingVideoCall();

	/**
	 * The description must have 2500 characters or less
	 */
	String invalidActionDescription();

	/**
	 * The admits by deadline is greater then the total number of admits!
	 */
	String invalidAdmitsByDeadline();

	/**
	 * The discharge by deadline is greater then the potential discharge!
	 */
	String invalidDischargeByDeadline();

	/**
	 * Invalid Email
	 */
	String invalidEmail();

	/**
	 * Some fields are invalid. Please fix them before proceeding
	 */
	String invalidFields();

	/**
	 * Names may only contain letters and hyphens
	 */
	String invalidName();

	/**
	 * The number of available beds is greater then the total number of beds!
	 */
	String invalidNumberOfAvailableBeds();

	/**
	 * The potential discharge is greater then the total number of beds!
	 */
	String invalidPotentialDischarge();

	/**
	 * Unit names may only contain letters, numbers and symbols
	 */
	String invalidUnitName();

	/**
	 * Given value is empty
	 */
	String isEmpty();

	/**
	 * Given value is not a number
	 */
	String isNotNumber();

	/**
	 * Given value isn't a positive number
	 */
	String isNotPositiveNumber();

	/**
	 * Given value is null
	 */
	String isNull();

	/**
	 * Login
	 */
	String loginTitle();

	/**
	 * Manage Units
	 */
	String manageUnitsTitle();

	/**
	 * Manage Users
	 */
	String manageUsersTitle();

	/**
	 * Manager
	 */
	String manager();

	/**
	 * Me
	 */
	String me();

	/**
	 * Message from {user}
	 */
	String messageFrom(String user);

	/**
	 * Messages
	 */
	String messagesTitle();

	/**
	 * Missed call from {caller}
	 */
	String missedCallFrom(String caller);

	/**
	 * Missed call
	 */
	String missedCallTitle();

	/**
	 * Call unanswered
	 */
	String missedCallUnanswered();

	/**
	 * Network error {errorMessage}
	 */
	String networkError(String errorMessage);

	/**
	 * Not started
	 */
	String notStarted();

	/**
	 * Nurse
	 */
	String nurse();

	/**
	 * Off servicing
	 */
	String offServicing();

	/**
	 * Other user isn't showing video
	 */
	String otherUserNotShowingVideo();

	/**
	 * Passwords must have at least one letter and one number
	 */
	String passwordAsIllegalCharacters();

	/**
	 * Password cannot be empty
	 */
	String passwordCantBeEmpty();

	/**
	 * Password must be minimum {minLength} characters long
	 */
	String passwordToShort(String minLength);

	/**
	 * Paused
	 */
	String paused();

	/**
	 * Profile
	 */
	String profileTitle();

	/**
	 * Push for discharge
	 */
	String pushForDischarge();

	/**
	 * Read
	 */
	String read();

	/**
	 * Ringing
	 */
	String ringing();

	/**
	 * RTDC
	 */
	String rtdcTitle();

	/**
	 * Select Time
	 */
	String selectTime();

	/**
	 * Sent
	 */
	String sent();

	/**
	 * Stakeholder
	 */
	String stakeHolder();

	/**
	 * Changing number of total beds will reset all other quantities. Are you sure?
	 */
	String totalBedsWarning();

	/**
	 * Total number of beds: {numBeds}
	 */
	String totalNumberOfBeds(String numBeds);

	/**
	 * Unit Deleted
	 */
	String unitDeleted();

	/**
	 * Unit Manager
	 */
	String unitManager();

	/**
	 * Unit Modified
	 */
	String unitModified();

	/**
	 * Units
	 */
	String unitsTitle();

	/**
	 * Unknown
	 */
	String unknownCaller();

	/**
	 * Message type not recognized: {type}
	 */
	String unknownMessageType(String type);

	/**
	 * Event has not been registered under Event.java/fire: {eventType}
	 */
	String unregisteredEvent(String eventType);

	/**
	 * user
	 */
	String user();

	/**
	 * User Deleted
	 */
	String userDeleted();

	/**
	 * Username contains illegal characters. Only allowed characters are letters, number, _ and -
	 */
	String usernameAsIllegalCharacters();

	/**
	 * Username cannot be empty
	 */
	String usernameCantBeEmpty();

	/**
	 * Username must be minimum {minLength} characters long
	 */
	String usernameToShort(String minLength);

	/**
	 * Users
	 */
	String usersTitle();

}