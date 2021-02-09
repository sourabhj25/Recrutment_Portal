package com.rmportal.requestModel;

import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateRequestModel {

	String firstName;

	String lastName;

	String address;

	String dateOfBirth;

	String city;

	String country;

	String employee_id;

	@Size(max = 10, message = "Mobile Number cannot be greater than 10 digits.")
	String mobile;

	String blood_group;

	String dept_name;
}
