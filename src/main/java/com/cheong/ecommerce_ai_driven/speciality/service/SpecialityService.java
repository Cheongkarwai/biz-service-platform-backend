package com.cheong.ecommerce_ai_driven.speciality.service;

import com.cheong.ecommerce_ai_driven.common.data.Connection;
import com.cheong.ecommerce_ai_driven.common.util.ConnectionUtil;
import com.cheong.ecommerce_ai_driven.company.mapper.ServiceMapper;
import com.cheong.ecommerce_ai_driven.speciality.dto.SpecialityDTO;
import com.cheong.ecommerce_ai_driven.speciality.dto.SpecialityInput;
import com.cheong.ecommerce_ai_driven.speciality.repository.SpecialityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Slf4j
public class SpecialityService {

    private final SpecialityRepository specialityRepository;

    private final ServiceMapper serviceMapper;

    public SpecialityService(SpecialityRepository specialityRepository,
                             ServiceMapper serviceMapper) {
        this.specialityRepository = specialityRepository;
        this.serviceMapper = serviceMapper;
    }

    @Transactional(readOnly = true)
    public Mono<SpecialityDTO> findById(String id) {
        return specialityRepository.findById(id)
                .map(serviceMapper::mapToServiceDTO)
                .doOnError(error -> log.error("Error occurred while fetching service with id {}", id, error));
    }

    @Transactional(readOnly = true)
    public Mono<Connection<SpecialityDTO>> findAll(String after, String before, int limit, List<String> categoryIds) {
        return specialityRepository.findAll(after, before, limit, categoryIds)
                .flatMap(serviceConnection -> ConnectionUtil.mapConnection(serviceConnection, serviceMapper::mapToServiceDTO))
                .doOnError(error -> log.error("Error occurred while fetching services", error));
    }

    public Mono<Void> save(SpecialityInput specialityInput) {
        return Mono.just(specialityInput)
                .map(serviceMapper::mapToService)
                .flatMap(specialityRepository::save)
                .then();
    }
}
