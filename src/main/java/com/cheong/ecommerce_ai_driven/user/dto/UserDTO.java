package com.cheong.ecommerce_ai_driven.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @JsonProperty("id")
    private String id;

    @JsonProperty("aud")
    private String aud;

    @JsonProperty("role")
    private String role;

    @JsonProperty("email")
    private String email;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("confirmation_sent_at")
    private OffsetDateTime confirmationSentAt;

    @JsonProperty("recovery_sent_at")
    private OffsetDateTime recoverySentAt;

    @JsonProperty("app_metadata")
    private Map<String, Object> appMetadata;

    @JsonProperty("user_metadata")
    private Map<String, Object> userMetadata;

    @JsonProperty("identities")
    private List<Identity> identities;

    @JsonProperty("created_at")
    private OffsetDateTime createdAt;

    @JsonProperty("banned_until")
    private OffsetDateTime bannedUntil;

    @JsonProperty("confirmed_at")
    private OffsetDateTime confirmedAt;

    @JsonProperty("updated_at")
    private OffsetDateTime updatedAt;

    @JsonProperty("email_change_sent_at")
    private OffsetDateTime emailChangeSentAt;

    @JsonProperty("email_confirmed_at")
    private OffsetDateTime emailConfirmedAt;

    @JsonProperty("invited_at")
    private OffsetDateTime invitedAt;

    @JsonProperty("last_sign_in_at")
    private OffsetDateTime lastSignInAt;

    @JsonProperty("new_email")
    private String newEmail;

    @JsonProperty("new_phone")
    private String newPhone;

    @JsonProperty("phone_change_sent_at")
    private OffsetDateTime phoneChangeSentAt;

    @JsonProperty("phone_confirmed_at")
    private OffsetDateTime phoneConfirmedAt;

    @JsonProperty("reauthentication_sent_at")
    private OffsetDateTime reauthenticationSentAt;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Identity {
        @JsonProperty("created_at")
        private OffsetDateTime createdAt;

        @JsonProperty("id")
        private String id;

        @JsonProperty("identity_data")
        private Map<String, Object> identityData;

        @JsonProperty("last_sign_in_at")
        private OffsetDateTime lastSignInAt;

        @JsonProperty("provider")
        private String provider;

        @JsonProperty("updated_at")
        private OffsetDateTime updatedAt;

        @JsonProperty("user_id")
        private String userId;
    }
}
