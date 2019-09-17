package org.intech.forum.controller;

import lombok.RequiredArgsConstructor;
import org.intech.forum.domain.dto.AccountAuthorityDto;
import org.intech.forum.service.AccountAuthorityService;
import org.intech.forum.validation.OnCreate;
import org.intech.forum.validation.OnUpdate;
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
@RequestMapping("/authorities")
public class AccountAuthorityController {

    private final AccountAuthorityService accountAuthorityService;

    @Validated(OnCreate.class)
    @PostMapping
    public AccountAuthorityDto createAccountAuthority(@Valid @RequestBody AccountAuthorityDto dto) {
        return accountAuthorityService.createAccountAuthority(dto);
    }

    @Validated(OnUpdate.class)
    @PutMapping("/{id}")
    public AccountAuthorityDto editAccountAuthority(@PathVariable int id, @Valid @RequestBody AccountAuthorityDto dto) {
        return accountAuthorityService.editAccountAuthority(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteAccountAuthority(@PathVariable int id) {
        accountAuthorityService.deleteAccountAuthority(id);
    }

    @GetMapping("/{id}")
    public AccountAuthorityDto getAccountAuthority(@PathVariable int id) {
        return accountAuthorityService.getAccountAuthority(id);
    }

    @GetMapping
    public List<AccountAuthorityDto> getAllAccountAuthorities() {
        return accountAuthorityService.getAllAccountAuthorities();
    }
}

