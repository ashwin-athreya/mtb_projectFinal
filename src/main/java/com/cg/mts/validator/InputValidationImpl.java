package com.cg.mts.validator;

import org.springframework.stereotype.Component;

@Component
public class InputValidationImpl implements InputValidator {

	@Override
	public boolean nameValidator(String name) {
		return name.length() >= 5;
	}

	public boolean usernameValidator(String username) {
		return username.matches("[A-Za-z]{5,20}$");
	}

	@Override
	public boolean passwordValidator(String password) {
		return password.length() >= 6;
	}

	@Override
	public boolean contactValidator(String contact) {
		return contact.matches("[0-9]{10}");
	}

	@Override
	public boolean emailValidator(String email) {
		return email.matches("^(.+)@(.+)$");
	}

	@Override
	public boolean roleValidator(String role) {
		if(role.equalsIgnoreCase("ADMIN") || role.equalsIgnoreCase("CUSTOMER")) {
			return true;
		}
		else 
		{
		return false;
		}
	}
}
