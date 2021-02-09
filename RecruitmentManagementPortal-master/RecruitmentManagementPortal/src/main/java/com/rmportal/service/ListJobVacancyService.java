package com.rmportal.service;

import java.util.List;

import com.rmportal.responseModel.JobVacancyResponseModel;
import com.rmportal.utility.CustomException;

public interface ListJobVacancyService {
	
	
	public List<JobVacancyResponseModel> getAllJobs();
	
	public String updateJobStatus(int job_vacancy_id, boolean is_active) throws CustomException;
	
	public JobVacancyResponseModel getJobDetails(int job_vacancy_id) throws CustomException;
}
