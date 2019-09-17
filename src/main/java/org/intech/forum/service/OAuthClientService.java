package org.intech.forum.service;

import org.intech.forum.domain.dto.OAuthClientDto;

import java.util.List;

/**
 * @author: Alexander Golovnya <mail@alexandergolovnya.ru>
 * @created: 2019/09/16
 */
public interface OAuthClientService {

    OAuthClientDto createOAuthClient(OAuthClientDto oAuthClientDto);

    OAuthClientDto editOAuthClient(int id, OAuthClientDto oAuthClientDto);

    void deleteOAuthClient(int id);

    OAuthClientDto getOAuthClient(int id);

    OAuthClientDto getOAuthClientByClientId(String clientId);

    List<OAuthClientDto> getAllOAuthClients();
}
