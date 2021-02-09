package com.rmportal.service.impl;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmportal.model.JobVacancy;
import com.rmportal.repository.JobVacancyRepository;
import com.rmportal.requestModel.JobVacancyRequestModel;
import com.rmportal.responseModel.AddJobVacancyResponse;
import com.rmportal.service.AddJobVacancyService;
import com.rmportal.utility.ConversionUtility;
import com.rmportal.utility.CustomException;

/**
 * @author saurabh
 *
 */
@Service
public class AddJobVacancyImpl implements AddJobVacancyService {

	@Autowired
	private JobVacancyRepository jobVacancyRepository;

	@Autowired
	private ConversionUtility conversionUtility;

	@Override
	public AddJobVacancyResponse addVacancy(JobVacancyRequestModel jobVacancyRequestModel) throws CustomException {

		AddJobVacancyResponse addJobVacancyResponse = new AddJobVacancyResponse();
		if (Objects.isNull(jobVacancyRequestModel)) {
			throw new CustomException(204, "Mandatory fields must not be empty");
		}
		if (jobVacancyRequestModel.getExp_from() == 0 && jobVacancyRequestModel.getExp_to() == 0) {
			addJobVacancyResponse.setStatus("Fresher");
		} else if (jobVacancyRequestModel.getExp_from() >= jobVacancyRequestModel.getExp_to()) {
			throw new CustomException(205, "Value for experience from should be less than experience to");
		}

		JobVacancy jobVacancy = conversionUtility.addJobVacancy(jobVacancyRequestModel);
		if (Objects.isNull(jobVacancy)) {
			throw new CustomException(501, "Error in JOB Posting.");
		}

		jobVacancyRepository.save(jobVacancy);
		addJobVacancyResponse.setJob_id(jobVacancy.getJob_vacancy_id());
		addJobVacancyResponse.setJob_title(jobVacancy.getJob_title());
		addJobVacancyResponse.setMessage("Job Added Succesfully");
		return addJobVacancyResponse;
	}

	// update job vacancy
	@Override
	public AddJobVacancyResponse updateJobVacancy(int job_vacancy_id, JobVacancyRequestModel jobVacancyRequestModel)
			throws CustomException {

		AddJobVacancyResponse addJobVacancyResponse = new AddJobVacancyResponse();

		if (jobVacancyRequestModel.getExp_from() == 0 && jobVacancyRequestModel.getExp_to() == 0) {
			addJobVacancyResponse.setStatus("Fresher");
		} else if (jobVacancyRequestModel.getExp_from() >= jobVacancyRequestModel.getExp_to()) {
			throw new CustomException(205, "Value for experience from should be less than experience to");
		}

		JobVacancy jobVacancy = jobVacancyRepository.findByJobVacancyId(job_vacancy_id);

		if (Objects.isNull(jobVacancy)) {
			throw new CustomException(204, "Invalid Job id. Job does not exists");
		}

		if (!jobVacancy.isActive()) {
			throw new CustomException(401, "Sorry. Cannot update Job as it is Inactive");
		}

		JobVacancy jobVacancyCU = conversionUtility.updateJobVacancy(jobVacancy, jobVacancyRequestModel);

		jobVacancyRepository.save(jobVacancyCU);

		addJobVacancyResponse.setJob_title(jobVacancy.getJob_title());
		addJobVacancyResponse.setJob_id(jobVacancy.getJob_vacancy_id());
		addJobVacancyResponse.setMessage("Job Vacancy Updated Succesfully");
		return addJobVacancyResponse;

	}
}
