package com.cheong.ecommerce_ai_driven.category.controller;

import com.cheong.ecommerce_ai_driven.common.converter.JsonPatchHttpMessageConverter;
import com.cheong.ecommerce_ai_driven.speciality.controller.CategoryController;
import com.cheong.ecommerce_ai_driven.speciality.controller.SpecialityController;
import com.cheong.ecommerce_ai_driven.speciality.dto.CategoryInput;
import com.cheong.ecommerce_ai_driven.speciality.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@ExtendWith(SpringExtension.class)
@WebFluxTest(CategoryController.class)
@ContextConfiguration(classes = {JsonPatchHttpMessageConverter.class})
public class CategoryControllerTest {

    private WebTestClient webTestClient;

    @Mock
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        this.webTestClient =
                WebTestClient.bindToController(new CategoryController(categoryService)).build();
    }

    @Test
    @DisplayName("When id given, when call find category by id API, it should return ok")
    void givenId_whenFindCategoryById_shouldReturnOk() {
        webTestClient.get()
                .uri("/api/v1/categories/1")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("When request parameter limit is being supplied, when call find all categories API, it should return ok")
    void givenRequiredRequestParameterLimit_whenFindAllCategories_shouldReturnOk() {
        webTestClient.get()
                .uri("/api/v1/categories?limit={limit}",
                        "10")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("When no request parameter limit is being supplied, when call find all categories API, it should return bad request")
    void givenNoRequestParameter_whenFindAllCategories_shouldReturnBadRequest() {
        webTestClient.get()
                .uri("/api/v1/categories")
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @DisplayName("When category name is not being supplied, when call create categories API, it should return bad request")
    void givenCategoryInputWithNoName_whenCreateCategory_shouldReturnBadRequest() {
        webTestClient.post()
                .uri("/api/v1/categories")
                .bodyValue(new CategoryInput())
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @DisplayName("When category name being supplied, when call create categories API, it should return ok")
    void givenCategoryInputWithName_whenCreateCategory_shouldReturnOk() {
        webTestClient.post()
                .uri("/api/v1/categories")
                .bodyValue(new CategoryInput("Test"))
                .exchange()
                .expectStatus().isOk();
    }

}
