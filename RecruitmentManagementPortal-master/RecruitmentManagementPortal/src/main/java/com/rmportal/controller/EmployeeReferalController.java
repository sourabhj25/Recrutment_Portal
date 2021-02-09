package com.rmportal.controller;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rmportal.constants.HttpStatusConstants;
import com.rmportal.model.EmployeeReferal;
import com.rmportal.model.ReferralStatus;
import com.rmportal.requestModel.ReferralStatusRequestModel;
import com.rmportal.requestModel.UploadResumeRequestModel;
import com.rmportal.responseModel.CandidateJoinResponseModel;
import com.rmportal.responseModel.ChangeReferralStatusResponse;
import com.rmportal.responseModel.EmployeeReferalResponseModel;
import com.rmportal.responseModel.HttpResponseModel;
import com.rmportal.responseModel.UploadResumeResponseModel;
import com.rmportal.service.EmployeeReferalService;
import com.rmportal.utility.ApplicationUtils;
import com.rmportal.utility.CustomException;
import com.rmportal.utility.UserUtility;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author tejas
 *
 */

@RestController
@Api(value = "EmployeeReferal Controller", description = "Refer the Candidate")
@CrossOrigin("*")
public class EmployeeReferalController {

	@Autowired
	private EmployeeReferalService employeeReferalService;

	@Autowired
	private ApplicationUtils applicationUtils;

	// Get Employee Details -- For the employee
	@RequestMapping(value = "/getEmployeeReferalList", method = RequestMethod.GET)
	@ApiOperation(value = "Get Candidate list for the employee Reference")
	public ResponseEntity<?> getEmployeeReferalList(@RequestParam("referance_email") String referance_email) {

		List<EmployeeReferalResponseModel> employeeReferalResponseModel = null;

		try {
			employeeReferalResponseModel = employeeReferalService.getEmployeeDetails(referance_email);
		} catch (CustomException e) {
			return ResponseEntity
					.ok(new HttpResponseModel(e.getMessage(), HttpStatusConstants.INTERNAL_SERVER_ERROR.id, null));
		}

		return ResponseEntity.ok(new HttpResponseModel("list of employee referals Fetched Successfully",
				HttpStatusConstants.OK.id, employeeReferalResponseModel));

	}

