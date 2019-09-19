package org.intech.forum.controller;

import lombok.RequiredArgsConstructor;
import org.intech.forum.domain.dto.OAuthClientDto;
import org.intech.forum.service.OAuthClientService;
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
@RequestMapping("/oauth-clients")
public class OAuthClientController {

    private final OAuthClientService oAuthClientService;

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @Validated(OnCreate.class)
    @PostMapping
    public OAuthClientDto createOAuthClient(@Valid @RequestBody OAuthClientDto dto) {
        return oAuthClientService.createOAuthClient(dto);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @Validated(OnUpdate.class)
    @PutMapping("/{id}")
    public OAuthClientDto editOAuthClient(@PathVariable int id, @Valid @RequestBody OAuthClientDto dto) {
        return oAuthClientService.editOAuthClient(id, dto);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteOAuthClient(@PathVariable int id) {
        oAuthClientService.deleteOAuthClient(id);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public OAuthClientDto getOAuthClient(@PathVariable int id) {
        return oAuthClientService.getOAuthClient(id);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @GetMapping("/id/{clientId}")
    public OAuthClientDto getOAuthClientByClientId(@PathVariable String clientId) {
        return oAuthClientService.getOAuthClientByClientId(clientId);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @GetMapping
    public List<OAuthClientDto> getAllOAuthClients() {
        return oAuthClientService.getAllOAuthClients();
    }
}

