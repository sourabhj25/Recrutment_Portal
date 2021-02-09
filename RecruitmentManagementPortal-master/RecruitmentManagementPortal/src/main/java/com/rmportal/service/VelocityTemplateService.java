package com.rmportal.service;

import javax.mail.MessagingException;

import com.rmportal.requestModel.PublishJobRequestModel;

public interface VelocityTemplateService {

	public String sendMail(PublishJobRequestModel publishJobRequest) throws MessagingException;
	
}
