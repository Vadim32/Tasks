package app1.web;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import app1.domain.App1User;

public class UserFormValidator implements Validator {

	public boolean supports(Class<?> obj) {
		 return App1User.class.isAssignableFrom(obj);
	}

	public void validate(Object command, Errors errors) {
		  ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "user.firstName");
          ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "user.lastName");
          ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "user.username");
          ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "user.password");
          ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userPosition", "user.userPosition");
	}

}
