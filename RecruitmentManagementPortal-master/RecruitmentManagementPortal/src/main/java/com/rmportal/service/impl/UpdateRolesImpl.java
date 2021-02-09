package com.rmportal.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rmportal.model.Role;
import com.rmportal.model.User;
import com.rmportal.repository.RoleRepository;
import com.rmportal.repository.UserRepository;
import com.rmportal.requestModel.UpdateRoleRequestModel;
import com.rmportal.responseModel.RoleResponseModel;
import com.rmportal.service.UpdateRolesService;
import com.rmportal.utility.ConversionUtility;
import com.rmportal.utility.CustomException;
import com.rmportal.utility.UserUtility;

/**
 * @author saurabh
 *
 */
@Service
@Transactional
public class UpdateRolesImpl implements UpdateRolesService {

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	ConversionUtility conversionUtility;

	@Autowired
	UserRepository userRepository;

	@Override
	public List<RoleResponseModel> getAllRoles() throws CustomException {

		List<Role> roles = (List<Role>) roleRepository.findAll();
		if(roles.isEmpty()){
			throw new CustomException(204, "Roles list is empty");
		}
		return conversionUtility.convertToRoleResponseModel(roles);

	}

	@Override
	public List<String> getAllRoleTypes() throws CustomException {
		List<Role> roles = (List<Role>) roleRepository.findAll();
		return roles.stream().map(i -> i.getRole()).collect(Collectors.toList());
	}

	@Override
	public String changeRole(UpdateRoleRequestModel updateRoleRequestModel) throws CustomException {

		String email=updateRoleRequestModel.getEmail();
		if(!UserUtility.isValidEmail(email)){
			throw new CustomException(203,"Invalid email id");
		}
		
		User user = userRepository.findByEmail(updateRoleRequestModel.getEmail());
		if (Objects.isNull(user)) {
			throw new CustomException(203, "Email id does not exist");
		}

		Role role = roleRepository.findOne(updateRoleRequestModel.getRole_id());

		if (Objects.isNull(role)) {
			throw new CustomException(203, "Invalid role id");
		}
		user.setRoles(role);
		return "Role Updated Successfully";
	}

}
