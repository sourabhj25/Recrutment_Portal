package com.rmportal.requestModel;

import javax.validation.constraints.Min;
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
public class PublishJobRequestModel {

	@NotNull(message="Designation field is empty")
	String designation;
	
	@NotNull(message="Experience field is empty")
	String experience;
	
	@Min(value=1, message="Opening Positions must me more than one")
	int positions;
	
	String jobDiscription;
	
	String knowledgeOf;
}
