package com.rmportal.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rmportal.model.Role;

/**
 * @author tejas
 *
 */
@Repository("roleRepository")
public interface RoleRepository extends CrudRepository<Role, Integer>{
	
	public Role findByRole(String role);
	

}
