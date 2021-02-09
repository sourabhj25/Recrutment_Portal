package com.rmportal.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rmportal.model.EmployeeBonus;

@Repository
public interface EmployeeBonusRepository extends CrudRepository<EmployeeBonus, Integer>{

}
