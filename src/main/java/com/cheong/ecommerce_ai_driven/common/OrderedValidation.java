package com.cheong.ecommerce_ai_driven.common;

import jakarta.validation.GroupSequence;

@GroupSequence({BasicValidation.class, AdvanceValidation.class})
public interface OrderedValidation {
}
