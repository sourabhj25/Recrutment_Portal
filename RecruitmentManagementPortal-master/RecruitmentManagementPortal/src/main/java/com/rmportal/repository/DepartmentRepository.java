package com.rmportal.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rmportal.model.Department;

@Repository
public interface DepartmentRepository extends CrudRepository<Department, Integer>{ 

	@Query(value = "SELECT * FROM department WHERE dept_name=:dept_name", nativeQuery = true)
	public Department findByName(@Param("dept_name") String dept_name);
}
