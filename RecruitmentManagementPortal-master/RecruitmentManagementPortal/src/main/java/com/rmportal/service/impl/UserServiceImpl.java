package com.rmportal.service.impl;

import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rmportal.constants.UserTokenType;
import com.rmportal.model.Department;
import com.rmportal.model.Role;
import com.rmportal.model.User;
import com.rmportal.model.UserToken;
import com.rmportal.repository.DepartmentRepository;
import com.rmportal.repository.RoleRepository;
import com.rmportal.repository.UserRepository;
import com.rmportal.repository.UserTokenRepository;
import com.rmportal.requestModel.ChangePasswordModel;
import com.rmportal.requestModel.ResetPasswordModel;
import com.rmportal.requestModel.UpdateRequestModel;
import com.rmportal.responseModel.UpdateResponseModel;
import com.rmportal.responseModel.UserResponseDTO;
import com.rmportal.service.UserServices;
import com.rmportal.utility.ActivationEmailUtility;
import com.rmportal.utility.ConversionUtility;
import com.rmportal.utility.CustomException;
import com.rmportal.utility.ForgetPasswordEmailUtility;
import com.rmportal.utility.PasswordEncryption;
import com.rmportal.utility.UserUtility;

/**
 * @author tejas
 *
 */

@Service
public class UserServiceImpl implements UserServices {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private ConversionUtility conversionUtility;

	@Autowired
	private DepartmentRepository departmentRepo;

	@Autowired
	private ActivationEmailUtility activationEmailUtility;

	@Autowired
	private UserTokenRepository userTokenRepository;

	@Autowired
	private ForgetPasswordEmailUtility forgotPasswordEmailUtility;

	@Autowired
	private PasswordEncryption passwordEncryption;

	@Autowired
	private PasswordEncoder bCryptPassword;

	@Autowired
	private DepartmentRepository departmentRepository;

	@Override
	public User findUserByEmail(String email) {
		return null;
	}

	/*
	 * public boolean isValidEmail(String email) { Pattern emailPattern =
	 * Pattern.compile(
	 * "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$",
	 * Pattern.CASE_INSENSITIVE);
	 * 
	 * Matcher m = emailPattern.matcher(email);
	 * 
	 * return m.matches();
	 * 
	 * }
	 */
	@Override
	public UserResponseDTO saveUser(User registerRequestModel) throws CustomException {

		/*
		 * if(registerRequestModel.getFirstName()==null){ throw new
		 * CustomException(HttpStatus.BAD_REQUEST.value()
		 * ,"FirstName Cannot be Null"); }
		 */

		registerRequestModel.setActive(false);

		Role userRole = roleRepository.findOne(1);

		if (Objects.isNull(userRole)) {
			throw new CustomException(HttpStatus.NOT_FOUND.value(), "No Role is Assign ");
		}

		/*
		 * if (UserUtility.isInvalidValue(registerRequestModel.getFirstName())
		 * || UserUtility.isInvalidValue(registerRequestModel.getLastName()) ||
		 * UserUtility.isInvalidValue(registerRequestModel.getEmail()) ||
		 * UserUtility.isInvalidValue(registerRequestModel.getPassword())){
		 * 
		 * throw new CustomException(HttpStatusConstants.BAD_REQUEST.getId(),
		 * "Mandatory Feilds Cannot be Empty"); }
		 */

		if (UserUtility.isValidEmail(registerRequestModel.getEmail())) {

			String[] str = registerRequestModel.getEmail().split("@");

			if (str[1].compareTo("yopmail.com") == 0) {

				User user = userRepository.findByEmail(registerRequestModel.getEmail());

				/*
				 * String email=null; if(UserUtility.isValidEmail(email)){ throw
				 * new CustomException(HttpStatus.NOT_FOUND.value(),
				 * "Invalid email address"); }
				 */

				if (Objects.nonNull(user)) {
					throw new CustomException(HttpStatus.NOT_FOUND.value(), "User already exists");
				}

				registerRequestModel.setRoles(userRole);

				Department dept = departmentRepository.findOne(1);
				registerRequestModel.setDepartments(dept);

				user = userRepository.save(registerRequestModel);
				activationEmailUtility.sendMail(user);

				return conversionUtility.convertUserToresponse(user);
			} else {
				throw new CustomException(HttpStatus.BAD_REQUEST.value(), "Invalid email address");

			}

		} else {
			throw new CustomException(204, "Invalid email address");
		}

	}

