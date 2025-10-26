package com.cheong.ecommerce_ai_driven.user.adapter;

import com.cheong.ecommerce_ai_driven.account.exception.AuthAccountNotFoundException;
import com.cheong.ecommerce_ai_driven.user.dto.*;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class SupabaseUserAdapter implements AuthProvider {

    private final WebClient webClient;

    public SupabaseUserAdapter(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Mono<UserDTO> signUp(UserInput userInput) {
        return webClient.post().uri("/auth/v1/signup")
                .bodyValue(userInput)
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        clientResponse -> clientResponse.bodyToMono(SupabaseError.class)
                                .flatMap(Mono::error)
                )
                .onStatus(
                        HttpStatusCode::is5xxServerError,
                        clientResponse -> Mono.error(new RuntimeException("Error occurred while signing up"))
                )
                .bodyToMono(UserDTO.class);
    }

    @Override
    public Mono<Void> signOut(String token) {
        return webClient.post().uri("/auth/v1/logout")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        clientResponse -> clientResponse.bodyToMono(SupabaseError.class)
                                .map(supabaseError -> new BadCredentialsException(supabaseError.getMsg())))
                .onStatus(
                        HttpStatusCode::is5xxServerError,
                        clientResponse -> Mono.error(new RuntimeException("Error occurred while signing up"))
                )
                .bodyToMono(Void.class);
    }

    @Override
    public Mono<Void> signUpOtp(SignUpOtpRequest signUpOtpRequest) {
        return null;
    }

    @Override
    public Mono<Void> recoverPassword(RecoverPasswordRequest recoverPasswordRequest) {
        return webClient.post().uri("/auth/v1/recover")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        clientResponse -> clientResponse.bodyToMono(SupabaseError.class)
                                .flatMap(Mono::error)
                )
                .onStatus(
                        HttpStatusCode::is5xxServerError,
                        clientResponse -> Mono.error(new RuntimeException("Error occurred while signing up"))
                )
                .bodyToMono(Void.class);
    }

    @Override
    public Mono<AuthResponse> login(LoginRequest loginRequest) {
        return webClient.post().uri("/auth/v1/recover")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        clientResponse -> clientResponse.bodyToMono(SupabaseError.class)
                                .flatMap(Mono::error)
                )
                .onStatus(
                        HttpStatusCode::is5xxServerError,
                        clientResponse -> Mono.error(new RuntimeException("Error occurred while signing up"))
                )
                .bodyToMono(AuthResponse.class);
    }

    @Override
    public Mono<UserDTO> findByUserId(String userId) {
        return webClient.get().uri("/auth/v1/admin/users/{userId}", userId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        clientResponse -> clientResponse.bodyToMono(SupabaseError.class)
                                .flatMap(Mono::error)
                )
                .bodyToMono(UserDTO.class)
                .switchIfEmpty(Mono.error(new AuthAccountNotFoundException("Account not found")));

    }
}
