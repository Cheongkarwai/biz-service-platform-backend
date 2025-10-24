package com.cheong.ecommerce_ai_driven.account.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountStateInvalidException extends RuntimeException{

    public AccountStateInvalidException(String message) {
        super(message);
    }
}
