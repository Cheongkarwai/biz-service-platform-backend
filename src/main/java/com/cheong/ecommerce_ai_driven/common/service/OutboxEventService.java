package com.cheong.ecommerce_ai_driven.common.service;

import com.cheong.ecommerce_ai_driven.common.dto.OutboxEvent;
import com.cheong.ecommerce_ai_driven.common.repository.OutboxEventRepository;
import com.cheong.ecommerce_ai_driven.company.entity.Business;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Service responsible for handling operations related to outbox events.
 * This service is designed to facilitate the creation and saving of events
 * to an outbox table for eventual processing.
 */
@Service
public class OutboxEventService {

    private final OutboxEventRepository outboxEventRepository;
    private final ObjectMapper objectMapper;

    public OutboxEventService(OutboxEventRepository outboxEventRepository, ObjectMapper objectMapper) {
        this.outboxEventRepository = outboxEventRepository;
        this.objectMapper = objectMapper;
    }

    /**
     * Saves a "company created" event to the outbox repository for the provided company and business details.
     * The event includes information about the company and the business, which is stored
     * in the outbox table for further processing.
     *
     * @param companyId the unique identifier of the company for which the event is being created
     * @param business the business entity containing details about the business associated with the company
     * @return a Mono<Void> indicating the completion of the save operation or an error if the save fails
     */
    public Mono<Void> saveCompanyCreatedOutboxEvent(String companyId,
                                                    Business business){
        OutboxEvent outboxEvent = new OutboxEvent();
        outboxEvent.setAggregateId(companyId);
        outboxEvent.setAggregateType(business.getClass().getSimpleName());
        outboxEvent.setEventType("companyCreated");
        try {
            outboxEvent.setPayload(objectMapper.writeValueAsString(business));
        }
        catch (Exception e){
            return Mono.error(new RuntimeException(e));
        }

        return this.outboxEventRepository.save(outboxEvent)
                .then();
    }
}
