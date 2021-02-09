package com.rmportal.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rmportal.model.ReferralStatus;

@Repository
public interface ReferralStatusRepository extends CrudRepository<ReferralStatus, Integer> {
}
