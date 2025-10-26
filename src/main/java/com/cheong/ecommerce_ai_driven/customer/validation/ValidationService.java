package com.cheong.ecommerce_ai_driven.customer.validation;

import com.cheong.ecommerce_ai_driven.common.dto.ValidationError;
import com.cheong.ecommerce_ai_driven.common.dto.ValidationException;
import com.cheong.ecommerce_ai_driven.customer.repository.CustomerRepository;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.function.BiFunction;

@Service
public class ValidationService {

    private final CustomerRepository customerRepository;

    public ValidationService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Mono<Boolean> validateEmail(String email){
        return customerRepository.existsByEmailAddress(email)
                .filter(exists -> !exists)
                .switchIfEmpty(Mono.error(ValidationError.EMAIL_ADDRESS_EXISTS.asException()));
    }

    public Mono<Boolean> validateMobileNumber(String mobileNumber){
        return customerRepository.existsByMobileNumber(mobileNumber)
                .filter(exists -> !exists)
                .switchIfEmpty(Mono.error(ValidationError.MOBILE_NUMBER_EXISTS.asException()));
    }

}
