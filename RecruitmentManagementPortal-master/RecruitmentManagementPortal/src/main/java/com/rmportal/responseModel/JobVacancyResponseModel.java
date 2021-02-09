package com.rmportal.responseModel;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JobVacancyResponseModel {

	int job_vacancy_id;

	String job_title;

	int number_of_openings;

	// String experience_required;

	int exp_to;

	int exp_from;

	String job_description;

	String technical_skills;

	String job_location;

	String salary_ctc;

	String education;

	String job_type;

}
