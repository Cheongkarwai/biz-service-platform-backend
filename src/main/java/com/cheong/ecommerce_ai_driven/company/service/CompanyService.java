package com.cheong.ecommerce_ai_driven.company.service;

import com.cheong.ecommerce_ai_driven.common.data.Connection;
import com.cheong.ecommerce_ai_driven.common.util.ConnectionUtil;
import com.cheong.ecommerce_ai_driven.company.dto.AddressDTO;
import com.cheong.ecommerce_ai_driven.company.dto.BusinessDTO;
import com.cheong.ecommerce_ai_driven.company.dto.BusinessInput;
import com.cheong.ecommerce_ai_driven.company.entity.*;
import com.cheong.ecommerce_ai_driven.company.mapper.AddressMapper;
import com.cheong.ecommerce_ai_driven.company.mapper.CompanyMapper;
import com.cheong.ecommerce_ai_driven.company.mapper.ServiceMapper;
import com.cheong.ecommerce_ai_driven.company.repository.AddressRepository;
import com.cheong.ecommerce_ai_driven.company.repository.BusinessAddressRepository;
import com.cheong.ecommerce_ai_driven.company.repository.CompanyRepository;
import com.cheong.ecommerce_ai_driven.company.repository.CompanyServiceRepository;
import com.cheong.ecommerce_ai_driven.customer.validation.ValidationService;
import com.cheong.ecommerce_ai_driven.speciality.dto.SpecialityDTO;
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

    private final ServiceMapper serviceMapper;

    private final AddressRepository addressRepository;

    private final AddressMapper addressMapper;

    private final CompanyServiceRepository companyServiceRepository;

    private final BusinessAddressRepository businessAddressRepository;

    private final ValidationService validationService;

    public CompanyService(CompanyRepository companyRepository,
                          CompanyMapper companyMapper,
                          ServiceMapper serviceMapper,
                          AddressRepository addressRepository,
                          AddressMapper addressMapper,
                          CompanyServiceRepository companyServiceRepository,
                          BusinessAddressRepository businessAddressRepository,
                          ValidationService validationService) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
        this.serviceMapper = serviceMapper;
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
        this.companyServiceRepository = companyServiceRepository;
        this.businessAddressRepository = businessAddressRepository;
        this.validationService = validationService;
    }

    @Transactional(readOnly = true)
    public Mono<Connection<BusinessDTO>> findAll(String after, String before, int limit, List<String> serviceIds) {
        return companyRepository.findAll(after, before, limit, serviceIds)
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
        Business business = companyMapper.mapToBusiness(businessInput);
        List<Address> addresses = addressMapper.mapToAddresses(businessInput.getAddresses());
        List<Speciality> specialities = serviceMapper.mapToServices(businessInput.getServices());

        return validationService.validateEmail(business.getEmail())
                .filter(isValid-> isValid)
                .map(isValid-> business)
                .flatMapMany(company-> companyRepository.save(company)
                        .flatMapMany(savedCompany -> saveAddresses(addresses, savedCompany.getId())
                                .doOnNext(tuple ->
                                        log.info("Saved addresses and services for company with id {}",
                                                savedCompany.getId())))
                        )
                .doOnError(throwable -> log.error("Error occurred while saving company {}", businessInput, throwable))
                .then();
    }


    private Flux<BusinessAddress> saveAddresses(@NonNull List<Address> addresses,
                                                String companyId) {
        Flux<Address> addressFlux = Flux.fromIterable(addresses)
                .map(address -> address);

        return addressRepository.saveAll(addressFlux)
                .flatMap(address -> businessAddressRepository.save(new BusinessAddress(companyId, address.getId())));
    }

    public Mono<BusinessDTO> findByEmail(String email) {
        return companyRepository.findByEmail(email)
                .map(companyMapper::mapToCompanyDTO);
    }
}
