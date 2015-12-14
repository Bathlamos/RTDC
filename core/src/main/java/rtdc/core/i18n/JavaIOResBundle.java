package rtdc.core.i18n;

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
	 * Error
	 */
	public String errorGeneral() {
		return BUNDLE.getString("errorGeneral");
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
	 * Passwords must have at least one letter and one number
	 */
	public String passwordAsIllegalCharacters() {
		return BUNDLE.getString("passwordAsIllegalCharacters");
	}

	/**
	 * Password must be minimum {0} characters long
	 */
	public String passwordToShort() {
		return BUNDLE.getString("passwordToShort");
	}

	/**
	 * Username contains illegal characters. Only allowed characters are letters, number, _ and -
	 */
	public String usernameAsIllegalCharacters() {
		return BUNDLE.getString("usernameAsIllegalCharacters");
	}

	/**
	 * Username must be minimum {0} characters long
	 */
	public String usernameToShort() {
		return BUNDLE.getString("usernameToShort");
	}

}