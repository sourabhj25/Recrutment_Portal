package com.rmportal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Table(name = "job_vacancy")
@Entity
@Data
@FieldDefaults(level=AccessLevel.PRIVATE)
public class JobVacancy {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "job_vacancy_id")
	int job_vacancy_id;

	@Column(name = "job_title")
	String job_title;

	@Column(name = "number_of_openings")
	int number_of_openings;

	@Column(name = "experience_required")
	String experience_required;

	@Column(name = "job_description")
	String job_description;

	@Column(name = "technical_skills")
	String technical_skills;

	@Column(name = "job_location")
	String job_location;

	@Column(name = "salary_ctc")
	String salary_ctc;

	@Column(name = "education")
	String education;

	@Column(name = "job_type")
	String job_type;

	@Column(name = "is_active")
	boolean isActive;
	
	@Column(name = "exp_to")
	int exp_to;
	
	@Column(name = "exp_from")
	int exp_from;
}
