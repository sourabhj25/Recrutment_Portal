package com.rmportal.utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.Range;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.rmportal.constants.HttpStatusConstants;
import com.rmportal.model.EmployeeReferal;
import com.rmportal.model.JobVacancy;
import com.rmportal.model.Permission;
import com.rmportal.model.Role;
import com.rmportal.model.User;
import com.rmportal.repository.EmployeeReferalRepository;
import com.rmportal.requestModel.JobVacancyRequestModel;
import com.rmportal.requestModel.RegisterRequestModel;
import com.rmportal.requestModel.SetBonusRequestModel;
import com.rmportal.requestModel.UpdateRequestModel;
import com.rmportal.requestModel.UploadResumeRequestModel;
import com.rmportal.responseModel.CandidateJoinResponseModel;
import com.rmportal.responseModel.EmployeeBonusStatusResponseModel;
import com.rmportal.responseModel.EmployeeReferalResponseModel;
import com.rmportal.responseModel.JobVacancyResponseModel;
import com.rmportal.responseModel.LoginResponseModel;
import com.rmportal.responseModel.RoleResponseModel;
import com.rmportal.responseModel.UpdateResponseModel;
import com.rmportal.responseModel.UserPremissionModel;
import com.rmportal.responseModel.UserResponseDTO;

/**
 * @author tejas
 *
 */
@Component
public class ConversionUtility {

	@Autowired
	private PasswordEncryption passwordEncryption;

	@Autowired
	private CalculateDifferenceInDate calculateDifferenceInDate;

	@Autowired
	private EmployeeReferalRepository employeeReferalBonusrepo;

	// Registration
	public User convertRequestToUser(RegisterRequestModel registerRequestModel) throws CustomException {
		if (StringUtils.isBlank(registerRequestModel.getFirstName())
				&& StringUtils.isBlank(registerRequestModel.getLastName())
				&& StringUtils.isBlank(registerRequestModel.getEmail())
				&& StringUtils.isBlank(registerRequestModel.getPassword())) {

			throw new CustomException(HttpStatusConstants.BAD_REQUEST.getId(), "Mandatory Feilds Cannot be Empty");
		}

		if (!UserUtility.isValidName(registerRequestModel.getFirstName())) {
			throw new CustomException(204, "Invalid First Name");
		}

		if (!UserUtility.isValidName(registerRequestModel.getLastName())) {
			throw new CustomException(204, "Invalid Last Name");
		}

		/*
		 * && UserUtility.isValidfullName(registerRequestModel.getFirstName())
		 * && UserUtility.isValidfullName(registerRequestModel.getLastName())
		 */
		User user = new User();
		user.setEmail(registerRequestModel.getEmail());
		user.setFirstName(registerRequestModel.getFirstName());
		user.setLastName(registerRequestModel.getLastName());
		user.setCreatedAt(new Date());
		user.setPassword(passwordEncryption.hashEncoder(registerRequestModel.getPassword()));

		return user;
	}

	// Registration Response
	public UserResponseDTO convertUserToresponse(User user) {

		UserResponseDTO userResponseDTO = new UserResponseDTO();

		userResponseDTO.setEmail(user.getEmail());
		userResponseDTO.setFirstName(user.getFirstName());
		userResponseDTO.setLastName(user.getLastName());

		return userResponseDTO;

	}

	// Update Response for Update Profile
	public UpdateResponseModel convertForUpdateResponse(User user) {
		UpdateResponseModel updateResponseModel = new UpdateResponseModel();
		updateResponseModel.setEmployee_id(user.getEmployee_id());
		updateResponseModel.setFirstName(user.getFirstName());
		updateResponseModel.setLastName(user.getLastName());
		updateResponseModel.setAddress(user.getAddress());
		updateResponseModel.setCity(user.getCity());
		updateResponseModel.setCountry(user.getCountry());
		updateResponseModel.setDOB(user.getDOB());
		updateResponseModel.setMobile(user.getMobile());
		updateResponseModel.setEmail(user.getEmail());
		updateResponseModel.setDepartment(user.getDepartments());
		updateResponseModel.setBlood_group(user.getBlood_group());
		updateResponseModel.setRoles(user.getRoles().getRole());
		return updateResponseModel;
	}

