package com.cheong.ecommerce_ai_driven.speciality.controller;

import com.cheong.ecommerce_ai_driven.common.data.Connection;
import com.cheong.ecommerce_ai_driven.speciality.dto.SpecialityInput;
import com.cheong.ecommerce_ai_driven.speciality.dto.SpecialityDTO;
import com.cheong.ecommerce_ai_driven.company.service.CompanyService;
import com.cheong.ecommerce_ai_driven.speciality.service.SpecialityService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/specialities")
public class SpecialityController {

    private final SpecialityService specialityService;

    public SpecialityController(SpecialityService specialityService) {
        this.specialityService = specialityService;
    }

    @GetMapping("/{id}")
    public Mono<SpecialityDTO> findById(@PathVariable String id){
        return specialityService.findById(id);
    }

    @GetMapping
    public Mono<Connection<SpecialityDTO>> findAll(@RequestParam(required = false) String after,
                                                   @RequestParam(required = false) String before,
                                                   @RequestParam int limit){

        return specialityService.findAll(after, before, limit);
    }

    @PostMapping
    public Mono<Void> create(@Valid @RequestBody SpecialityInput specialityInput){
        return specialityService.save(specialityInput);
    }
}
