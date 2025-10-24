package com.cheong.ecommerce_ai_driven.common.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import io.debezium.data.Json;
import org.springframework.core.ResolvableType;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ReactiveHttpInputMessage;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class JsonPatchHttpMessageConverter implements HttpMessageReader<JsonPatch> {

    private final ObjectMapper objectMapper;

    public JsonPatchHttpMessageConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public List<MediaType> getReadableMediaTypes() {
        return Collections.singletonList(MediaType.valueOf("application/json-patch+json"));
    }

    @Override
    public boolean canRead(ResolvableType elementType, MediaType mediaType) {
        return MediaType.valueOf("application/json-patch+json").includes(mediaType);
    }

    @Override
    public Flux<JsonPatch> read(ResolvableType elementType, ReactiveHttpInputMessage message, Map<String, Object> hints) {
        return readMono(elementType, message, hints).flux();
    }


    @Override
    public Mono<JsonPatch> readMono(ResolvableType elementType, ReactiveHttpInputMessage message, Map<String, Object> hints) {
        return DataBufferUtils.join(message.getBody()).handle((buffer, sink) -> {
            try {
                JsonNode jsonNode = objectMapper.readTree(buffer.asInputStream());
                sink.next(JsonPatch.fromJson(jsonNode));
            } catch (IOException e) {
                sink.error(new RuntimeException("Failed to parse JSON Patch", e));
            } finally {
                DataBufferUtils.release(buffer);
            }
        });
    }
}