	@Override
	public User FindById(long id) {
		return null;
	}

	@Override
	public User updateUser(int id, UpdateRequestModel updateRequestModel) throws CustomException {

		User updatedUser = userRepository.findByUserId(id);
		Department department = departmentRepo.findByName(updateRequestModel.getDept_name());

		if (Objects.isNull(updatedUser)) {
			throw new CustomException(HttpStatus.NOT_FOUND.value(), "User does not exists");
		}

		if (Objects.nonNull(updateRequestModel)) {

			if (StringUtils.isBlank(updateRequestModel.getFirstName())) {
				throw new CustomException(HttpStatus.NOT_FOUND.value(), "First name can not be empty");
			} else if (!UserUtility.isValidName(updateRequestModel.getFirstName())) {
				throw new CustomException(HttpStatus.NOT_FOUND.value(), "Invalid firstName");
			} else {
				updatedUser.setFirstName(updateRequestModel.getFirstName());
			}

			if (StringUtils.isBlank(updateRequestModel.getLastName())) {
				throw new CustomException(HttpStatus.NOT_FOUND.value(), "Last name can not be empty");
			} else if (!UserUtility.isValidName(updateRequestModel.getLastName())) {
				throw new CustomException(HttpStatus.NOT_FOUND.value(), "Invalid lastName");
			} else {
				updatedUser.setLastName(updateRequestModel.getLastName());
			}

			updatedUser.setEmployee_id("Not Set");

			if (StringUtils.isBlank(updateRequestModel.getAddress())) {
				throw new CustomException(HttpStatus.NOT_FOUND.value(), "Mandatory Feilds Cannot be Empty");
			} else {
				updatedUser.setAddress(updateRequestModel.getAddress());
			}

			if (StringUtils.isBlank(updateRequestModel.getBlood_group())) {
				throw new CustomException(HttpStatus.NOT_FOUND.value(), "Mandatory Feilds Cannot be Empty");
			} else {
				updatedUser.setBlood_group(updateRequestModel.getBlood_group());
			}

			if (StringUtils.isBlank(updateRequestModel.getCity())) {
				throw new CustomException(HttpStatus.NOT_FOUND.value(), "Mandatory Feilds Cannot be Empty");
			} else {
				updatedUser.setCity(updateRequestModel.getCity());
			}

			if (StringUtils.isBlank(updateRequestModel.getCountry())) {
				throw new CustomException(HttpStatus.NOT_FOUND.value(), "Mandatory Feilds Cannot be Empty");
			} else {
				updatedUser.setCountry(updateRequestModel.getCountry());
			}

			if (!UserUtility.isValidMobile(updateRequestModel.getMobile())) {
				throw new CustomException(HttpStatus.NOT_FOUND.value(), "Mandatory Feilds Cannot be Empty");
			} else {
				updatedUser.setMobile(updateRequestModel.getMobile());
			}

			if (StringUtils.isBlank(updateRequestModel.getDateOfBirth())) {
				throw new CustomException(HttpStatus.NOT_FOUND.value(), "Mandatory Feilds Cannot be Empty");
			} else {
				updatedUser.setDOB(updateRequestModel.getDateOfBirth());
			}

			// updatedUser.setEmployee_id(updateRequestModel.getEmployee_id());
			updatedUser.setEmployee_id("AGSxxx"); // temporary value set for employee id
			
			if (Objects.isNull(department)) {
				throw new CustomException(204, "Invalid Department");
			}
			updatedUser.setDepartments(department);

			userRepository.save(updatedUser);
		} else {
			throw new CustomException(HttpStatus.BAD_REQUEST.value(), "User already exits");
		}

		return updatedUser;
	}

	@Override
	public List<User> getAllUsers() throws CustomException {

		List<User> getUsers = (List<User>) userRepository.findAll();
		if (Objects.isNull(getUsers) || getUsers.isEmpty()) {
			throw new CustomException(HttpStatus.NOT_FOUND.value(), "No Users Are Presents");
		}
		return getUsers;
	}

