package com.buildtwicebulldozeonce.universiteanonyme.Validators;

import com.buildtwicebulldozeonce.universiteanonyme.Models.User;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "name", "name.empty");

        User user = (User) o;

        errors.reject("szar");
    }
}