	// Login Response
	public LoginResponseModel convertUserToLoginResponse(User userFromTable) {
		LoginResponseModel responseModel = new LoginResponseModel();
		responseModel.setEmail(userFromTable.getEmail());
		responseModel.setFirstName(userFromTable.getFirstName());
		responseModel.setLastName(userFromTable.getLastName());
		responseModel.setUser_id(userFromTable.getId());
		responseModel.setProfileStatus(false);
		responseModel.setPermissions(getPermission(userFromTable.getRoles().getRolePermission()));
		RoleResponseModel roleResponse = new RoleResponseModel();
		roleResponse.setId(userFromTable.getRoles().getId());
		roleResponse.setRole(userFromTable.getRoles().getRole());
		roleResponse.setPermissions(userFromTable.getRoles().getRolePermission());

		responseModel.setRoleResponse(roleResponse);
		String check = userFromTable.toString();
		if (check.matches(".*null.*")) {
			responseModel.setProfileStatus(false);
		} else {
			responseModel.setProfileStatus(true);
		}
		
		userFromTable.setLastLogin(new Date());
		return responseModel;

	}

	// getPermission method for above method(convertUserToLoginResponse)
	public UserPremissionModel getPermission(List<Permission> list) {
		UserPremissionModel model = new UserPremissionModel();

		for (Permission permission : list) {

			if (permission.getPremissionName().compareTo("AddOpenPosition") == 0) {

				model.setAddOpenPosition(true);

			}

			if (permission.getPremissionName().compareTo("UpdateOpenPosition") == 0) {
				model.setUpdateOpenPosition(true);
			}

			if (permission.getPremissionName().compareTo("ViewOpenPosition") == 0) {
				model.setViewOpenPosition(true);
			}

			if (permission.getPremissionName().compareTo("ChangeApplicationStatus") == 0) {
				model.setChangeApplicationStatus(true);
			}

			if (permission.getPremissionName().compareTo("DeactivateUser") == 0) {
				model.setDeactivateUser(true);
			}

			if (permission.getPremissionName().compareTo("AssignRole") == 0) {
				model.setAssignRole(true);
			}

			if (permission.getPremissionName().compareTo("ViewResumeStatus") == 0) {
				model.setViewResumeStatus(true);
			}
			if (permission.getPremissionName().compareTo("ViewBonus") == 0) {
				model.setViewBonus(true);
			}

			if (permission.getPremissionName().compareTo("AddReferral") == 0) {
				model.setAddReferral(true);
			}

			if (permission.getPremissionName().compareTo("AddBonusDetails") == 0) {
				model.setAddBonusDetails(true);
			}

			if (permission.getPremissionName().compareTo("UpdateBonusDetails") == 0) {
				model.setUpdateBonusDetails(true);
			}

		}
		return model;
	}

	// Activate user through profile
	public String setStatusToUser() {

		User user = new User();
		user.setActive(true);
		return "UserActivated";
	}

	// Method for Update Status
	public User convertRequestToUser(UpdateRequestModel updateRequestModel, User user) {

		System.out.println("firstname : " + updateRequestModel.getFirstName());
		user.setFirstName(updateRequestModel.getFirstName());
		user.setLastName(updateRequestModel.getLastName());
		user.setAddress(updateRequestModel.getAddress());
		user.setCity(updateRequestModel.getCity());
		user.setCountry(updateRequestModel.getCountry());
		user.setDOB(updateRequestModel.getDateOfBirth());
		user.setMobile(updateRequestModel.getMobile());
		user.setBlood_group(updateRequestModel.getBlood_group());
		return user;

	}

	// Method for List of Roles
	public List<RoleResponseModel> convertToRoleResponseModel(List<Role> roles) {
		List<RoleResponseModel> roleResponseList = new ArrayList<>();
		for (Role role : roles) {
			RoleResponseModel roleResponseModel = new RoleResponseModel();
			roleResponseModel.setId(role.getId());
			roleResponseModel.setRole(role.getRole());
			roleResponseList.add(roleResponseModel);
		}
		return roleResponseList;
	}

	// Add Job Vacancies
	public JobVacancy addJobVacancy(JobVacancyRequestModel jobVacancyRequestModel) {
		JobVacancy jobVacancy = new JobVacancy();
		jobVacancy.setJob_title(jobVacancyRequestModel.getJob_title());
		jobVacancy.setNumber_of_openings(jobVacancyRequestModel.getNumber_of_openings());
		jobVacancy.setExp_to(jobVacancyRequestModel.getExp_to());
		jobVacancy.setExp_from(jobVacancyRequestModel.getExp_from());
		jobVacancy.setJob_description(jobVacancyRequestModel.getJob_description());
		jobVacancy.setTechnical_skills(jobVacancyRequestModel.getTechnical_skills());
		jobVacancy.setJob_location(jobVacancyRequestModel.getJob_location());
		if (StringUtils.isBlank(jobVacancyRequestModel.getSalary_ctc())) {
			jobVacancy.setSalary_ctc("Not Disclosed");
		} else {
			jobVacancy.setSalary_ctc(jobVacancyRequestModel.getSalary_ctc());
		}
		jobVacancy.setEducation(jobVacancyRequestModel.getEducation());
		jobVacancy.setJob_type(jobVacancyRequestModel.getJob_type());
		jobVacancy.setActive(true);
		return jobVacancy;
	}

