//package com.cheong.ecommerce_ai_driven.config;
//
//import com.cheong.ecommerce_ai_driven.common.service.OutboxEventService;
//import com.cheong.ecommerce_ai_driven.company.entity.Business;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import io.debezium.engine.ChangeEvent;
//import io.debezium.engine.DebeziumEngine;
//import io.debezium.engine.format.JsonByteArray;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.integration.debezium.dsl.Debezium;
//import org.springframework.integration.dsl.IntegrationFlow;
//
//import java.io.IOException;
//import java.util.Properties;
//
//@Configuration
//@Slf4j
//public class DebeziumConfig {
//
//
//    private final OutboxEventService outboxEventService;
//
//    public DebeziumConfig(OutboxEventService outboxEventService) {
//        this.outboxEventService = outboxEventService;
//    }
//
//    @Bean
//    public Properties debeziumProperties() {
//        Properties props = new Properties();
//
//        props.setProperty("name", "business-connector");
//        props.setProperty("connector.class", "io.debezium.connector.postgresql.PostgresConnector");
//        props.setProperty("plugin.name", "pgoutput");
//
//        // Postgres connection
//        props.setProperty("database.hostname", "localhost");
//        props.setProperty("database.port", "5432");
//        props.setProperty("database.user", "postgres");
//        props.setProperty("database.password", "root");
//        props.setProperty("database.dbname", "biz_services_platform");
//
//        // Replication slot + publication
//        props.setProperty("slot.name", "business_kafka");
//        props.setProperty("publication.name", "business_pub");
//        props.setProperty("topic.prefix", "business-db");
//
//        // Which tables to capture
//        props.setProperty("table.include.list", "public.business");
//
//        // Offset storage (keeps track of where you left off)
//        props.setProperty("offset.storage", "org.apache.kafka.connect.storage.FileOffsetBackingStore");
//        props.setProperty("offset.storage.file.filename", "/tmp/debezium-offsets.dat");
//        props.setProperty("offset.flush.interval.ms", "60000");
//
//        props.setProperty("transforms", "unwrap");
//        props.setProperty("transforms.unwrap.type", "io.debezium.transforms.ExtractNewRecordState");
//        props.setProperty("transforms.unwrap.drop.tombstones", "true");
//        props.setProperty("transforms.unwrap.delete.handling.mode", "rewrite"); // optional
//
//        return props;
//    }
//
//    @Bean
//    public DebeziumEngine.Builder<ChangeEvent<byte[],byte[]>> debeziumEngineBuilder(Properties debeziumProperties) {
//        return DebeziumEngine.create(JsonByteArray.class)
//                .using(debeziumProperties);
//    }
//
//
//    @Bean
//    public IntegrationFlow debeziumInbound(
//            DebeziumEngine.Builder<ChangeEvent<byte[], byte[]>> debeziumEngineBuilder, ObjectMapper objectMapper) {
//
//        return IntegrationFlow
//                .from(Debezium
//                        .inboundChannelAdapter(debeziumEngineBuilder))
//                .<byte[], JsonNode>transform(bytes -> {
//                    try {
//                        return objectMapper.readTree(new String(bytes));
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                })
//                .handle(m -> {
//                    JsonNode jsonNode = (JsonNode) m.getPayload();
//                    try {
//                        Business business = objectMapper.readValue(jsonNode.get("payload").toString(), Business.class);
//                        if(business != null) {
//                            outboxEventService.saveCompanyCreatedOutboxEvent(business.getId().toString(), business)
//                                    .subscribe();
//                        }
//                    } catch (JsonProcessingException e) {
//                        log.error("Error occurred while processing business event {}", jsonNode.get("payload").toString(), e);
//                    }
//                })
//                .get();
//    }
//}
