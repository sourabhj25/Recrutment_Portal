package com.rmportal.utility;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class UserUtility {

	private UserUtility() {
		// this class can not be instantiated
	}

	/*public static boolean isInvalidValue(String value) {
		return StringUtils.isBlank(value);
	}*/

	public static boolean isValidMobile(String mobile) {
		return mobile.matches("^[0-9]{10}$");
	}

	public static boolean isValidEmail(String email) {
		return email.matches("^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$");
	}

	public static boolean isValidfullName(String fullName) {
		if (StringUtils.isBlank(fullName)) {
			return false;
		}

		return fullName.matches("^[a-zA-Z]+[\\s]*[a-zA-Z]*$");
	}

	public static boolean isValidName(String name) {
		return name.matches("^[a-zA-Z]*$");
	}

	public static boolean isValidDetails(String details) {
		String[] detailsArray = details.split(",");
		for (String array : detailsArray) {
			if (StringUtils.isBlank(array)) {
				return false;
			}
		}
		return true;
	}
}