	// List of job vacancy
	public List<JobVacancyResponseModel> getAllJobVacancy(List<JobVacancy> jobVacancylist) {
		List<JobVacancyResponseModel> jobresponselist = new ArrayList<>();

		for (JobVacancy jobVacancy : jobVacancylist) {
			if (jobVacancy.isActive()) {
				JobVacancyResponseModel jobVacancyResponse = new JobVacancyResponseModel();
				jobVacancyResponse.setJob_vacancy_id(jobVacancy.getJob_vacancy_id());
				jobVacancyResponse.setJob_title(jobVacancy.getJob_title());
				jobVacancyResponse.setNumber_of_openings(jobVacancy.getNumber_of_openings());
				jobVacancyResponse.setExp_to(jobVacancy.getExp_to());
				jobVacancyResponse.setExp_from(jobVacancy.getExp_from());
				jobVacancyResponse.setJob_description(jobVacancy.getJob_description());
				jobVacancyResponse.setTechnical_skills(jobVacancy.getTechnical_skills());
				if (StringUtils.isBlank(jobVacancy.getSalary_ctc())) {
					jobVacancyResponse.setSalary_ctc("Not Disclosed");
				} else {
					jobVacancyResponse.setSalary_ctc(jobVacancy.getSalary_ctc());
				}
				jobVacancyResponse.setEducation(jobVacancy.getEducation());
				jobVacancyResponse.setJob_type(jobVacancy.getJob_type());
				jobVacancyResponse.setJob_location(jobVacancy.getJob_location());
				jobresponselist.add(jobVacancyResponse);
			}
		}
		return jobresponselist;

	}

	// Get My Candidate Referral list
	public List<EmployeeReferalResponseModel> convertTOGetEmployees(List<EmployeeReferal> employeeReferalList) {
		List<EmployeeReferalResponseModel> employeeReferalResponselist = new ArrayList<>();
		for (EmployeeReferal employeeReferal : employeeReferalList) {
			EmployeeReferalResponseModel employeeReferalResponseModel = new EmployeeReferalResponseModel();
			employeeReferalResponseModel.setReferal_id(employeeReferal.getReferal_id());
			employeeReferalResponseModel.setApplicant_name(employeeReferal.getApplicant_name());
			employeeReferalResponseModel.setExperience(employeeReferal.getExperience());
			employeeReferalResponseModel.setTechnical_skill(employeeReferal.getTechnical_skill());
			employeeReferalResponseModel.setResume(employeeReferal.getApplicant_name() + " Resume");
			employeeReferalResponseModel.setApplication_status(employeeReferal.getApplication_status());
			employeeReferalResponseModel.setJob_id(employeeReferal.getJob_id());
			employeeReferalResponseModel.setBonous_status(employeeReferal.getBonous_status());
			employeeReferalResponseModel.setDate(employeeReferal.getDate());
			employeeReferalResponselist.add(employeeReferalResponseModel);

		}
		return employeeReferalResponselist;

	}

	// Add Resume
	public EmployeeReferal addEmployeeResume(UploadResumeRequestModel uploadResumeRequestModel, MultipartFile file)
			throws IOException, CustomException {
		EmployeeReferal employeeReferal = new EmployeeReferal();
		employeeReferal.setApplicant_name(uploadResumeRequestModel.getApplicant_name());
		employeeReferal.setExperience(uploadResumeRequestModel.getExperience());
		employeeReferal.setTechnical_skill(uploadResumeRequestModel.getTechnical_skills());
		employeeReferal.setReferance_email(uploadResumeRequestModel.getEmail());
		Date date = new Date();
		employeeReferal.setDate(date);
		employeeReferal.setApplication_status("In Process");
		employeeReferal.setApplicant_email("default@agsft.com");

		employeeReferal.setFile_name(file.getOriginalFilename());
		employeeReferal.setFile_extension(FilenameUtils.getExtension(file.getOriginalFilename()));
		if (!file.isEmpty()) {
			if (!file.getOriginalFilename().isEmpty()) {
				employeeReferal.setResume(file.getBytes());
			} else {
				throw new CustomException(204, "File Name is empty");
			}
		} else {
			throw new CustomException(204, "Please Attatch File");
		}

		return employeeReferal;
	}

