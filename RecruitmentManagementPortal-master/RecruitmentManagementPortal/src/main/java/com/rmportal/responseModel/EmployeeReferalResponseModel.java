package com.rmportal.responseModel;

import java.util.Date;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeReferalResponseModel {

	int referal_id;

	String applicant_name;

	float experience;

	String technical_skill;

	// @Column(name="referance_email")
	// String referance_email;

	// @Column(name="referance_user_id")
	// int referance_user_id;
	//

	String resume;

	String application_status;

	int job_id;

	Date date;

	String bonous_status;
	
	String fileName;
	
	String fileExtension;
}
