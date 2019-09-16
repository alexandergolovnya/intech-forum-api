package org.intech.forum.service;

import lombok.RequiredArgsConstructor;
import org.intech.forum.domain.repository.OAuthClientRepository;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

/**
 * @author: Alexander Golovnya <mail@alexandergolovnya.ru>
 * @created: 2019/09/16
 */
@Service
@RequiredArgsConstructor
public class CustomClientDetailsService implements ClientDetailsService {

    private final OAuthClientRepository oAuthClientRepository;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        return oAuthClientRepository.findByClientId(clientId)
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("OAuth client with client id %s doesn't exist", clientId)));
    }
}
