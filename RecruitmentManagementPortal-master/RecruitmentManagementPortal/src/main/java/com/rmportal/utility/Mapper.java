package com.rmportal.utility;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.rmportal.requestModel.LoginRequestModel;


/**
 * @author saurabh
 *
 */
public class Mapper implements RowMapper<LoginRequestModel>{
	@Override
	public LoginRequestModel mapRow(ResultSet rs, int rowNum) throws SQLException {

		LoginRequestModel login = new LoginRequestModel();
		login.setEmail(rs.getString("email"));
		login.setPassword(rs.getString("password"));
		return login;
	}
}
