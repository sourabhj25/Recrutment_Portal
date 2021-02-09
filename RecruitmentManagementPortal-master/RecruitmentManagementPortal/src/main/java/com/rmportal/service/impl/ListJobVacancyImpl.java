package com.rmportal.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmportal.model.JobVacancy;
import com.rmportal.repository.JobVacancyRepository;
import com.rmportal.responseModel.JobVacancyResponseModel;
import com.rmportal.service.ListJobVacancyService;
import com.rmportal.utility.ConversionUtility;
import com.rmportal.utility.CustomException;

/**
 * @author tejas
 *
 */
@Service
public class ListJobVacancyImpl implements ListJobVacancyService {

	@Autowired
	private JobVacancyRepository jobVacancyRepository;

	@Autowired
	private ConversionUtility conversionUtility;

	// List of all job which are active in the database
	@Override
	public List<JobVacancyResponseModel> getAllJobs() {
		List<JobVacancy> getJobs = (List<JobVacancy>) jobVacancyRepository.findAll();

		return conversionUtility.getAllJobVacancy(getJobs);
	}

	// Update the job Status
	@Override
	public String updateJobStatus(int job_vacancy_id, boolean is_active) throws CustomException {

		JobVacancy jobVacancy = jobVacancyRepository.findOne(job_vacancy_id);
		if(Objects.isNull(jobVacancy))
		{
			throw new CustomException(204,"Invalid job vacancy id");
		}

		if (is_active) {
			jobVacancy.setActive(true);
		} else {
			jobVacancy.setActive(false);
		}

		jobVacancyRepository.save(jobVacancy);
		return "Job Status Changed Successfully";
	}

	// Get all the job details added in database
	@Override
	public JobVacancyResponseModel getJobDetails(int job_vacancy_id) throws CustomException {
		JobVacancy jobVacancy = jobVacancyRepository.findOne(job_vacancy_id);
		if (Objects.isNull(jobVacancy)) {
			throw new CustomException(204, "Job vacancy id does not exist");
		}

		conversionUtility.getDetailJobVacancy(jobVacancy);
		return conversionUtility.getDetailJobVacancy(jobVacancy);
	}

}
