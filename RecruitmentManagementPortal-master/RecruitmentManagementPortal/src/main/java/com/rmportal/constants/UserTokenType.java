package com.rmportal.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author saurabh 
 * Constants for type of token generated for the user in
 *         UserToken Table
 */
@Getter	// Generate Getters
@AllArgsConstructor	// Generate All args constructor
public enum UserTokenType {

	ADD_USER, 
	RESET_PASSWORD
}