	// Get all employee referal list
	public List<EmployeeReferalResponseModel> getAllEmployeeReferal(List<EmployeeReferal> employeeReferalList) {
		List<EmployeeReferalResponseModel> employeeReferalResponselist = new ArrayList<>();
		for (EmployeeReferal employeeReferal : employeeReferalList) {
			EmployeeReferalResponseModel employeeReferalResponseModel = new EmployeeReferalResponseModel();
			employeeReferalResponseModel.setReferal_id(employeeReferal.getReferal_id());
			employeeReferalResponseModel.setApplicant_name(employeeReferal.getApplicant_name());
			employeeReferalResponseModel.setExperience(employeeReferal.getExperience());
			employeeReferalResponseModel.setTechnical_skill(employeeReferal.getTechnical_skill());
			employeeReferalResponseModel.setResume("Download ");
			employeeReferalResponseModel.setApplication_status(employeeReferal.getApplication_status());
			employeeReferalResponseModel.setJob_id(employeeReferal.getJob_id());
			employeeReferalResponseModel.setBonous_status(employeeReferal.getBonous_status());
			employeeReferalResponseModel.setDate(employeeReferal.getDate());
			employeeReferalResponseModel.setFileName(employeeReferal.getFile_name());
			employeeReferalResponseModel.setFileExtension(employeeReferal.getFile_extension());
			employeeReferalResponselist.add(employeeReferalResponseModel);
		}
		return employeeReferalResponselist;

	}

	// Calculate Bonus
	public EmployeeBonusStatusResponseModel calculateBonus(EmployeeReferal employeeReferal) {

		Date joinedDate = employeeReferal.getDate();
		int experience = (int) employeeReferal.getExperience();

		List<EmployeeReferal> employeeReferalBonus = (List<EmployeeReferal>) employeeReferalBonusrepo.findAll();
		Range<Integer> beginner = Range.between(0, 2);
		Range<Integer> senior = Range.between(3, 5);
		Range<Integer> superSenior = Range.between(6, 15);

		EmployeeBonusStatusResponseModel employeeBonusStatusResponse = new EmployeeBonusStatusResponseModel();
		for (EmployeeReferal employeeBonus : employeeReferalBonus) {
			if (beginner.contains(experience)) {
				employeeBonusStatusResponse.setReferal_id(employeeReferal.getReferal_id());
				employeeBonusStatusResponse.setBonus_status("Not applicable");
				break;
			} else if (senior.contains(experience)) {
				employeeBonusStatusResponse.setReferal_id(employeeReferal.getReferal_id());
				employeeBonusStatusResponse.setBonus_status("10000");
				break;
			} else if (superSenior.contains(experience)) {
				employeeBonusStatusResponse.setReferal_id(employeeReferal.getReferal_id());
				employeeBonusStatusResponse.setBonus_status("15000");
				break;
			}
		}
		return employeeBonusStatusResponse;
	}

	// get join candidate list

	public List<CandidateJoinResponseModel> getJoinCandidateList(List<EmployeeReferal> joinCandidateList) {

		List<CandidateJoinResponseModel> candidateJoinResponselist = new ArrayList<>();
		for (EmployeeReferal employeeReferal : joinCandidateList) {
			CandidateJoinResponseModel candidateJoinResponseModel = new CandidateJoinResponseModel();

			candidateJoinResponseModel.setApplicant_name(employeeReferal.getApplicant_name());
			candidateJoinResponseModel.setApplication_status(employeeReferal.getApplication_status());
			candidateJoinResponseModel.setBonous_status(employeeReferal.getBonous_status());
			candidateJoinResponseModel.setDate(employeeReferal.getDate());
			candidateJoinResponseModel.setExperience((int) employeeReferal.getExperience());
			candidateJoinResponseModel.setJob_id(employeeReferal.getJob_id());
			candidateJoinResponseModel.setReferal_id(employeeReferal.getReferal_id());
			candidateJoinResponseModel.setTechnical_skill(employeeReferal.getTechnical_skill());
			candidateJoinResponseModel.setApplicant_email(employeeReferal.getApplicant_email());
			candidateJoinResponselist.add(candidateJoinResponseModel);

		}

		return candidateJoinResponselist;

	}

