package com.example.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ContactNumberValidator implements
        ConstraintValidator<ContactNumberConstraint, String> {

    @Override
    public void initialize(ContactNumberConstraint contactNumber) {
    }

    @Override
    public boolean isValid(String contactField,
                           ConstraintValidatorContext cxt) {
        if (contactField == null) {
            cxt.disableDefaultConstraintViolation();
            cxt.buildConstraintViolationWithTemplate("Contact number must not be null")
                    .addConstraintViolation();
            return false;
        } else if (!contactField.matches("[0-9]+")) {
            cxt.disableDefaultConstraintViolation();
            cxt.buildConstraintViolationWithTemplate("Contact number must be a number")
                    .addConstraintViolation();
            return false;
        } else if (contactField.length() <= 8 || contactField.length() >= 14) {
            cxt.disableDefaultConstraintViolation();
            String template;
            if (contactField.length() <= 8) {
                template = "Contact number must be at least 9 digits";
            } else {
                template = "Contact number must be less than 14 digits";
            }
            cxt.buildConstraintViolationWithTemplate(template)
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

}
