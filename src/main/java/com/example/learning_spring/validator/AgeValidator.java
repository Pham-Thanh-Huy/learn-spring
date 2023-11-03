package com.example.learning_spring.validator;

import com.example.learning_spring.constraints.Age;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class AgeValidator implements ConstraintValidator<Age, Integer> {
    @Override
    public boolean isValid(Integer age, ConstraintValidatorContext context) {
        return age > 18;
    }
}
