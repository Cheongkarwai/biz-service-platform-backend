package com.cheong.ecommerce_ai_driven.webhook.service;

import com.cheong.ecommerce_ai_driven.account.dto.AccountType;
import com.cheong.ecommerce_ai_driven.account.service.AccountService;
import com.cheong.ecommerce_ai_driven.common.web.OutboxEvent;
import com.cheong.ecommerce_ai_driven.company.service.CompanyService;
import com.cheong.ecommerce_ai_driven.customer.service.CustomerService;
import com.cheong.ecommerce_ai_driven.user.dto.UserDTO;
import com.cheong.ecommerce_ai_driven.webhook.dto.SupabaseCdcWebhookPayload;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CallbackService {

    private final OutboxEventService outboxEventService;

    private final CustomerService customerService;

    private final CompanyService companyService;

    private final AccountService accountService;


    public CallbackService(OutboxEventService outboxEventService,
                           CompanyService companyService,
                           CustomerService customerService,
                           AccountService accountService) {
        this.outboxEventService = outboxEventService;
        this.companyService = companyService;
        this.customerService = customerService;
        this.accountService = accountService;
    }

    public Mono<Void> handleOutboxEvent(SupabaseCdcWebhookPayload<OutboxEvent> payload) {
        return outboxEventService.notify(payload);
    }

    public Mono<Void> handleUserCreatedEvent(SupabaseCdcWebhookPayload<UserDTO> payload) {
        UserDTO userDTO = payload.getRecord();
        if (userDTO == null) {
            return Mono.empty();
        }
        return Mono.just(userDTO)
                .flatMap(user -> {
                    if (AccountType.PERSONAL.equals(user.getAppMetadata().get("account_type"))) {
                       return handleCustomerAccountCreatedEvent(user);
                    } else {
                        return handleCompanyAccountCreatedEvent(user);
                    }
                }).then();
    }

    private Mono<Void> handleCustomerAccountCreatedEvent(UserDTO user) {
        return customerService.findByEmail(user.getEmail())
                .flatMap(customerDTO -> accountService.findByCustomerId(customerDTO.getId())
                        .map(account -> {
                            account.setSupabaseUserId(user.getId());
                            return account;
                        })).
                flatMap(accountService::save)
                .then();
    }

    private Mono<Void> handleCompanyAccountCreatedEvent(UserDTO user){
        return companyService.findByEmail(user.getEmail())
                .flatMap(companyDTO -> accountService.findByCustomerId(companyDTO.getId())
                        .map(account -> {
                            account.setSupabaseUserId(user.getId());
                            return account;
                        }))
                .flatMap(accountService::save)
                .then();
    }
}
