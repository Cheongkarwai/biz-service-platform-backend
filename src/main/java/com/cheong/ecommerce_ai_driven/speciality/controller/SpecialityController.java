package com.cheong.ecommerce_ai_driven.speciality.controller;

import com.cheong.ecommerce_ai_driven.common.paging.dto.Connection;
import com.cheong.ecommerce_ai_driven.speciality.dto.SpecialityInput;
import com.cheong.ecommerce_ai_driven.speciality.dto.SpecialityDTO;
import com.cheong.ecommerce_ai_driven.company.service.CompanyService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/specialities")
public class SpecialityController {

    private final CompanyService companyService;

    public SpecialityController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/{id}")
    public Mono<SpecialityDTO> findById(@PathVariable String id){
        return companyService.findServiceById(id);
    }

    @GetMapping
    public Mono<Connection<SpecialityDTO>> findAll(@RequestParam(required = false) String after,
                                                   @RequestParam(required = false) String before,
                                                   @RequestParam int limit){

        return companyService.findAllServices(after, before, limit);
    }

    @PostMapping
    public Mono<Void> create(@RequestBody SpecialityInput specialityInput){
        return companyService.saveSpeciality(specialityInput);
    }
}
