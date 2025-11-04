package com.cheong.ecommerce_ai_driven.company.controller;

import com.cheong.ecommerce_ai_driven.common.converter.JsonPatchHttpMessageConverter;
import com.cheong.ecommerce_ai_driven.company.api.CompanyController;
import com.cheong.ecommerce_ai_driven.company.dto.AddressDTO;
import com.cheong.ecommerce_ai_driven.company.dto.AddressType;
import com.cheong.ecommerce_ai_driven.company.dto.BusinessInput;
import com.cheong.ecommerce_ai_driven.company.service.CompanyService;
import com.cheong.ecommerce_ai_driven.speciality.controller.SpecialityController;
import com.cheong.ecommerce_ai_driven.speciality.service.SpecialityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.Context;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.ArrayList;
import java.util.List;

@WebFluxTest(CompanyController.class)
@ContextConfiguration(classes = {JsonPatchHttpMessageConverter.class})
public class CompanyControllerTest {

    private WebTestClient webTestClient;

    @Mock
    private CompanyService companyService;


    private String email = "Biz@gmail.com";
    private String mobileNumber = "601128188291";
    private String overview = "This is a test business";
    private String name = "Test Business";
    private List<AddressDTO> addresses = new ArrayList<>();

    @BeforeEach
    void setUp() {
        this.webTestClient =
                WebTestClient.bindToController(new CompanyController(companyService))
                        .build();
    }

    @Test
    @DisplayName("Given business id, when call find company by id API, should return ok")
    void givenId_whenFindCompanyById_shouldReturnOk() {
        webTestClient.get()
                .uri("/api/v1/companies/1")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("Given business id, when call find company addresses by id API, should return ok")
    void givenId_whenFindCompanyAddressesById_shouldReturnOk() {
        webTestClient.get()
                .uri("/api/v1/companies/1/addresses")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("Given business id, when call find company service by id API, should return ok")
    void givenId_whenFindCompanyServiceById_shouldReturnOk() {
        webTestClient.get()
                .uri("/api/v1/companies/1/services")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("Given business name, when call save company API, should return bad request")
    void givenOnlyBusinessName_whenSaveCompany_shouldReturnBadRequest() {

        webTestClient.post()
                .uri("/api/v1/companies")
                .bodyValue(new BusinessInput(name, null, null, null, null, null))
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @DisplayName("Given business mobile number, when call save company API, should return bad request")
    void givenOnlyBusinessMobileNumber_whenSaveCompany_shouldReturnBadRequest() {

        webTestClient.post()
                .uri("/api/v1/companies")
                .bodyValue(new BusinessInput(null, mobileNumber, null, null, null, null))
                .exchange()
                .expectStatus().isBadRequest();
    }


    @Test
    @DisplayName("Given business overview, when call save company API, should return bad request")
    void givenOnlyBusinessOverview_whenSaveCompany_shouldReturnBadRequest() {

        webTestClient.post()
                .uri("/api/v1/companies")
                .bodyValue(new BusinessInput(null, null, null, overview, null, null))
                .exchange()
                .expectStatus().isBadRequest();
    }


    @Test
    @DisplayName("Given business email, when call save company API, should return bad request")
    void givenOnlyBusinessEmail_whenSaveCompany_shouldReturnBadRequest() {

        webTestClient.post()
                .uri("/api/v1/companies")
                .bodyValue(new BusinessInput(null, null, email, null, null, null))
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @DisplayName("Given business empty addresses, when call save company API, should return bad request")
    void givenEmptyBusinessAddresses_whenSaveCompany_shouldReturnBadRequest() {

        webTestClient.post()
                .uri("/api/v1/companies")
                .bodyValue(new BusinessInput(name, mobileNumber, email, overview, addresses, null))
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @DisplayName("Given business non-empty addresses but invalid address, when call save company API, should return bad request")
    void givenBusinessAddressesAndNonValidAddress_whenSaveCompany_shouldReturnBadRequest() {
        AddressDTO address = new AddressDTO();
        address.setCity("Bangkok");
        address.setZip("43200");
        address.setStreet("Changi Blvd");
        address.setType(AddressType.BUSINESS);
        addresses.add(address);

        webTestClient.post()
                .uri("/api/v1/companies")
                .bodyValue(new BusinessInput(name, mobileNumber, email, overview, addresses, null))
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @DisplayName("Given business non-empty addresses, when call save company API, should return ok")
    void givenBusinessNonEmptyAddressesAndValidAddress_whenSaveCompany_shouldReturnOk() {
        AddressDTO address = new AddressDTO();
        address.setCity("Bangkok");
        address.setZip("43200");
        address.setStreet("Changi Blvd");
        address.setState("Cheras");
        address.setType(AddressType.BUSINESS);
        addresses.add(address);

        webTestClient.post()
                .uri("/api/v1/companies")
                .bodyValue(new BusinessInput(name, mobileNumber, email, overview, addresses, null))
                .exchange()
                .expectStatus().isOk();
    }
}
