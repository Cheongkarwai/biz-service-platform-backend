package com.cheong.ecommerce_ai_driven.company.service;

import com.cheong.ecommerce_ai_driven.common.data.Connection;
import com.cheong.ecommerce_ai_driven.common.util.ConnectionUtil;
import com.cheong.ecommerce_ai_driven.company.dto.AddressDTO;
import com.cheong.ecommerce_ai_driven.company.dto.BusinessDTO;
import com.cheong.ecommerce_ai_driven.company.dto.BusinessInput;
import com.cheong.ecommerce_ai_driven.speciality.dto.SpecialityDTO;
import com.cheong.ecommerce_ai_driven.company.entity.*;
import com.cheong.ecommerce_ai_driven.company.mapper.AddressMapper;
import com.cheong.ecommerce_ai_driven.company.mapper.CompanyMapper;
import com.cheong.ecommerce_ai_driven.company.mapper.ServiceMapper;
import com.cheong.ecommerce_ai_driven.company.repository.*;
import com.cheong.ecommerce_ai_driven.speciality.dto.SpecialityInput;
import com.cheong.ecommerce_ai_driven.speciality.model.Speciality;
import com.cheong.ecommerce_ai_driven.speciality.repository.SpecialityRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class CompanyService {

    private final CompanyRepository companyRepository;

    private final CompanyMapper companyMapper;

    private final SpecialityRepository serviceRepository;

    private final ServiceMapper serviceMapper;

    private final AddressRepository addressRepository;

    private final AddressMapper addressMapper;

    private final CompanyServiceRepository companyServiceRepository;

    private final BusinessAddressRepository businessAddressRepository;

    public CompanyService(CompanyRepository companyRepository,
                          CompanyMapper companyMapper,
                          SpecialityRepository serviceRepository,
                          ServiceMapper serviceMapper,
                          AddressRepository addressRepository,
                          AddressMapper addressMapper,
                          CompanyServiceRepository companyServiceRepository,
                          BusinessAddressRepository businessAddressRepository) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
        this.serviceRepository = serviceRepository;
        this.serviceMapper = serviceMapper;
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
        this.companyServiceRepository = companyServiceRepository;
        this.businessAddressRepository = businessAddressRepository;
    }

    @Transactional(readOnly = true)
    public Mono<Connection<BusinessDTO>> findAll(String after, String before, int limit) {
        return companyRepository.findAll(after, before, limit)
                .flatMap(companyConnection -> ConnectionUtil.mapConnection(companyConnection, companyMapper::mapToCompanyDTO))
                .doOnError(error -> log.error("Error occurred while fetching companies", error));
    }

    @Transactional(readOnly = true)
    public Mono<BusinessDTO> findById(String id) {
        return companyRepository.findById(id)
                .map(companyMapper::mapToCompanyDTO)
                .doOnError(error -> log.error("Error occurred while fetching company with id {}", id, error));
    }

    @Transactional(readOnly = true)
    public Flux<AddressDTO> findAllAddressesById(String id) {
        return businessAddressRepository.findAllById(id)
                .flatMap(businessAddress -> addressRepository.findById(businessAddress.getAddressId()))
                .map(addressMapper::mapToAddressDTO);
    }

    @Transactional(readOnly = true)
    public Flux<SpecialityDTO> findAllServicesById(String id) {
        //return serviceRepository.findAll;
        return Flux.empty();
    }

    @Transactional
    public Mono<Void> create(BusinessInput businessInput) {
        log.info("Creating company in progress.. {}", businessInput);
        Business business = companyMapper.mapToBusiness(businessInput);
        List<Address> addresses = addressMapper.mapToAddresses(businessInput.getAddresses());
        List<Speciality> specialities = serviceMapper.mapToServices(businessInput.getServices());

        log.info("name {}", business.getName());
        return Mono.just(business)
                .flatMapMany(company-> companyRepository.save(company)
                        .flatMapMany(savedCompany -> {
                            log.info("Saved company with id {}", savedCompany.getId());
                            return saveAddresses(addresses, savedCompany.getId())
                                    .doOnNext(tuple ->
                                            log.info("Saved addresses and services for company with id {}",
                                                    savedCompany.getId()));
                        })
                        )
                .doOnError(throwable -> log.error("Error occurred while saving company {}", businessInput, throwable))
                .then();
    }

    private Flux<BusinessService> saveServices(List<Speciality> specialities,
                                               String companyId) {
        Flux<BusinessService> serviceFlux = Flux.fromIterable(specialities)
                .map(service -> new BusinessService(
                        UUID.randomUUID().toString(),
                        new BusinessServiceId(
                                companyId,
                                service.getId())
                ));

        return companyServiceRepository.saveAll(serviceFlux);
    }

    private Flux<BusinessAddress> saveAddresses(@NonNull List<Address> addresses,
                                                String companyId) {
        Flux<Address> addressFlux = Flux.fromIterable(addresses)
                .map(address -> address);

        return addressRepository.saveAll(addressFlux)
                .flatMap(address -> businessAddressRepository.save(new BusinessAddress(companyId, address.getId())));
    }

}
