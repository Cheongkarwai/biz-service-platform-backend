package com.cheong.ecommerce_ai_driven.account.api;

import com.cheong.ecommerce_ai_driven.account.dto.AccountDTO;
import com.cheong.ecommerce_ai_driven.account.service.AccountService;
import com.cheong.ecommerce_ai_driven.user.dto.RecoverPasswordRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PutMapping("/{id}:activate")
    public Mono<AccountDTO> activate(@PathVariable String id){
        return accountService.activate(id);
    }

    @PutMapping("/{id}:deactivate")
    public Mono<AccountDTO> deactivate(@PathVariable String id){
        return accountService.deactivate(id);
    }

    @PutMapping("/{id}:suspend")
    public Mono<AccountDTO> suspend(@PathVariable String id){
        return accountService.suspend(id);
    }

    @PostMapping("/password:recover")
    public Mono<Void> recoverPassword(@Validated @RequestBody RecoverPasswordRequest recoverPasswordRequest){
        return accountService.recoverPassword(recoverPasswordRequest);
    }
}
