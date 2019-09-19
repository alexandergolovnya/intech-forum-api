package org.intech.forum.controller;

import lombok.RequiredArgsConstructor;
import org.intech.forum.domain.dto.AccountDto;
import org.intech.forum.service.AccountService;
import org.intech.forum.validation.OnCreate;
import org.intech.forum.validation.OnUpdate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author: Alexander Golovnya <mail@alexandergolovnya.ru>
 * @created: 2019/09/17
 */
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    @Validated(OnCreate.class)
    @PostMapping
    public AccountDto createAccount(@Valid @RequestBody AccountDto dto) {
        return accountService.createAccount(dto);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @Validated(OnUpdate.class)
    @PutMapping("/{id}")
    public AccountDto editAccount(@PathVariable int id, @Valid @RequestBody AccountDto dto) {
        return accountService.editAccount(id, dto);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable int id) {
        accountService.deleteAccount(id);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/{id}")
    public AccountDto getAccount(@PathVariable int id) {
        return accountService.getAccount(id);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/email/{email}")
    public AccountDto getAccountByEmail(@PathVariable String email) {
        return accountService.getAccountByEmail(email);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/phone/{phone}")
    public AccountDto getAccountByPhone(@PathVariable String phone) {
        return accountService.getAccountByPhone(phone);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping
    public List<AccountDto> getAllAccounts() {
        return accountService.getAllAccounts();
    }
}

