package com.rmportal.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

@Component
public class ApplicationUtils {

	@Autowired
	@Qualifier("jsr303Validator")
	Validator validator;

	public void validateEntity(final Object object, BindingResult bindingResult) {
		validator.validate(object, bindingResult);
	}
}
