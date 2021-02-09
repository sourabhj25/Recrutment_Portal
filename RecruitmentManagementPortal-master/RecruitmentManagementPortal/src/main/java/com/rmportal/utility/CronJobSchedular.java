package com.rmportal.utility;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.rmportal.model.EmployeeReferal;
import com.rmportal.repository.EmployeeReferalRepository;

@Component
@EnableScheduling
public class CronJobSchedular {

	@Autowired
	private EmployeeReferalRepository employeeReferralRepo;

	@Autowired
	private DateCalculator dateCalculator;

//	private static final Logger log = LoggerFactory.getLogger(CronJobSchedular.class);

	@Scheduled(cron = "${cronjob.daily.time}")
	public void sendReminderMail() throws CustomException {

		List<EmployeeReferal> employeeReferralList = (List<EmployeeReferal>) employeeReferralRepo.findAll();
		if(Objects.isNull(employeeReferralList)){
			throw new CustomException(401, " No single record Found! Empty");
		}
		for (EmployeeReferal referralList : employeeReferralList) {
			if (referralList.getApplication_status().compareTo("Joined")==0) {
				dateCalculator.calculateDifferenceBetweenDates(referralList);
			}
		}
	}

}
