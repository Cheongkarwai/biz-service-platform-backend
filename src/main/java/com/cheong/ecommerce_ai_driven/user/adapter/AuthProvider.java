package com.cheong.ecommerce_ai_driven.user.adapter;

import com.cheong.ecommerce_ai_driven.user.dto.*;
import reactor.core.publisher.Mono;

public interface AuthProvider{

    Mono<UserDTO> signUp(UserInput userInput);

    Mono<Void> signOut(String token);

    Mono<Void> signUpOtp(SignUpOtpRequest signUpOtpRequest);

    Mono<Void> recoverPassword(RecoverPasswordRequest recoverPasswordRequest);

    Mono<AuthResponse> login(LoginRequest loginRequest);

    Mono<UserDTO> findByUserId(String userId);

}
