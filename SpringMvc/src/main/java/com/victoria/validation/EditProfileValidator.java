package com.victoria.validation;

import com.victoria.model.User;
import com.victoria.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class EditProfileValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.isAssignableFrom(aClass);
    }

    @Autowired
    private UserService userService;

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User)o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fio", "fio.empty", "Full Name must not be empty.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "login", "login.empty", "Login must not be empty.");

        if(!userService.isYourLoginForUpdateUser(user.getLogin(),user.getPasswordHash())){
            errors.rejectValue("login", "login.isExist", "This login is already in use.");
        }

        if(!EmailValidator.getInstance().isValid( user.getEmail() ) ){
            errors.rejectValue("email", "email.notValid", "E-mail address is not valid.");
        }

        if(!userService.isYourEmailForUpdateUser(user.getEmail(),user.getPasswordHash())){
            errors.rejectValue("email", "email.isExist", "This E-mail is already in use.");
        }

    }
}
