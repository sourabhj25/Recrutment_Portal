package com.rmportal.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rmportal.model.JobVacancy;


@Repository
public interface JobVacancyRepository extends CrudRepository<JobVacancy, Integer>{

	
	@Query(value = "SELECT * FROM job_vacancy WHERE job_vacancy_id=:job_vacancy_id", nativeQuery = true)
	public JobVacancy findByJobVacancyId(@Param("job_vacancy_id") int job_vacancy_id);

}
