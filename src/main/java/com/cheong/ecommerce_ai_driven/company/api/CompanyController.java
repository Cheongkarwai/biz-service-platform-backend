package com.cheong.ecommerce_ai_driven.company.api;

import com.cheong.ecommerce_ai_driven.common.data.Connection;
import com.cheong.ecommerce_ai_driven.common.web.AdvanceValidation;
import com.cheong.ecommerce_ai_driven.common.web.BasicValidation;
import com.cheong.ecommerce_ai_driven.company.dto.AddressDTO;
import com.cheong.ecommerce_ai_driven.company.dto.BusinessDTO;
import com.cheong.ecommerce_ai_driven.company.dto.BusinessInput;
import com.cheong.ecommerce_ai_driven.speciality.dto.SpecialityDTO;
import com.cheong.ecommerce_ai_driven.company.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/v1/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/{id}")
    public Mono<BusinessDTO> findById(@PathVariable String id){
        return companyService.findById(id);
    }
    
    @GetMapping
    public @ResponseBody Mono<Connection<BusinessDTO>> findAll(@RequestParam(required = false) String after,
                                                 @RequestParam(required = false) String before,
                                                 @RequestParam int limit,
                                                 @RequestParam(required = false) List<String> serviceIds) {
        return companyService.findAll(after, before, limit, serviceIds);
    }

    @GetMapping("/{id}/addresses")
    public Flux<AddressDTO> findAddressesByCompanyId(@PathVariable String id){
        return companyService.findAllAddressesById(id);
    }

    @GetMapping("/{id}/services")
    public Flux<SpecialityDTO> findServicesByCompanyId(@PathVariable String id){
        return companyService.findAllServicesById(id);
    }

    @PostMapping
    public Mono<Void> create(@Validated(value = {BasicValidation.class, AdvanceValidation.class}) @RequestBody BusinessInput businessInput){
        return companyService.create(businessInput);
    }

}
