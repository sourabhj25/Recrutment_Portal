package com.rmportal.service.impl;

import java.util.Date;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rmportal.constants.HttpStatusConstants;
import com.rmportal.model.User;
import com.rmportal.repository.RoleRepository;
import com.rmportal.repository.UserRepository;
import com.rmportal.requestModel.LoginRequestModel;
import com.rmportal.responseModel.LoginResponseModel;
import com.rmportal.service.LoginServices;
import com.rmportal.utility.ConversionUtility;
import com.rmportal.utility.CustomException;
import com.rmportal.utility.UserUtility;

/**
 * @author saurabh
 *
 */

@Service
public class LoginServiceImpl implements LoginServices {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ConversionUtility conversionUtility;

	@Autowired
	private PasswordEncoder bCryptPassword;

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public LoginResponseModel validateUser(LoginRequestModel loginRequestModel) throws CustomException {

		if(!UserUtility.isValidEmail(loginRequestModel.getEmail())){
			throw new CustomException(204, "Invalid email id");
		}
		User user = userRepository.findByEmail(loginRequestModel.getEmail());
		if (Objects.isNull(user)) {
			throw new CustomException(HttpStatusConstants.NO_CONTENT.id, "Invalid Email id or Password");
		}
		
		if (user.isActive()) {

			if (bCryptPassword.matches(loginRequestModel.getPassword(), user.getPassword())) {
				
				return conversionUtility.convertUserToLoginResponse(user);

			} else {
				throw new CustomException(401, "Invalid Email id or Password");
			}
		} else {
			throw new CustomException(406, "User is Inactive");
		}
	}

}
