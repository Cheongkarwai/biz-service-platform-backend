package com.cheong.ecommerce_ai_driven.company.service;

import com.cheong.ecommerce_ai_driven.common.paging.dto.Connection;
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
                .flatMap(companyConnection -> {
                    Connection<BusinessDTO> connection = new Connection<>();
                    connection.setPageInfo(companyConnection.getPageInfo());
                    return Flux.fromIterable(companyConnection.getEdges())
                            .map(edge -> {
                                Connection<BusinessDTO>.Edge connectionEdge = connection.new Edge();
                                connectionEdge.setNode(companyMapper.mapToCompanyDTO(edge.getNode()));
                                connectionEdge.setCursor(edge.getCursor());
                                return connectionEdge;
                            })
                            .collectList()
                            .map(edges -> {
                                connection.setEdges(edges);
                                return connection;
                            });
                })
                .doOnError(error -> log.error("Error occurred while fetching companies", error));
    }

    @Transactional(readOnly = true)
    public Mono<BusinessDTO> findById(String id) {
        return companyRepository.findById(id)
                .map(companyMapper::mapToCompanyDTO)
                .doOnError(error -> log.error("Error occurred while fetching company with id {}", id, error));
    }

    @Transactional(readOnly = true)
    public Mono<SpecialityDTO> findServiceById(String id) {
        return serviceRepository.findById(id)
                .map(serviceMapper::mapToServiceDTO)
                .doOnError(error -> log.error("Error occurred while fetching service with id {}", id, error));
    }

    @Transactional(readOnly = true)
    public Mono<Connection<SpecialityDTO>> findAllServices(String after, String before, int limit) {
        return serviceRepository.findAll(after, before, limit)
                .flatMap(serviceConnection -> {
                    Connection<SpecialityDTO> connection = new Connection<>();
                    connection.setPageInfo(serviceConnection.getPageInfo());
                    return Flux.fromIterable(serviceConnection.getEdges())
                            .map(edge -> {
                                Connection<SpecialityDTO>.Edge connectionEdge = connection.new Edge();
                                connectionEdge.setNode(serviceMapper.mapToServiceDTO(edge.getNode()));
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
                .doOnError(throwable -> {
                    throwable.printStackTrace();
                    log.error("Error occurred while saving company {}", businessInput, throwable);
                })
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

    private Flux<BusinessAddress> saveAddresses(List<Address> addresses,
                                                String companyId) {
        Flux<Address> addressFlux = Flux.fromIterable(addresses)
                .map(address -> address);

        return addressRepository.saveAll(addressFlux)
                .doOnNext(address -> {
                    log.info("Saved address with id {}", address.getId());
                    log.info("Saved company with id {}", companyId);
                })
                .flatMap(address -> businessAddressRepository.save(new BusinessAddress(companyId, address.getId())));
    }

    public Mono<Void> saveSpeciality(SpecialityInput specialityInput) {
        return Mono.just(specialityInput)
                .map(serviceMapper::mapToService)
                .flatMap(serviceRepository::save)
                .then();
    }
}
