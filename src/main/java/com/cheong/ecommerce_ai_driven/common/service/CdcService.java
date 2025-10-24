//package com.cheong.ecommerce_ai_driven.common.service;
//
//import com.cheong.ecommerce_ai_driven.common.dto.ChangeEventDTO;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import io.r2dbc.postgresql.PostgresqlConnectionFactory;
//import io.r2dbc.postgresql.api.PostgresqlReplicationConnection;
//import io.r2dbc.postgresql.replication.LogSequenceNumber;
//import io.r2dbc.postgresql.replication.ReplicationRequest;
//import io.r2dbc.postgresql.replication.ReplicationSlotRequest;
//import jakarta.annotation.PostConstruct;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//import reactor.core.publisher.Mono;
//import reactor.core.scheduler.Schedulers;
//
//import java.nio.charset.StandardCharsets;
//
//@Slf4j
//@Component
//public class CdcService {
//
//    private final PostgresqlConnectionFactory connectionFactory;
//    private final ObjectMapper objectMapper;
//    private final OutboxEventService outboxEventService;
//
//    public CdcService(PostgresqlConnectionFactory connectionFactory,
//                      ObjectMapper objectMapper,
//                      OutboxEventService outboxEventService) {
//        this.connectionFactory = connectionFactory;
//        this.objectMapper = objectMapper;
//        this.outboxEventService = outboxEventService;
//    }
//
//    @PostConstruct
//    public void init() {
//        System.out.println("CDC Service is initialized");
//        Mono<PostgresqlReplicationConnection> replicationConnectionMono = connectionFactory.replication();
//
//        ReplicationSlotRequest replicationSlotRequest = ReplicationSlotRequest
//                .logical()
//                .slotName("business_kafka")
//                .outputPlugin("pgoutput")
//                .temporary()
//                .build();
//
//        replicationConnectionMono.flatMap(connection -> {
//            return connection.createSlot(replicationSlotRequest)
//                    .flatMap(replicationSlot -> {
//                        ReplicationRequest replicationRequest = ReplicationRequest.logical()
//                                .slotName("business_kafka")
//                                .startPosition(replicationSlot.getConsistentPoint())
//                                .slotOption("proto_version", "1")
//                                .slotOption("publication_names", "business_pub")
//                                .build();
//                        return connection.startReplication(replicationRequest);
//                    })
//                    .flatMap(replicationStream -> {
//                        return replicationStream
//                                .map(byteBuf -> {
//                                    byte[] bytes = new byte[byteBuf.readableBytes()];
//                                    byteBuf.readBytes(bytes);
//                                    String json = new String(bytes, StandardCharsets.UTF_8);
//                                    return json;
//                                })
//                                .doOnNext(json -> log.info("Received json: {}", json))
//                                .mapNotNull(jsonString -> {
//                                    try {
//                                        return objectMapper.readValue(jsonString, ChangeEventDTO.class);
//                                    } catch (Exception e) {
//                                        log.error("Error in parsing change event", e);
//                                        return null;
//                                    }
//                                })
//                                .flatMap(this::publishChangeEvent)
//                                .doOnNext(message -> log.info("Received change: {}", message))
//                                .publishOn(Schedulers.boundedElastic())
//                                .doOnError(t -> {
//                                    log.error("Error in replication stream", t);
//                                    replicationStream.close().subscribe();
//                                })
//                                .then();
//                    });
//        }).subscribe();
//    }
//
//    private Mono<Void> publishChangeEvent(ChangeEventDTO changeEventDTO) {
//        if ("I".equals(changeEventDTO.getAction())) {
//            return Mono.empty();
//        }
//        return Mono.empty();
//    }
//
//}
