package com.rmportal.controller;

import java.util.Objects;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rmportal.requestModel.PublishJobRequestModel;
import com.rmportal.service.VelocityTemplateService;
import com.rmportal.utility.ApplicationUtils;
import com.rmportal.utility.CustomException;

import io.swagger.annotations.Api;

@RestController
@Api(value = "Velocity Template", description = "Velocity Template for publish email")
@CrossOrigin("*")
public class VelocityTemplate {

	@Autowired
	private VelocityTemplateService velocityTemplateService;
	
	@Autowired
	private ApplicationUtils applicationUtils;

	@RequestMapping(value = "/broadcastJob", method = RequestMethod.POST)
	public String broadcastJob(@Valid @RequestBody PublishJobRequestModel publishJobRequest,
			BindingResult bindingResult) throws MessagingException, CustomException {
		if (Objects.isNull(publishJobRequest)) {
			throw new CustomException(204, "Job Posting filed are empty.");
		}
		try {
			if (bindingResult.hasErrors()) {
				throw new CustomException(204, bindingResult.getAllErrors().get(0).getDefaultMessage());
			}
			applicationUtils.validateEntity(publishJobRequest, bindingResult);
		} catch (Exception e) {
			throw new CustomException(201, e.getMessage());
		}
		
		return velocityTemplateService.sendMail(publishJobRequest);
	}

}
