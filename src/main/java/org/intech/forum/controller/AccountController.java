package org.intech.forum.controller;

import lombok.RequiredArgsConstructor;
import org.intech.forum.domain.dto.AccountDto;
import org.intech.forum.service.AccountService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author: Alexander Golovnya <mail@alexandergolovnya.ru>
 * @created: 2019/09/17
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public AccountDto createAccount(@Valid @RequestBody AccountDto dto) {
        return accountService.createAccount(dto);
    }

    @PutMapping("/{id}")
    public AccountDto editAccount(@PathVariable int id, @Valid @RequestBody AccountDto dto) {
        return accountService.editAccount(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable int id) {
        accountService.deleteAccount(id);
    }

    @GetMapping("/{id}")
    public AccountDto getAccount(@PathVariable int id) {
        return accountService.getAccount(id);
    }

    @GetMapping("/email/{email}")
    public AccountDto getAccountByEmail(@PathVariable String email) {
        return accountService.getAccountByEmail(email);
    }

    @GetMapping("/phone/{phone}")
    public AccountDto getAccountByPhone(@PathVariable String phone) {
        return accountService.getAccountByPhone(phone);
    }

    @GetMapping
    public List<AccountDto> getAllAccounts() {
        return accountService.getAllAccounts();
    }
}

