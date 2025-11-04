package com.cheong.ecommerce_ai_driven.speciality.repository;

import com.cheong.ecommerce_ai_driven.common.data.Connection;
import com.cheong.ecommerce_ai_driven.speciality.model.Speciality;
import reactor.core.publisher.Mono;

public interface CustomSpecialityRepository {

    Mono<Connection<Speciality>> findAll(String after, String before, int limit);
}
