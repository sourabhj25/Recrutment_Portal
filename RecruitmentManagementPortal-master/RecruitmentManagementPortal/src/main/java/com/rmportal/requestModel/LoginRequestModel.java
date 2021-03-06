package com.rmportal.requestModel;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * @author saurabh
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginRequestModel {

	@NotEmpty
	// @Email
	String email;

	@Size(max = 16, min = 8, message = "Length of password must be at least 8-16 characters")
	@NotEmpty
	String password;

}
