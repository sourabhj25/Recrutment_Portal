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

@Table(name = "referral_status")
@Entity
@Data
@FieldDefaults(level=AccessLevel.PRIVATE)
public class ReferralStatus {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "referral_id")
	int referral_id;

	@Column(name = "referral_status")
	String referral_status;

}
