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

@Table(name = "employee_bonus")
@Entity
@Data
@FieldDefaults(level=AccessLevel.PRIVATE)
public class EmployeeBonus {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "range_id")
	int range_id;

	@Column(name = "from")
	int from;
	
	@Column(name = "to")
	int to;
	
	@Column(name = "bonus_amount")
	double bonus_amount;
}
