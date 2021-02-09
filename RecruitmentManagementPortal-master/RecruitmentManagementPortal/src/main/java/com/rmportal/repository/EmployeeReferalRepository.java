package com.rmportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rmportal.model.EmployeeReferal;

@Repository
public interface EmployeeReferalRepository extends CrudRepository<EmployeeReferal, Integer> {

	@Query(value = "SELECT * FROM employee_referal WHERE referance_email=:referance_email", nativeQuery = true)
	public List<EmployeeReferal> findByEmployeeEmail(@Param("referance_email") String referance_email);
	
	@Query(value = "SELECT * FROM employee_referal WHERE application_status='Joined'", nativeQuery = true)
	public List<EmployeeReferal> findByApplicationStatus();
}
