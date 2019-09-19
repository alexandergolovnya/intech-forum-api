package org.intech.forum.controller;

import lombok.RequiredArgsConstructor;
import org.intech.forum.domain.dto.AccountAuthorityDto;
import org.intech.forum.service.account.AccountAuthorityService;
import org.intech.forum.validation.marker.OnCreate;
import org.intech.forum.validation.marker.OnUpdate;
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
@RequestMapping("/authorities")
public class AccountAuthorityController {

    private final AccountAuthorityService accountAuthorityService;

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @Validated(OnCreate.class)
    @PostMapping
    public AccountAuthorityDto createAccountAuthority(@Valid @RequestBody AccountAuthorityDto dto) {
        return accountAuthorityService.createAccountAuthority(dto);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @Validated(OnUpdate.class)
    @PutMapping("/{id}")
    public AccountAuthorityDto editAccountAuthority(@PathVariable int id, @Valid @RequestBody AccountAuthorityDto dto) {
        return accountAuthorityService.editAccountAuthority(id, dto);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteAccountAuthority(@PathVariable int id) {
        accountAuthorityService.deleteAccountAuthority(id);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public AccountAuthorityDto getAccountAuthority(@PathVariable int id) {
        return accountAuthorityService.getAccountAuthority(id);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @GetMapping
    public List<AccountAuthorityDto> getAllAccountAuthorities() {
        return accountAuthorityService.getAllAccountAuthorities();
    }
}

