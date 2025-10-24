package com.cheong.ecommerce_ai_driven.customer.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MinAgeValidator.class)
@Documented
public @interface MinAge {
    int minAge() default 18;
    String message() default "{constraints.minAge.error}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
