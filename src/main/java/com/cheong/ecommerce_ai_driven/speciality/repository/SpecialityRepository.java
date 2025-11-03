package com.cheong.ecommerce_ai_driven.speciality.repository;

import com.cheong.ecommerce_ai_driven.speciality.model.Speciality;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface SpecialityRepository extends R2dbcRepository<Speciality, String>, CustomSpecialityRepository {
}
