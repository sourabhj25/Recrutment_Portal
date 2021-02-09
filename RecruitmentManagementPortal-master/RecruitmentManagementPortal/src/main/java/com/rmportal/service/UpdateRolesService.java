package com.rmportal.service;

import java.util.List;

import com.rmportal.requestModel.UpdateRoleRequestModel;
import com.rmportal.responseModel.RoleResponseModel;
import com.rmportal.utility.CustomException;

/**
 * @author saurabh
 *
 */
public interface UpdateRolesService {

	public List<RoleResponseModel> getAllRoles() throws CustomException;

	public List<String> getAllRoleTypes() throws CustomException;

	public String changeRole(UpdateRoleRequestModel updateRoleRequestModel) throws CustomException;
}
