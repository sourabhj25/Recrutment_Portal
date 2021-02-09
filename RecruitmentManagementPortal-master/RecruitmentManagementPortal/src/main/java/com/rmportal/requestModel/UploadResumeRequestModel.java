package com.rmportal.requestModel;

import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level=AccessLevel.PRIVATE)
public class UploadResumeRequestModel {

	@NotNull(message = "Internal Error Occured. Please contact to Admin")
	String email;

	@NotNull(message = "Mandatory fields cannot be null")
	String applicant_name;

	@NotNull(message = "Mandatory fields cannot be null")
	float experience;

	@NotNull(message = "Mandatory fields cannot be null")
	String technical_skills;
}
