package com.cheong.ecommerce_ai_driven.customer.api;

import com.cheong.ecommerce_ai_driven.common.OrderedValidation;
import com.cheong.ecommerce_ai_driven.customer.dto.CustomerDTO;
import com.cheong.ecommerce_ai_driven.customer.input.CustomerInput;
import com.cheong.ecommerce_ai_driven.customer.service.CustomerService;
import com.github.fge.jsonpatch.JsonPatch;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{id}")
    public Mono<CustomerDTO> findById(@PathVariable String id){
        return customerService.findById(id);
    }

    @PostMapping
    public Mono<Void> save(@Validated(OrderedValidation.class) @RequestBody CustomerInput customerInput){
        return customerService.save(customerInput);
    }

    @PatchMapping(value = "/{id}", consumes = "application/json-patch+json")
    public Mono<ResponseEntity<Void>> partialUpdate(@PathVariable String id, @RequestBody JsonPatch jsonPatch){
        return customerService.patch(id, jsonPatch)
                .then(Mono.fromCallable(() -> ResponseEntity.noContent().build()));
    }
}
