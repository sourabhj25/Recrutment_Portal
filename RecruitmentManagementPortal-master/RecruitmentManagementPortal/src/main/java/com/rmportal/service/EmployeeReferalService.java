package com.rmportal.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.rmportal.model.EmployeeReferal;
import com.rmportal.model.ReferralStatus;
import com.rmportal.requestModel.ReferralStatusRequestModel;
import com.rmportal.requestModel.UploadResumeRequestModel;
import com.rmportal.responseModel.CandidateJoinResponseModel;
import com.rmportal.responseModel.ChangeReferralStatusResponse;
import com.rmportal.responseModel.EmployeeReferalResponseModel;
import com.rmportal.responseModel.UploadResumeResponseModel;
import com.rmportal.utility.CustomException;

/**
 * @author saurabh
 *
 */
public interface EmployeeReferalService {

	/*
	 * public EmployeeReferalResponseModel getEmployeeDetails(int referal_id)
	 * throws CustomException;
	 */

	public List<EmployeeReferalResponseModel> getEmployeeDetails(String referance_email) throws CustomException;

	public UploadResumeResponseModel addResume(UploadResumeRequestModel uploadResumeRequestModel, MultipartFile file)
			throws CustomException;

	public EmployeeReferal fetchResume(int job_vacancy_id) throws CustomException;

	public List<EmployeeReferalResponseModel> getEmployeeReferalList() throws CustomException;
	
	public ChangeReferralStatusResponse setReferralStatus(ReferralStatusRequestModel referralStatusRequestModel) throws CustomException;
	
	public List<ReferralStatus> getReferralStatusList() throws CustomException ;
	
	public List<CandidateJoinResponseModel> getJoinCandidateList()throws CustomException;

}
