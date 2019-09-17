package org.intech.forum.controller;

import lombok.RequiredArgsConstructor;
import org.intech.forum.domain.dto.OAuthClientDto;
import org.intech.forum.service.OAuthClientService;
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
@RequestMapping("/oauth-clients")
public class OAuthClientController {

    private final OAuthClientService oAuthClientService;

    @Validated(OnCreate.class)
    @PostMapping
    public OAuthClientDto createOAuthClient(@Valid @RequestBody OAuthClientDto dto) {
        return oAuthClientService.createOAuthClient(dto);
    }

    @Validated(OnUpdate.class)
    @PutMapping("/{id}")
    public OAuthClientDto editOAuthClient(@PathVariable int id, @Valid @RequestBody OAuthClientDto dto) {
        return oAuthClientService.editOAuthClient(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteOAuthClient(@PathVariable int id) {
        oAuthClientService.deleteOAuthClient(id);
    }

    @GetMapping("/{id}")
    public OAuthClientDto getOAuthClient(@PathVariable int id) {
        return oAuthClientService.getOAuthClient(id);
    }

    @GetMapping("/id/{clientId}")
    public OAuthClientDto getOAuthClientByClientId(@PathVariable String clientId) {
        return oAuthClientService.getOAuthClientByClientId(clientId);
    }

    @GetMapping
    public List<OAuthClientDto> getAllOAuthClients() {
        return oAuthClientService.getAllOAuthClients();
    }
}

