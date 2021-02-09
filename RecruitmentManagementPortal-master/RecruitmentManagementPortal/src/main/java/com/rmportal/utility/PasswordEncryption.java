package com.rmportal.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author saurabh
 *
 */
@Component
public class PasswordEncryption {

	@Autowired
	private PasswordEncoder bCryptPassword;
	
	public String hashEncoder(String password) {
		String hashEncoder = bCryptPassword.encode(password);
		return hashEncoder;
	}
	
	
}
