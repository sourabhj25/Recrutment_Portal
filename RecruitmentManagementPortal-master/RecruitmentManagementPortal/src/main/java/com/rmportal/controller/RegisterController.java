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
import com.rmportal.model.User;
import com.rmportal.requestModel.RegisterRequestModel;
import com.rmportal.requestModel.UpdateRequestModel;
import com.rmportal.responseModel.HttpResponseModel;
import com.rmportal.responseModel.UpdateResponseModel;
import com.rmportal.responseModel.UserResponseDTO;
import com.rmportal.service.UserServices;
import com.rmportal.utility.ApplicationUtils;
import com.rmportal.utility.ConversionUtility;
import com.rmportal.utility.CustomException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author tejas
 *
 */
@RestController
@Api(value = "Registration Controller", description = "Registration Process")
@CrossOrigin("*")
public class RegisterController {

	@Autowired
	private UserServices userService;

	@Autowired
	private ConversionUtility conversionUtility;

	@Autowired
	private ApplicationUtils applicationUtils;

	// Registration API
	@RequestMapping(value = "/registration", method = RequestMethod.POST, consumes = "application/json")
	@ApiOperation(value = "User Registration")
	public ResponseEntity<?> registeration(@RequestBody @Valid RegisterRequestModel registerRequestModel,
			BindingResult bindingResult) throws CustomException {

		try {
			if (bindingResult.hasErrors()) {
				throw new CustomException(204, bindingResult.getAllErrors().get(0).getDefaultMessage());
			}
			applicationUtils.validateEntity(registerRequestModel, bindingResult);
		} catch (Exception e) {
			throw new CustomException(201, e.getMessage());
		}

		User user = conversionUtility.convertRequestToUser(registerRequestModel);
		UserResponseDTO httpResponseModel = null;

		try {
			httpResponseModel = userService.saveUser(user);
			return ResponseEntity.ok(new HttpResponseModel("Registered successfully, please check your email",
					HttpStatusConstants.OK.id, httpResponseModel));

		} catch (CustomException e) {

			return ResponseEntity
					.ok(new HttpResponseModel(e.getMessage(), HttpStatusConstants.INTERNAL_SERVER_ERROR.id, null));
		}

	}

	// UpdateUser API
	@RequestMapping(value = "/updateUser/{id}", method = RequestMethod.POST, consumes = "application/json")
	@ApiOperation(value = "Update User")
	public ResponseEntity<?> updateUser(@PathVariable("id") int id,
			@Valid @RequestBody UpdateRequestModel updateRequestModel, BindingResult bindingResult)
			throws CustomException {

		try {
			if (bindingResult.hasErrors()) {
				throw new CustomException(204, bindingResult.getAllErrors().get(0).getDefaultMessage());
			}
			applicationUtils.validateEntity(updateRequestModel, bindingResult);
		} catch (Exception e) {
			throw new CustomException(201, e.getMessage());
		}

		try {
			User user = userService.updateUser(id, updateRequestModel);
			UpdateResponseModel updateUserResponse = conversionUtility.convertForUpdateResponse(user);
			return ResponseEntity.ok(
					new HttpResponseModel("User updated successfully", HttpStatusConstants.OK.id, updateUserResponse));
		} catch (CustomException e) {
			return ResponseEntity.ok(new HttpResponseModel(e.getMessage(), e.getId(), null));
		}

	}

	// Process Activation link while Registration
	@RequestMapping(value = "/activation/{userId}/{token}", method = RequestMethod.GET)
	@ApiOperation(value = "Validate Token")
	public ResponseEntity<?> validateToken(@PathVariable("token") String token, @PathVariable("userId") int userId)
			throws CustomException {

		if (userService.validateUserToken(userId, token)) {
			return ResponseEntity.ok(new HttpResponseModel("User Activated", HttpStatusConstants.OK.id, null));
		}

		return ResponseEntity
				.ok(new HttpResponseModel("Invalid Token", HttpStatusConstants.INTERNAL_SERVER_ERROR.id, null));

	}

	// List of Users API
	@RequestMapping(value = "/getUsers", method = RequestMethod.GET)
	@ApiOperation(value = "Get List of Users")
	public ResponseEntity<?> listAllUsers() {
		List<User> users = null;
		try {
			users = userService.getAllUsers();
			return ResponseEntity
					.ok(new HttpResponseModel("User list fetched successfully", HttpStatusConstants.OK.id, users));
		} catch (CustomException e) {
			return ResponseEntity.ok(new HttpResponseModel(e.getMessage(), e.getId(), null));
		}
	}

	// UserStatus Activation/Deactivation API through page
	@RequestMapping(value = "/updateStatus/{status}", method = RequestMethod.GET)
	@ApiOperation(value = "Activation/Deactivation")
	public ResponseEntity<?> updateStatus(@PathVariable boolean status, @RequestParam(required = true) String email) {

		try {
			return ResponseEntity.ok(new HttpResponseModel("User status updated successfully",
					HttpStatusConstants.OK.id, userService.updateStatus(status, email)));
		} catch (CustomException e) {
			return ResponseEntity.ok(new HttpResponseModel(e.getMessage(), e.getId(), null));
		}

	}

	// Get User Details
	@RequestMapping(value = "/getDetails/{user_id}", method = RequestMethod.GET)
	@ApiOperation(value = "Get User Details")
	public ResponseEntity<?> getDetails(@PathVariable("user_id") int user_id) {

		UpdateResponseModel updateResponseModel = null;

		try {
			updateResponseModel = userService.getDetails(user_id);
		} catch (CustomException e) {
			return ResponseEntity.ok(new HttpResponseModel(e.getMessage(), HttpStatusConstants.INTERNAL_SERVER_ERROR.id,
					updateResponseModel));
		}

		return ResponseEntity.ok(new HttpResponseModel("User details fetched successfully", HttpStatusConstants.OK.id,
				updateResponseModel));

	}

}
