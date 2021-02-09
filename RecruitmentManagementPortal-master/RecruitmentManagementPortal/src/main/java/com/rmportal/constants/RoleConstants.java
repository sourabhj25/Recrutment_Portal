package com.rmportal.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleConstants {

	END_USER(1, "END_USER"),
	ADMIN(2, "ADMIN"),
	HR(3, "HR");
	
	int role_id;
	
	String role_name;
	
}
