package com.rmportal.responseModel;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserPremissionModel {

	boolean AddOpenPosition;

	boolean UpdateOpenPosition;

	boolean ViewOpenPosition;

	boolean ChangeApplicationStatus;

	boolean DeactivateUser;

	boolean AssignRole;

	boolean ViewResumeStatus;

	boolean ViewBonus;

	boolean AddReferral;

	boolean AddBonusDetails;

	boolean UpdateBonusDetails;

}
