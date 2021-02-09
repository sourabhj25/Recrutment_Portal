package com.rmportal.service;

import com.rmportal.requestModel.LoginRequestModel;
import com.rmportal.responseModel.LoginResponseModel;
import com.rmportal.utility.CustomException;

/**
 * @author saurabh
 *
 */
public interface LoginServices {

	public LoginResponseModel validateUser(LoginRequestModel loginRequestModel) throws CustomException;

}
