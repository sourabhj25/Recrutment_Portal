package com.rmportal.utility;

import com.rmportal.service.impl.LoginServiceImpl;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * Global custom exception class.<br>
 * Returns response in form of Error ID and Error Message.
 * @author saurabh
 * @see {@link Exception}
 */
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = false)
public class CustomException extends Exception {

	int id;

	String status;

	public CustomException(int id, String status) {
		super(status);
		this.status = status;
		this.id = id;
	}

}
