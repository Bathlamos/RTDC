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
	 * Call ended, duration {0}
	 */
	String callEndedMessage(String arg0);

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
	 * Edit Capacity - {0}
	 */
	String editCapacityOfUnitTitle(String arg0);

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
	 * Error code {0} {1}
	 */
	String errorCode(String arg0, String arg1);

	/**
	 * Error
	 */
	String errorGeneral();

	/**
	 * Unrecognized output from server: {0}
	 */
	String errorUnrecognized(String arg0);

	/**
	 * Failed
	 */
	String failed();

	/**
	 * Hold
	 */
	String hold();

	/**
	 * In call with {0}
	 */
	String inCallWith(String arg0);

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
	 * Message from {0}
	 */
	String messageFrom(String arg0);

	/**
	 * Messages
	 */
	String messagesTitle();

	/**
	 * Missed call from {0}
	 */
	String missedCallFrom(String arg0);

	/**
	 * Missed call
	 */
	String missedCallTitle();

	/**
	 * Call unanswered
	 */
	String missedCallUnanswered();

	/**
	 * Network error {0}
	 */
	String networkError(String arg0);

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
	 * Password must be minimum {0} characters long
	 */
	String passwordToShort(String arg0);

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
	 * Total number of beds: {0}
	 */
	String totalNumberOfBeds(String arg0);

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
	 * Message type not recognized: {0}
	 */
	String unknownMessageType(String arg0);

	/**
	 * Event has not been registered under Event.java/fire: {0}
	 */
	String unregisteredEvent(String arg0);

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
	 * Username must be minimum {0} characters long
	 */
	String usernameToShort(String arg0);

	/**
	 * Users
	 */
	String usersTitle();

}