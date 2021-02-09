package com.rmportal.requestModel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * @author saurabh
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level=AccessLevel.PRIVATE)
public class ChangePasswordModel {

	@NotNull(message = "Internal Error Occured. Please contact to Admin")
	String email;
	
	@Size(min = 8, max = 16, message= "Password must contain atleast 8-16 characters")
	@NotNull(message = "Mandatory field cannot be Empty")
	String oldPassword;
	
	@Size(min = 8, max = 16, message= "Password must contain atleast 8-16 characters")
	@NotNull(message = "Mandatory field cannot be Empty")
	String newPassword;
	
	/*@Size(min = 8, max = 16, message= "Password must contain atleast 8-16 characters")
	@NotNull(message = "Mandatory field cannot be Empty")
	String confirmNewPassword;*/
	
}
