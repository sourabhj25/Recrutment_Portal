package com.rmportal.responseModel;

import com.rmportal.model.Department;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateResponseModel {

	String employee_id;

	String firstName;

	String lastName;

	Department department;

	String address;

	String DOB;

	String city;

	String country;

	String mobile;

	String blood_group;

	String email;

	String roles;

	// String dept_name;

}
