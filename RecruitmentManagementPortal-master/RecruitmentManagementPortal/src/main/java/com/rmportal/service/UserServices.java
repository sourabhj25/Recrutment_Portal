package com.rmportal.service;

import java.util.List;

import com.rmportal.model.User;
import com.rmportal.requestModel.ChangePasswordModel;
import com.rmportal.requestModel.ResetPasswordModel;
import com.rmportal.requestModel.UpdateRequestModel;
import com.rmportal.responseModel.UpdateResponseModel;
import com.rmportal.responseModel.UserResponseDTO;
import com.rmportal.utility.CustomException;

/**
 * @author tejas
 *
 */

public interface UserServices {

	public User findUserByEmail(String email);

	public UserResponseDTO saveUser(User user) throws CustomException;

	public User FindById(long id);

	public User updateUser(int id, UpdateRequestModel updateRequestModel) throws CustomException;
	
	public List<User> getAllUsers() throws CustomException;
	
	public boolean updateStatus(boolean status, String email) throws CustomException;  
	 
	public boolean validateUserToken(int userid, String token) throws CustomException;
	
	public boolean forgetPassword(String email) throws CustomException;
	
	public boolean resetPassword(ResetPasswordModel resetPasswordModel) throws CustomException;
	
	public boolean changePassword(ChangePasswordModel changePasswordModel) throws CustomException;
	
	public UpdateResponseModel getDetails(int user_id) throws CustomException;
	
}
