package com.rmportal;

import java.io.IOException;
import java.util.Properties;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.velocity.VelocityEngineFactory;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class RecruitmentManagementPortalApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecruitmentManagementPortalApplication.class, args);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public Docket swaggerIntegration() {

		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.rmportal.controller")).build().apiInfo(information());

	}

	private ApiInfo information() {

		ApiInfo apiInfo = new ApiInfo("AGSFT RECRUITMENT PORTAL", "Online Portal for Recruitment Management", " ",
				"TOS", "agsft.com", "AGSFT License", "www.agsft.com");
		return apiInfo;
	}

	@Bean
	public Validator jsr303Validator() {
		return new LocalValidatorFactoryBean();
	}
	
	@Bean
    public VelocityEngine getVelocityEngine() throws VelocityException, IOException{
        VelocityEngineFactory factory = new VelocityEngineFactory();
        Properties props = new Properties();
        props.put("resource.loader", "classpath");
        props.put("classpath.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        factory.setVelocityProperties(props);
        
        VelocityEngine ve = factory.createVelocityEngine();
        ve.init();
        return ve;      
    }
}
