package com.cheong.ecommerce_ai_driven.category.controller;

import com.cheong.ecommerce_ai_driven.speciality.controller.SpecialityController;
import com.cheong.ecommerce_ai_driven.speciality.dto.SpecialityInput;
import com.cheong.ecommerce_ai_driven.speciality.service.SpecialityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class SpecialityControllerTest {

    private WebTestClient webTestClient;

    @Mock
    private SpecialityService specialityService;

    @BeforeEach
    void setUp() {
        this.webTestClient =
                WebTestClient.bindToController(new SpecialityController(specialityService)).build();
    }

    @Test
    @DisplayName("When request parameter limit is being supplied, when call find all specialities API, it should return ok")
    void givenRequiredRequestParameterLimit_whenFindAllSpecialities_shouldReturnOk() {
        webTestClient.get()
                .uri("/api/v1/specialities?limit={limit}",
                        "10")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("When no request parameter limit is being supplied, when call find all specialities API, it should return bad request")
    void givenNoRequestParameterLimit_whenFindAllSpecialities_shouldReturnBadRequest() {
        webTestClient.get()
                .uri("/api/v1/specialities")
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @DisplayName("When id is being supplied, when call find speciality by id API, it should return ok")
    void givenId_whenFindSpecialityById_shouldReturnOk() {
        webTestClient.get()
                .uri("/api/v1/specialities/1")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("When speciality name is not being supplied, when call create speciality API, it should return bad request")
    void givenSpecialityInputWithNoName_whenCreateSpeciality_shouldReturnBadRequest() {
        webTestClient.post()
                .uri("/api/v1/specialities")
                .bodyValue(new SpecialityInput())
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @DisplayName("When speciality name is being supplied, when call create speciality API, it should return ok")
    void givenSpecialityInputWithNoName_whenCreateSpeciality_shouldReturnOk() {
        webTestClient.post()
                .uri("/api/v1/specialities")
                .bodyValue(new SpecialityInput("Hi"))
                .exchange()
                .expectStatus().isOk();
    }
}