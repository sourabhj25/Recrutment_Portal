package com.rmportal.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rmportal.constants.HttpStatusConstants;
import com.rmportal.requestModel.LoginRequestModel;
import com.rmportal.requestModel.ResetPasswordModel;
import com.rmportal.responseModel.HttpResponseModel;
import com.rmportal.responseModel.LoginResponseModel;
import com.rmportal.service.LoginServices;
import com.rmportal.service.UserServices;
import com.rmportal.utility.ApplicationUtils;
import com.rmportal.utility.CustomException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author saurabh/tejas Controller for Login, Forgot Password and Reset
 *         Password
 */

@RestController
@Api(value = "Login Controller", description = "Login details")
@CrossOrigin("*")
public class LoginController {

	@Autowired
	private LoginServices loginService;

	@Autowired
	private UserServices userService;

	@Autowired
	private GlobalExceptionHandler globalException;

	@Autowired
	private ApplicationUtils applicationUtils;

	// Login Controller
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ApiOperation(value = "Login Controller")
	public ResponseEntity<?> login(HttpServletRequest request, HttpServletResponse response,
			@Valid @RequestBody LoginRequestModel loginRequestModel, BindingResult bindingResult)
			throws CustomException {

		try {
			if (bindingResult.hasErrors()){
				throw new CustomException(204, bindingResult.getAllErrors().get(0).getDefaultMessage());
			}
			applicationUtils.validateEntity(loginRequestModel, bindingResult);
		} catch (Exception e) {
			throw new CustomException(201, e.getMessage());
		}

		LoginResponseModel responseModel = null;

		try {
			responseModel = loginService.validateUser(loginRequestModel);
		} catch (CustomException e) {
			return ResponseEntity.ok(new HttpResponseModel(e.getMessage(), e.getId(), null));
		}
		return ResponseEntity.ok(new HttpResponseModel("Login Successfully", HttpStatusConstants.OK.id, responseModel));

	}

	// Forget Password Controller
	@RequestMapping(value = "/forgetPassword", method = RequestMethod.GET)
	@ApiOperation(value = "Forget Password")
	public ResponseEntity<?> forgetPassword(@RequestParam("email") String email) throws CustomException {

		if (userService.forgetPassword(email)) {
			return ResponseEntity.ok(new HttpResponseModel("Reset password link sent, please check your email",
					HttpStatusConstants.OK.id, null));
		}

		return ResponseEntity
				.ok(new HttpResponseModel("Invalid Email", HttpStatusConstants.INTERNAL_SERVER_ERROR.id, null));
	}

	// Reset Password Controller
	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	@ApiOperation(value = "Reset Password")
	public ResponseEntity<?> resetPassword(HttpServletRequest request, HttpServletResponse response,
			@Valid @RequestBody ResetPasswordModel resetPasswordModel, BindingResult bindingResult)
			throws CustomException {

		try {
			if (bindingResult.hasErrors()){
				throw new CustomException(204, bindingResult.getAllErrors().get(0).getDefaultMessage());
			}
			applicationUtils.validateEntity(resetPasswordModel, bindingResult);
		} catch (Exception e) {
			throw new CustomException(201, e.getMessage());
		}

		if (userService.resetPassword(resetPasswordModel)) {
			return ResponseEntity
					.ok(new HttpResponseModel("Password changed successfully", HttpStatusConstants.OK.id, null));
		}

		return ResponseEntity.ok(
				new HttpResponseModel("Invalid token or user id", HttpStatusConstants.INTERNAL_SERVER_ERROR.id, null));

	}
}