	// Upload Resume
	@RequestMapping(value = "/uploadResume", method = RequestMethod.POST, consumes = {
			MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Upload Resume")
	public ResponseEntity<?> uploadResume(
			@ApiParam("Please enter the value in this format. \n\n"
					+ "{\"email\":\"value\", \"applicant_name\":\"value\", \"experience\":\"value\", \"technical_skills\":\"value\"}") @RequestPart("details") String details,
			@RequestPart("file") MultipartFile file, BindingResult bindingResult) throws CustomException {

		UploadResumeRequestModel uploadResumeRequestModel = null;

		if (file.isEmpty()) {
			return ResponseEntity.ok(new HttpResponseModel("Please attach the File", HttpStatusConstants.OK.id, null));
		}
		if (StringUtils.isBlank(details)) {
			return ResponseEntity.ok(new HttpResponseModel("Please fill the Details", HttpStatusConstants.OK.id, null));
		}
		if (!UserUtility.isValidDetails(details)) {
			throw new CustomException(204, "Please fill the Details");
		}

		ObjectMapper mapper = new ObjectMapper();
		try {
			uploadResumeRequestModel = mapper.readValue(details, UploadResumeRequestModel.class);
			if (bindingResult.hasErrors())
				throw new CustomException(204, bindingResult.getAllErrors().get(0).getDefaultMessage());
			applicationUtils.validateEntity(uploadResumeRequestModel, bindingResult);
		} catch (Exception e) {
			e.getMessage();
		}

		if(Objects.isNull(uploadResumeRequestModel)){
			throw new CustomException(401, "Details field cannot be empty");
		}
		if (!UserUtility.isValidEmail(uploadResumeRequestModel.getEmail())) {
			throw new CustomException(204, "Invalid email id");
		}
		if (!UserUtility.isValidfullName(uploadResumeRequestModel.getApplicant_name())) {
			throw new CustomException(204, "Invalid applicant name");
		}
		if (StringUtils.isBlank(uploadResumeRequestModel.getTechnical_skills())) {
			throw new CustomException(204, "Please enter technical skills");
		}
		if (uploadResumeRequestModel.getExperience() == 0) {
			throw new CustomException(204, "Please enter experience");
		}

		UploadResumeResponseModel uploadResumeResponseModel = employeeReferalService.addResume(uploadResumeRequestModel,
				file);

		return ResponseEntity.ok(
				new HttpResponseModel("Data Saved Successfully", HttpStatusConstants.OK.id, uploadResumeResponseModel));
	}

	// Fetch File from DB
	@RequestMapping(value = "/retrieveFile", method = RequestMethod.GET)
	@ApiOperation(value = "Retrieve Resume")
	public ResponseEntity<?> retriveFile(@RequestParam int referal_id, HttpServletResponse httpServletResponse)
			throws IOException, CustomException {

		EmployeeReferal resumeFile = employeeReferalService.fetchResume(referal_id);
		httpServletResponse.setContentType("application/pdf");

		FileCopyUtils.copy(resumeFile.getResume(), httpServletResponse.getOutputStream());
		return null;
	}

	// Get all referral list -- Only to HR and Admin
	@RequestMapping(value = "/getAllEmployeeReferals", method = RequestMethod.GET)
	@ApiOperation(value = "Get All Employee Referal List")
	public ResponseEntity<?> getAllEmployeeReferals() throws CustomException {

		List<EmployeeReferalResponseModel> employeeReferalList = employeeReferalService.getEmployeeReferalList();

		return ResponseEntity.ok(new HttpResponseModel("Employee Referral list fetched successfully",
				HttpStatusConstants.OK.id, employeeReferalList));

	}

	// Change/Set Referral Status
	@RequestMapping(value = "/changeReferralStatus", method = RequestMethod.POST)
	@ApiOperation(value = "Change the Referral status for the applicant")
	public ResponseEntity<?> setReferralStatus(@RequestBody ReferralStatusRequestModel referralStatusRequestModel)
			throws CustomException {

		if (Objects.isNull(referralStatusRequestModel)) {
			return ResponseEntity.ok(new HttpResponseModel(" Sorry. Unable to proceed the request",
					HttpStatusConstants.NO_CONTENT.id, null));
		}

		ChangeReferralStatusResponse changeReferralStatusResponse = employeeReferalService
				.setReferralStatus(referralStatusRequestModel);
		return ResponseEntity.ok(new HttpResponseModel(
				"Status Changed for Candidate : " + changeReferralStatusResponse.getApplicant_name(),
				HttpStatusConstants.OK.id, changeReferralStatusResponse));
	}

	// referral status list

	@RequestMapping(value = "/getReferralStatusList", method = RequestMethod.GET)
	@ApiOperation(value = "Get List of Referral Status")
	public ResponseEntity<?> getReferralStatusList() {
		List<ReferralStatus> referralStatusList = null;
		try {
			referralStatusList = employeeReferalService.getReferralStatusList();
			return ResponseEntity.ok(new HttpResponseModel("Referral Status list fetched Successfully",
					HttpStatusConstants.OK.id, referralStatusList));
		} catch (CustomException e) {

			return ResponseEntity.ok(new HttpResponseModel(e.getMessage(), e.getId(), referralStatusList));

		}

		/*
		 * return ResponseEntity.ok( new
		 * HttpResponseModel("Referral Status list fetched",
		 * HttpStatusConstants.OK.id, referralStatusList));
		 */
	}

	// get list for join candidate
	@RequestMapping(value = "/getJoinCandidatelist", method = RequestMethod.GET)
	@ApiOperation(value = "Get Join Candidate list ")
	public ResponseEntity<?> getJoinCandidateList() {

		List<CandidateJoinResponseModel> candidateJoinResponseModels = null;

		try {
			candidateJoinResponseModels = employeeReferalService.getJoinCandidateList();
			return ResponseEntity.ok(new HttpResponseModel("Joined Candidate list featched Successfully",
					HttpStatusConstants.OK.id, candidateJoinResponseModels));
		} catch (CustomException e) {

			return ResponseEntity.ok(new HttpResponseModel(e.getMessage(), HttpStatusConstants.INTERNAL_SERVER_ERROR.id,
					candidateJoinResponseModels));
		}

	}

}
