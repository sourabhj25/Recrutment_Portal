package com.rmportal.service;

import org.springframework.stereotype.Component;

import com.rmportal.requestModel.JobVacancyRequestModel;
import com.rmportal.responseModel.AddJobVacancyResponse;
import com.rmportal.utility.CustomException;

/**
 * @author saurabh
 *
 */
@Component
public interface AddJobVacancyService {

	public AddJobVacancyResponse addVacancy(JobVacancyRequestModel jobVacancyRequestModel) throws CustomException;
	
	public AddJobVacancyResponse updateJobVacancy(int job_vacancy_id,JobVacancyRequestModel jobVacancyRequestModel) throws CustomException;
	
	
}
