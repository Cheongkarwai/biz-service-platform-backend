package com.cheong.ecommerce_ai_driven.customer.service;

import com.cheong.ecommerce_ai_driven.account.dto.AccountStatus;
import com.cheong.ecommerce_ai_driven.account.dto.AccountType;
import com.cheong.ecommerce_ai_driven.account.input.AccountInput;
import com.cheong.ecommerce_ai_driven.account.service.AccountService;
import com.cheong.ecommerce_ai_driven.common.paging.dto.Connection;
import com.cheong.ecommerce_ai_driven.common.util.RetryUtil;
import com.cheong.ecommerce_ai_driven.customer.dto.CustomerDTO;
import com.cheong.ecommerce_ai_driven.customer.entity.Customer;
import com.cheong.ecommerce_ai_driven.customer.input.CustomerInput;
import com.cheong.ecommerce_ai_driven.customer.mapper.CustomerMapper;
import com.cheong.ecommerce_ai_driven.customer.repository.CustomerRepository;
import com.cheong.ecommerce_ai_driven.customer.validation.ValidationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@Service
@Slf4j
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;

    private final AccountService accountService;

    private final ValidationService validationService;

    private final RetryUtil retryUtil;

    private final ObjectMapper objectMapper;

    public CustomerService(CustomerRepository customerRepository,
                           CustomerMapper customerMapper,
                           AccountService accountService,
                           ValidationService validationService,
                           RetryUtil retryUtil,
                           ObjectMapper objectMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
        this.accountService = accountService;
        this.validationService = validationService;
        this.retryUtil = retryUtil;
        this.objectMapper = objectMapper;
    }

    public Mono<CustomerDTO> findById(String id) {
        return customerRepository.findById(id)
                .map(customerMapper::mapToCustomerDTO);
    }

    public Mono<Connection<CustomerDTO>> findAll(String after, String before, int limit) {
        return customerRepository.findAll(after, before, limit)
                .flatMap(serviceConnection -> {
                    Connection<CustomerDTO> connection = new Connection<>();
                    connection.setPageInfo(serviceConnection.getPageInfo());
                    return Flux.fromIterable(serviceConnection.getEdges())
                            .map(edge -> {
                                Connection<CustomerDTO>.Edge connectionEdge = connection.new Edge();
                                connectionEdge.setNode(customerMapper.mapToCustomerDTO(edge.getNode()));
                                connectionEdge.setCursor(edge.getCursor());
                                return connectionEdge;
                            })
                            .collectList()
                            .map(edges -> {
                                connection.setEdges(edges);
                                return connection;
                            });
                }).doOnError(error -> log.error("Error occurred while fetching services", error));
    }

    @Transactional
    public Mono<Void> save(CustomerInput customerInput) {

        Mono<Tuple2<Boolean, Boolean>> customerValidationMono = Mono.zip(
                validationService.validateEmail(customerInput.getEmailAddress()),
                validationService.validateMobileNumber(customerInput.getMobileNumber())
        );

        return customerValidationMono
                .flatMap(v -> Mono.just(customerInput)
                .map(customerMapper::mapToCustomer)
                .flatMap(customerRepository::save)
                .flatMap(customer -> accountService.save(AccountInput.builder()
                        .customerId(customer.getId())
                        .status(AccountStatus.INACTIVE)
                        .type(AccountType.PERSONAL)
                        .build()))
                        .retryWhen(retryUtil.getRetrySpec())
                .then());
    }

    public Mono<Void> patch(String id, JsonPatch jsonPatch) {
        return customerRepository.findById(id)
                .<Customer>handle((customer, sink) -> {
                    try {
                        sink.next(applyPatch(customer, jsonPatch));
                    } catch (JsonPatchException | JsonProcessingException e) {
                        sink.error(new RuntimeException(e));
                    }
                })
                .flatMap(customerRepository::save)
                .then();
    }

    private Customer applyPatch(Customer targetCustomer, JsonPatch jsonPatch) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = jsonPatch.apply(objectMapper.convertValue(targetCustomer, JsonNode.class));
        return objectMapper.treeToValue(patched, Customer.class);
    }
}
