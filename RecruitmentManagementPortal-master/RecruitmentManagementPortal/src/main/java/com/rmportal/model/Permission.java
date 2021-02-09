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

@Entity
@Table(name = "permission")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Permission {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "permission_id")
	int permission_id;

	@Column(name = "permission_name")
	String premissionName;

}
