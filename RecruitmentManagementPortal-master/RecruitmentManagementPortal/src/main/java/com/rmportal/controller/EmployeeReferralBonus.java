package com.rmportal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rmportal.constants.HttpStatusConstants;
import com.rmportal.requestModel.SetBonusRequestModel;
import com.rmportal.responseModel.EmployeeBonusStatusResponseModel;
import com.rmportal.responseModel.HttpResponseModel;
import com.rmportal.service.EmployeeBonusService;
import com.rmportal.utility.CustomException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "Employee Referral Bonus", description = "Bonus for the Referred Employee")
@CrossOrigin("*")
public class EmployeeReferralBonus {

	@Autowired
	private EmployeeBonusService employeeBonusService;

	// Get referal bonus for individual candidate
	/**
	 * Show the bonus for the referred employee by respective referral ID.<br>
	 * Throws exception {@link CustomException} if invalid ID is entered.
	 * 
	 * @param referal_id
	 * @return bonus amount and details for particular candidate(with respect to referral ID)
	 */
	@RequestMapping(value = "/getReferralBonus/{referal_id}", method = RequestMethod.GET)
	@ApiOperation(value = "Get the Referral Bonus Status for the Employee")
	public ResponseEntity<?> getReferralBonus(@PathVariable int referal_id) {

		EmployeeBonusStatusResponseModel employeeBonusResponse;
		try {
			if (referal_id <= 0) {
				throw new CustomException(401, "Request cannot be proceeded");
			}
			employeeBonusResponse = employeeBonusService.getReferralBonus(referal_id);
		} catch (CustomException e) {
			return ResponseEntity.ok(new HttpResponseModel(e.getMessage(), 204, "No Bonus"));

		}

		return ResponseEntity
				.ok(new HttpResponseModel(HttpStatus.OK.name(), HttpStatusConstants.OK.id, employeeBonusResponse));

	}

	// Set Bonus for the candidate
	@RequestMapping(value = "/setBonus", method = RequestMethod.POST)
	@ApiOperation(value = "Set Bonus for the individual candidate")
	public ResponseEntity<?> setBonus(@RequestBody SetBonusRequestModel setBonusRequestModel) throws CustomException {
		String message = employeeBonusService.setBonus(setBonusRequestModel);

		return ResponseEntity.ok(new HttpResponseModel(message, HttpStatusConstants.OK.id, null));
	}

}