	public String setBonusConversion(EmployeeReferal employeeReferal, SetBonusRequestModel setBonusRequestModel)
			throws CustomException {
		long difference = calculateDifferenceInDate.differenceCalculator(employeeReferal);
		Range<Integer> beginner = Range.between(0, 2);
		Range<Integer> senior = Range.between(3, 5);
		Range<Integer> superSenior = Range.between(6, 15);

		if (setBonusRequestModel.getBonus_status().compareTo("1stStage") == 0 && difference >= 45) {

			if (beginner.contains((int) employeeReferal.getExperience())) {
				return " No Bonus Applicant";
			}
			if (senior.contains((int) employeeReferal.getExperience())) {
				long basic_amount = 10000;
				employeeReferal.setBonus_amount(basic_amount / 2);
				return " 1st stage Bonus Updated Successfully";
			}
			if (superSenior.contains((int) employeeReferal.getExperience())) {
				long basic_amount = 15000;
				employeeReferal.setBonus_amount(basic_amount / 2);
				return " 1st stage Bonus Updated Successfully";
			}

		} else {
			throw new CustomException(202, " Employee has not yet completed 45 days");
		}

		if (setBonusRequestModel.getBonus_status().compareTo("2ndStage") == 0 && difference >= 100) {

			if (beginner.contains((int) employeeReferal.getExperience())) {
				return " No Bonus Applicant";
			}
			if (senior.contains((int) employeeReferal.getExperience())) {
				long basic_amount = 10000;
				employeeReferal.setBonus_amount(basic_amount);
				return " Total Bonus amount Updated";
			}
			if (superSenior.contains((int) employeeReferal.getExperience())) {
				long basic_amount = 15000;
				employeeReferal.setBonus_amount(basic_amount);
				return " Total Bonus amount Updated";
			}
		} else {
			throw new CustomException(202, " Employee has not yet completed 100 days");
		}
		return "No Bonus";
	}

	// Update JOB vacancy
	public JobVacancy updateJobVacancy(JobVacancy jobVacancy, JobVacancyRequestModel jobVacancyRequestModel) {
		jobVacancy.setEducation(jobVacancyRequestModel.getEducation());
		jobVacancy.setExp_from(jobVacancyRequestModel.getExp_from());
		jobVacancy.setExp_to(jobVacancyRequestModel.getExp_to());
		jobVacancy.setJob_description(jobVacancyRequestModel.getJob_description());
		jobVacancy.setJob_location(jobVacancyRequestModel.getJob_location());
		jobVacancy.setJob_title(jobVacancyRequestModel.getJob_title());
		jobVacancy.setJob_type(jobVacancyRequestModel.getJob_type());
		jobVacancy.setNumber_of_openings(jobVacancyRequestModel.getNumber_of_openings());
		jobVacancy.setTechnical_skills(jobVacancyRequestModel.getTechnical_skills());
		if (StringUtils.isBlank(jobVacancyRequestModel.getSalary_ctc())) {
			jobVacancy.setSalary_ctc("Not Disclosed");
		} else {
			jobVacancy.setSalary_ctc(jobVacancyRequestModel.getSalary_ctc());
		}
		return jobVacancy;
	}

	// Get row from job vacancy table for JOB Update
	public JobVacancyResponseModel getDetailJobVacancy(JobVacancy jobVacancy) {

		JobVacancyResponseModel jobVacancyResponse = new JobVacancyResponseModel();
		jobVacancyResponse.setJob_vacancy_id(jobVacancy.getJob_vacancy_id());
		jobVacancyResponse.setJob_title(jobVacancy.getJob_title());
		jobVacancyResponse.setNumber_of_openings(jobVacancy.getNumber_of_openings());
		// jobVacancyResponse.setExperience_required(jobVacancy.getExperience_required());
		jobVacancyResponse.setExp_to(jobVacancy.getExp_to());
		jobVacancyResponse.setExp_from(jobVacancy.getExp_from());
		jobVacancyResponse.setJob_description(jobVacancy.getJob_description());
		jobVacancyResponse.setTechnical_skills(jobVacancy.getTechnical_skills());
		jobVacancyResponse.setSalary_ctc(jobVacancy.getSalary_ctc());
		if (StringUtils.isBlank(jobVacancy.getSalary_ctc())) {
			jobVacancyResponse.setSalary_ctc("Not Disclosed");
		} else {
			jobVacancyResponse.setSalary_ctc(jobVacancy.getSalary_ctc());
		}
		jobVacancyResponse.setEducation(jobVacancy.getEducation());
		jobVacancyResponse.setJob_type(jobVacancy.getJob_type());
		jobVacancyResponse.setJob_location(jobVacancy.getJob_location());

		return jobVacancyResponse;
	}
}
