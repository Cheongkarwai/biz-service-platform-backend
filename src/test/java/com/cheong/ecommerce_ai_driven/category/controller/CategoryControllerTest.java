package com.cheong.ecommerce_ai_driven.category.controller;

import com.cheong.ecommerce_ai_driven.common.converter.JsonPatchHttpMessageConverter;
import com.cheong.ecommerce_ai_driven.speciality.controller.CategoryController;
import com.cheong.ecommerce_ai_driven.speciality.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@ExtendWith(SpringExtension.class)
@SpringBootTest
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
    void givenRequiredRequestParameterLimit_whenFindAllCategories_shouldReturnOk() {
        webTestClient.get()
                .uri("/api/v1/categories?limit={limit}",
                        "10")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void givenNoRequestParameter_whenFindAllCategories_shouldReturnBadRequest(){
        webTestClient.get()
                .uri("/api/v1/categories")
                .exchange()
                .expectStatus().isBadRequest();
    }

}
