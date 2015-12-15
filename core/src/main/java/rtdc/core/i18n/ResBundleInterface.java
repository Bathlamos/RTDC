package rtdc.core.i18n;

public interface ResBundleInterface {

	/**
	 * Error
	 */
	String errorGeneral();

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
	 * Passwords must have at least one letter and one number
	 */
	String passwordAsIllegalCharacters();

	/**
	 * Password must be minimum {0} characters long
	 */
	String passwordToShort();

	/**
	 * Username contains illegal characters. Only allowed characters are letters, number, _ and -
	 */
	String usernameAsIllegalCharacters();

	/**
	 * Username must be minimum {0} characters long
	 */
	String usernameToShort();

}