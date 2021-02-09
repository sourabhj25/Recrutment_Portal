package com.rmportal.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.rmportal.model.EmployeeReferal;

@Component
public class EmailReminder {

	@Autowired
	private JavaMailSender emailSender;

	public void sendMail(EmployeeReferal referralList, long diff) {

		if (diff != 0) {
			String subject = "Reminder to Transfer Bonus Amount";
			String thanksText = "\n \n Thanks, \n AGSFT Support Team";
			/*
			 * String message = "Hi " + ", \n" +
			 * "\n This is the mail from AGSFT Recruitment Management Portal to remind you that "
			 * + referralList.getApplicant_name() + "(" +
			 * referralList.getApplicant_email() + ")" +
			 * " \n have completed his " + diff +
			 * " of duration. \n Transfer the bonus to referee " +
			 * referralList.getReferance_email();
			 */
			String message = "Hello Sir/Madam " + ", \n" + "\n This is to remind you that "
					+ referralList.getApplicant_name() + "(" + referralList.getApplicant_email() + ")"
					+ " has completed his/her " + diff
					+ " days of duration. \n \n Please transfer the bonus amount to the referee("
					+ referralList.getReferance_email() + ") of "+referralList.getApplicant_name()+"." 
					+ thanksText;

			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setTo("jsourabh@agsft.com");
			mailMessage.setSubject(subject);
			mailMessage.setText(message);
			mailMessage.setFrom("no-reply-rpPortal@agsft.com");
			emailSender.send(mailMessage);
		}

	}

}
