package com.rmportal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * @author saurabh
 *
 */
@Table(name = "user_token")
@Entity
@Data
@FieldDefaults(level=AccessLevel.PRIVATE)
public class UserToken {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_token_id")
	int tokenid;

	@Column(name = "user_token")
	String token;

	@Column(name = "user_id")
	int user_id;
	
	@Column(name = "token_type")
	String tokenType;
	
	

}
