package com.rmportal.service.impl;

import javax.mail.MessagingException;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmportal.requestModel.PublishJobRequestModel;
import com.rmportal.service.VelocityTemplateService;
import com.rmportal.utility.VelocityTemplateUtility;

@Service
public class VelocityTemplateServiceImpl implements VelocityTemplateService{

	@Autowired
	private VelocityTemplateUtility velocityTemplateUtility;
	
	@Autowired
	private VelocityEngine velocityEngine;
	
	public static final String location = "templates/velocity1.vm";
	
	@Override
	public String sendMail(PublishJobRequestModel publishJobRequest) throws MessagingException {
		return velocityTemplateUtility.sendMailUtil(publishJobRequest,velocityEngine,location);
	}

}
