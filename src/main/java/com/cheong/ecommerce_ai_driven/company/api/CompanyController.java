package com.cheong.ecommerce_ai_driven.company.api;

import com.cheong.ecommerce_ai_driven.common.paging.dto.Connection;
import com.cheong.ecommerce_ai_driven.company.dto.AddressDTO;
import com.cheong.ecommerce_ai_driven.company.dto.BusinessDTO;
import com.cheong.ecommerce_ai_driven.company.dto.BusinessInput;
import com.cheong.ecommerce_ai_driven.company.dto.ServiceDTO;
import com.cheong.ecommerce_ai_driven.company.service.CompanyService;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.http.HttpHeaders;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/companies")
public class CompanyController {

    private CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/{id}")
    public Mono<BusinessDTO> findById(@PathVariable UUID id){
        return companyService.findById(id);
    }
    
    @GetMapping
    public @ResponseBody Mono<Connection<BusinessDTO>> findAll(@RequestParam(required = false) String after,
                                                 @RequestParam(required = false) String before,
                                                 @RequestParam int limit) {
        return companyService.findAll(after, before, limit);
    }

    @GetMapping("/{id}/addresses")
    public Flux<AddressDTO> findAddressesByCompanyId(@PathVariable UUID id){
        return companyService.findAllAddressesById(id);
    }

    @GetMapping("/{id}/services")
    public Flux<ServiceDTO> findServicesByCompanyId(@PathVariable String id){
        return companyService.findAllServicesById(id);
    }

    @PostMapping
    public Mono<Void> create(@RequestBody BusinessInput businessInput){
        return companyService.create(businessInput);
    }

}
