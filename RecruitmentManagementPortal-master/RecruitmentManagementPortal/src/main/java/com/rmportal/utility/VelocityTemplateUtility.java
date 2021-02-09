package com.rmportal.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.rmportal.requestModel.PublishJobRequestModel;

@Component
// @PropertySource("classpath:mail.properties")
public class VelocityTemplateUtility {

	@Autowired
	private JavaMailSender emailSender;

	public String sendMailUtil(PublishJobRequestModel publishJobRequest, VelocityEngine velocityEngine, String location)
			throws MessagingException {

		String subject = "Opening Positions.";

		List<String> descriptionList = new ArrayList<>();
		String[] descriptionArray = publishJobRequest.getJobDiscription().split(",");
		for(String description : descriptionArray){
			descriptionList.add(description);
		}
		
		List<String> knowledgeOfList = new ArrayList<>();
		String[] knowledgeOfArray = publishJobRequest.getKnowledgeOf().split(",");
		for(String knowledgeOf : knowledgeOfArray){
			knowledgeOfList.add(knowledgeOf);
		}
		
		Map<String, Object> model = new HashMap<>();
		model.put("designation", publishJobRequest.getDesignation());
		model.put("experience", publishJobRequest.getExperience());
		model.put("positions", publishJobRequest.getPositions());
		model.put("jobDescription", descriptionList);
		model.put("knowledgeOf", knowledgeOfList);
		String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, location, "UTF-8", model);

		MimeMessage mimeMessage = emailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
		mimeMessageHelper.setTo("jsourabh@agsft.com");
		mimeMessageHelper.setSubject(subject);
		mimeMessageHelper.setFrom("no-reply-rpPortal@agsft.com");
		mimeMessageHelper.setText(text, true);
		emailSender.send(mimeMessage);
		return "Email Sent Successfully...";
	}
}