	@Override
	public boolean updateStatus(boolean status, String email) throws CustomException {

		User user = userRepository.findByEmail(email);
		if (!UserUtility.isValidEmail(email)) {
			throw new CustomException(204, "Invalid email address");
		}

		if (Objects.isNull(user)) {
			throw new CustomException(HttpStatus.NOT_FOUND.value(), "User does not exists");
		}
		if (user.isActive() && status) {
			throw new CustomException(HttpStatus.NOT_FOUND.value(), "User alerady activated");
		}
		if (!user.isActive() && !status) {
			throw new CustomException(HttpStatus.NOT_FOUND.value(), "User already deactivated");
		}
		user.setActive(status);
		userRepository.save(user);
		return user.isActive();

	}

	public boolean validateUserToken(int userId, String token) throws CustomException {

		if (StringUtils.isEmpty(token)) {
			throw new CustomException(500, "Activation Token can't be empty");
		} else {
			UserToken tokenObj = userTokenRepository.findByTokenValue(token);
			if (Objects.isNull(tokenObj)) {
				throw new CustomException(500, "Invalid token or user id");
			}

			/*
			 * if
			 * (tokenObj.getTokenType().compareTo(UserTokenType.ADD_USER.name())
			 * != 0) { throw new CustomException(500,
			 * "Activation Token Mismatch or Expired. Please contact Admin"); }
			 */

			User user = userRepository.findByUserId(userId);
			if (Objects.isNull(user)) {
				throw new CustomException(500, "Invalid token or user id");
			}
			user.setActive(true);
			userTokenRepository.delete(tokenObj);
			return true;
		}

	}

	// Forget Password
	@Override
	public boolean forgetPassword(String email) throws CustomException {
		User user = userRepository.findByEmail(email);
		if (!UserUtility.isValidEmail(email)) {
			throw new CustomException(204, "Invalid email address");
		}
		if (Objects.nonNull(user) && user.isActive()) {
			forgotPasswordEmailUtility.sendMail(user);
			return true;
		} else {
			throw new CustomException(213, "User does not exist, Please register.");

		}
	}

	// Reset Password
	@Override
	public boolean resetPassword(ResetPasswordModel resetPasswordModel) throws CustomException {

		if (StringUtils.isBlank(resetPasswordModel.getPassword())) {
			throw new CustomException(401, "Mandatory fields cannot be empty");
		}

		UserToken userToken = userTokenRepository.findByToken(resetPasswordModel.getUserId(),
				resetPasswordModel.getToken());

		if (Objects.nonNull(userToken)
				&& userToken.getTokenType().compareTo(UserTokenType.RESET_PASSWORD.name()) == 0) {
			User user = userRepository.findByUserId(userToken.getUser_id());

			if (Objects.isNull(user)) {
				throw new CustomException(500, "Invalid token or user id");
			}

			user.setPassword(passwordEncryption.hashEncoder(resetPasswordModel.getPassword()));
			userTokenRepository.delete(userToken);
			return true;
		} else {
			return false;
		}

	}

	// Change Password from Profile
	@Override
	public boolean changePassword(ChangePasswordModel changePasswordModel) throws CustomException {

		String changepassword = changePasswordModel.getEmail();
		if (!UserUtility.isValidEmail(changepassword)) {
			throw new CustomException(204, "Invalid email address");
		}
		User user = userRepository.findByEmail(changePasswordModel.getEmail());

		if (Objects.isNull(user)) {
			throw new CustomException(500, "User does not exists");
		}

		if (!user.isActive()) {
			throw new CustomException(202, "Password Cannot be changed please contact to Admin");
		}

		if (!bCryptPassword.matches(changePasswordModel.getOldPassword(), user.getPassword())) {
			throw new CustomException(500, "Old Password did not match");
		}

		/*
		 * if (!changePasswordModel.getNewPassword().equals(changePasswordModel.
		 * getConfirmNewPassword())) throw new CustomException(500,
		 * "New Password mismatch. Cannot reset Password");
		 */

		user.setPassword(passwordEncryption.hashEncoder(changePasswordModel.getNewPassword()));
		return true;

	}

	@Override
	public UpdateResponseModel getDetails(int user_id) throws CustomException {
		User user = userRepository.findByUserId(user_id);
		if (Objects.isNull(user)) {
			throw new CustomException(500, "Invalid user id, unable to fetch user details");
		}
		return conversionUtility.convertForUpdateResponse(user);
	}
}
