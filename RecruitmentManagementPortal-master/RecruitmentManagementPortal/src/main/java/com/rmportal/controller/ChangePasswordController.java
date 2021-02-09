package com.rmportal.controller;

import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rmportal.constants.HttpStatusConstants;
import com.rmportal.requestModel.ChangePasswordModel;
import com.rmportal.responseModel.HttpResponseModel;
import com.rmportal.service.UserServices;
import com.rmportal.utility.ApplicationUtils;
import com.rmportal.utility.CustomException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author saurabh Controller for Login, Forgot Password and Reset Password
 */

@RestController
@Api(value = "User Upgradation", description = "Change Details from Profile Page")
@CrossOrigin("*")
public class ChangePasswordController {

	@Autowired
	private UserServices userService;

	@Autowired
	private ApplicationUtils applicationUtils;

	// Change Password Controller
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	@ApiOperation(value = "Change Password")

	public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordModel changePasswordModel,
			BindingResult bingingResult) throws CustomException {

		if (Objects.isNull(changePasswordModel)) {
			throw new CustomException(205, "Mandatory fields can't be Empty");
		}

		try {
			if (bingingResult.hasErrors()) {
				throw new CustomException(204, bingingResult.getAllErrors().get(0).getDefaultMessage());
			}
			applicationUtils.validateEntity(changePasswordModel, bingingResult);
		} catch (Exception e) {
			throw new CustomException(201, e.getMessage());
		}

		if (userService.changePassword(changePasswordModel)) {
			return ResponseEntity.ok(new HttpResponseModel("Password Changed", HttpStatusConstants.OK.id, null));
		}

		return ResponseEntity.ok(new HttpResponseModel("Unable to change the password, please try again.",
				HttpStatusConstants.INTERNAL_SERVER_ERROR.id, null));

	}
}
