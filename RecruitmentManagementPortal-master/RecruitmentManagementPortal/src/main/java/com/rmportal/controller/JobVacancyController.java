package com.rmportal.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rmportal.constants.HttpStatusConstants;
import com.rmportal.requestModel.JobVacancyRequestModel;
import com.rmportal.responseModel.AddJobVacancyResponse;
import com.rmportal.responseModel.HttpResponseModel;
import com.rmportal.responseModel.JobVacancyResponseModel;
import com.rmportal.service.AddJobVacancyService;
import com.rmportal.service.ListJobVacancyService;
import com.rmportal.utility.ApplicationUtils;
import com.rmportal.utility.CustomException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author saurabh/tejas
 *
 */
@RestController
@Api(value = "Job Vacancy", description = "Job Vacancies")
@CrossOrigin("*")
public class JobVacancyController {

	@Autowired
	private AddJobVacancyService addJobVacancyService;

	@Autowired
	private ListJobVacancyService listJobVacancyService;

	@Autowired
	private ApplicationUtils applicationUtils;

	// Add Job Vacancies
	@RequestMapping(value = "/addJobVacancy", method = RequestMethod.POST)
	@ApiOperation(value = "Add Job Vacancies")
	public ResponseEntity<?> addJobVacancy(@Valid @RequestBody JobVacancyRequestModel jobVacancyRequestModel,
			BindingResult binding) throws CustomException {

		try {
			if (binding.hasErrors()) {
				throw new CustomException(204, binding.getAllErrors().get(0).getDefaultMessage());
			}
			applicationUtils.validateEntity(jobVacancyRequestModel, binding);
		} catch (Exception e) {
			throw new CustomException(201, e.getMessage());
		}

		AddJobVacancyResponse addJobVacancyResponse = null;
		addJobVacancyResponse = addJobVacancyService.addVacancy(jobVacancyRequestModel);

		return ResponseEntity.ok(
				new HttpResponseModel("New JOB POSTED Successfully", HttpStatusConstants.OK.id, addJobVacancyResponse));
	}

	// Get job Vacancies
	@RequestMapping(value = "/getJobVacancy", method = RequestMethod.GET)
	@ApiOperation(value = "Job Vacancy List")
	public ResponseEntity<?> getJobVacancy(){
		List<JobVacancyResponseModel> jobVacancy = listJobVacancyService.getAllJobs();

		return ResponseEntity.ok(new HttpResponseModel("Job Vacancies list fetched successfully",
				HttpStatusConstants.OK.id, jobVacancy));

	}

	// Update Job Status
	@RequestMapping(value = "/jobStatus", method = RequestMethod.GET)
	@ApiOperation(value = "Activate or Deactivate the jobs")
	public ResponseEntity<?> jobStatus(@RequestParam int job_vacancy_id, @RequestParam boolean is_active)
			throws CustomException {

		String message = listJobVacancyService.updateJobStatus(job_vacancy_id, is_active);
		return ResponseEntity.ok(
				new HttpResponseModel(HttpStatusConstants.OK.getStatus() + message, HttpStatusConstants.OK.id, null));
	}

	// Update Job Vacancy
	@RequestMapping(value = "/updateJobVacancy/{id}", method = RequestMethod.POST)
	@ApiOperation(value = "update Job Vacancies")
	public ResponseEntity<?> addJobVacancy(@PathVariable("id") int job_vacancy_id,
			@Valid @RequestBody JobVacancyRequestModel jobVacancyRequestModel, BindingResult bindingResult)
			throws CustomException {
		AddJobVacancyResponse addJobVacancyResponse = null;

		try {
			if (bindingResult.hasErrors()) {
				throw new CustomException(204, bindingResult.getAllErrors().get(0).getDefaultMessage());
			}
			applicationUtils.validateEntity(jobVacancyRequestModel, bindingResult);
		} catch (CustomException e) {
			return ResponseEntity.ok(new HttpResponseModel(e.getMessage(), e.getId(), null));
		}

		addJobVacancyResponse = addJobVacancyService.updateJobVacancy(job_vacancy_id, jobVacancyRequestModel);
		return ResponseEntity.ok(new HttpResponseModel("JOB vacancy updated Successfully", HttpStatusConstants.OK.id,
				addJobVacancyResponse));
	}

	// Get row from job vacancy table for JOB Update
	@RequestMapping(value = "/getDetailsOfJob/{job_vacancy_id}", method = RequestMethod.POST)
	@ApiOperation(value = "Get job vacancy details for update job vacancy")
	public ResponseEntity<?> addJobVacancy(@PathVariable("job_vacancy_id") int job_vacancy_id) throws CustomException {
		JobVacancyResponseModel jobVacancyResponseModel = listJobVacancyService.getJobDetails(job_vacancy_id);
		return ResponseEntity.ok(new HttpResponseModel("Detais of the job_vacancy id fetched Successfully",
				HttpStatusConstants.OK.id, jobVacancyResponseModel));
	}

}
