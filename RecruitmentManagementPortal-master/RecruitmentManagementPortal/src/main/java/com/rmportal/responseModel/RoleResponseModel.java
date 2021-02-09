package com.rmportal.responseModel;

import java.util.List;

import com.rmportal.model.Permission;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * @author saurabh
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleResponseModel {

	int id;

	String role;

	List<Permission> permissions;

}
