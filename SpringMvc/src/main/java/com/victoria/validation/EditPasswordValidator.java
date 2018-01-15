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
public class EditPasswordValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.isAssignableFrom(aClass);
    }

    @Autowired
    private UserService userService;

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User)o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordHash", "passwordHash.empty", "Password must not be empty.");
        if (!(user.getPasswordHash()).equals(user.getConfirmPassword())) {
            errors.rejectValue("confirmPassword", "confirmPassword.passwordDontMatch", "Passwords don't match.");
        }
        String password = user.getPasswordHash();
        if (password.length() < 6){
            errors.rejectValue("passwordHash", "passwordHash.tooShort", "Password must more than 5 characters.");
        }
    }
}
