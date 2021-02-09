package com.rmportal.service.impl;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmportal.model.EmployeeReferal;
import com.rmportal.model.User;
import com.rmportal.repository.EmployeeReferalRepository;
import com.rmportal.repository.UserRepository;
import com.rmportal.requestModel.SetBonusRequestModel;
import com.rmportal.responseModel.EmployeeBonusStatusResponseModel;
import com.rmportal.service.EmployeeBonusService;
import com.rmportal.utility.ConversionUtility;
import com.rmportal.utility.CustomException;
import com.rmportal.utility.UserUtility;

@Service
public class EmployeeBonusImpl implements EmployeeBonusService {

	@Autowired
	private EmployeeReferalRepository employeeReferalRepo;

	@Autowired
	private ConversionUtility conversionUtility;

	@Autowired
	private UserRepository userRepository;

	// get candidate bonus
	@Override
	public EmployeeBonusStatusResponseModel getReferralBonus(int referal_id) throws CustomException {

		EmployeeReferal employeeReferal = employeeReferalRepo.findOne(referal_id);
		if (Objects.isNull(employeeReferal)) {
			throw new CustomException(204, " No Content Found for Specified Referral Id");
		}

		if (employeeReferal.getApplication_status().compareTo("Joined") != 0) {
			throw new CustomException(206, "Bonus is not applicable as candidate not yet joined the organization");
		}
		return conversionUtility.calculateBonus(employeeReferal);

	}

	// set employee bonus for the referred candidate
	@Override
	public String setBonus(SetBonusRequestModel setBonusRequestModel) throws CustomException {

		EmployeeReferal employeeReferal = employeeReferalRepo.findOne(setBonusRequestModel.getReferral_id());
		if (Objects.isNull(employeeReferal)) {
			throw new CustomException(204, "Invalid Referral Id");
		}
		/*
		 * if (employeeReferal.getApplication_status().compareTo("Joined") != 0)
		 * { throw new CustomException(206, " Candidate is Still not Joined"); }
		 */

		String applicantEmail = setBonusRequestModel.getApplicant_email();
		if (!UserUtility.isValidEmail(applicantEmail)) {
			throw new CustomException(204, "Invalid Applicant Email Id");
		}

		User user = userRepository.findByEmail(setBonusRequestModel.getApplicant_email());
		if (Objects.isNull(user)) {
			throw new CustomException(204, "User does not exist");
		}
		if (!user.isActive()) {
			throw new CustomException(401, "User is in-active");
		}

		return conversionUtility.setBonusConversion(employeeReferal, setBonusRequestModel);
	}
}
