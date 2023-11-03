package com.example.learning_spring.constraints;

import com.example.learning_spring.validator.AgeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = AgeValidator.class)
@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Documented
public @interface Age {
    String message() default "Age must be greater than 18";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
