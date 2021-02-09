package com.rmportal.utility;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.rmportal.model.EmployeeReferal;

@Component
public class CalculateDifferenceInDate {

	public long differenceCalculator(EmployeeReferal employeeReferal){
		LocalDate now = LocalDate.now();
		Date input = employeeReferal.getDate();
		Instant instant = Instant.ofEpochMilli(input.getTime());
		LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
		LocalDate joiningDate = localDateTime.toLocalDate();
		Period p = Period.between(now, joiningDate);
		long diff = Math.abs(p.getDays());
		return diff;
	}
	
}
