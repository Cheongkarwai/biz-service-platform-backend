package com.cheong.ecommerce_ai_driven.common.web;

import jakarta.validation.GroupSequence;

@GroupSequence({BasicValidation.class, AdvanceValidation.class})
public interface OrderedValidation {
}
