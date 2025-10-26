package com.cheong.ecommerce_ai_driven.company.api;

import com.cheong.ecommerce_ai_driven.common.paging.dto.Connection;
import com.cheong.ecommerce_ai_driven.company.dto.ServiceDTO;
import com.cheong.ecommerce_ai_driven.company.service.CompanyService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/services")
public class ServiceController {

    private final CompanyService companyService;

    public ServiceController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/{id}")
    public Mono<ServiceDTO> findById(@PathVariable String id){
        return companyService.findServiceById(id);
    }

    @GetMapping
    public Mono<Connection<ServiceDTO>> findAll(@RequestParam(required = false) String after,
                                                @RequestParam(required = false) String before,
                                                @RequestParam int limit){

        return companyService.findAllServices(after, before, limit);
    }
}
