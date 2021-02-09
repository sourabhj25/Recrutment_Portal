package com.rmportal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rmportal.constants.HttpStatusConstants;
import com.rmportal.requestModel.UpdateRoleRequestModel;
import com.rmportal.responseModel.HttpResponseModel;
import com.rmportal.responseModel.RoleResponseModel;
import com.rmportal.service.UpdateRolesService;
import com.rmportal.utility.CustomException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author saurabh
 *
 */
@RestController
@Api(value = "Update Roles", description = "Change the User Role")
@CrossOrigin("*")
public class UpdateRolesController {

	@Autowired
	private UpdateRolesService updateRolesService;

	// Get user Roles
	@RequestMapping(value = "/getRoles", method = RequestMethod.GET)
	@ApiOperation(value = "User Registration")
	public ResponseEntity<?> getRoles() throws CustomException {
		List<RoleResponseModel> roles = updateRolesService.getAllRoles();
		
		return ResponseEntity.ok(new HttpResponseModel("Role list fetched successfully", HttpStatusConstants.OK.id, roles));

	}

	// Update User Role
	@RequestMapping(value = "/updateUserRole", method = RequestMethod.POST)
	@ApiOperation(value = "User Registration")
	public ResponseEntity<?> updateUserRole(@RequestBody UpdateRoleRequestModel updateRoleRequestModel)
			throws CustomException {

		String message = updateRolesService.changeRole(updateRoleRequestModel);
		return ResponseEntity.ok(new HttpResponseModel(message, HttpStatusConstants.OK.id, null));
	}

}
