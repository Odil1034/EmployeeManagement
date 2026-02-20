package com.example.EmployeeManagement.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AgeValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAge {

    String message() default "Age must be between {min} and {max} years";

    int min() default 16;

    int max() default 110;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
