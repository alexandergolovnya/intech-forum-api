package org.intech.forum.service;

import lombok.RequiredArgsConstructor;
import org.intech.forum.domain.dto.OAuthClientDto;
import org.intech.forum.domain.entity.OAuthClient;
import org.intech.forum.domain.repository.OAuthClientRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang.StringUtils.isEmpty;
import static org.intech.forum.utils.ModelMapperUtils.map;
import static org.intech.forum.utils.ModelMapperUtils.mapAll;

/**
 * @author: Alexander Golovnya <mail@alexandergolovnya.ru>
 * @created: 2019/09/17
 */
@Service
@RequiredArgsConstructor
public class OAuthClientServiceImpl implements OAuthClientService {

    private final OAuthClientRepository oAuthClientRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuthClientDto createOAuthClient(OAuthClientDto dto) {
        checkOAuthClientIdForUniqueness(dto);

        final OAuthClient oAuthClient = map(dto, OAuthClient.class);
        oAuthClient.setClientSecret(passwordEncoder.encode(dto.getClientSecret()));
        oAuthClientRepository.save(oAuthClient);

        return map(oAuthClient, OAuthClientDto.class);
    }

    @Override
    public OAuthClientDto editOAuthClient(int id, OAuthClientDto dto) {
        final Optional<OAuthClient> oAuthClientToEdit = oAuthClientRepository.findById(id);

        if (oAuthClientToEdit.isPresent()) {
            OAuthClient oAuthClient = oAuthClientToEdit.get();

            // check client id for uniqueness if it was edited
            if (!isEmpty(dto.getClientId()) && !dto.getClientId().equals(oAuthClient.getClientId())) {
                checkOAuthClientIdForUniqueness(dto);
            }

            oAuthClient = map(dto, OAuthClient.class);
            oAuthClient.setClientSecret(passwordEncoder.encode(dto.getClientSecret()));
            oAuthClientRepository.save(oAuthClient);

            return map(oAuthClient, OAuthClientDto.class);

        } else throw new IllegalArgumentException("OAuth client with such id doesn't exists.");
    }

    @Override
    public void deleteOAuthClient(int id) {
        if (oAuthClientRepository.existsById(id)) {

            oAuthClientRepository.deleteById(id);

        } else throw new IllegalArgumentException("OAuth client  with such id doesn't exists");
    }

    @Override
    public OAuthClientDto getOAuthClient(int id) {
        final Optional<OAuthClient> oAuthClient = oAuthClientRepository.findById(id);
        if (oAuthClient.isPresent()) {
            return map(oAuthClient.get(), OAuthClientDto.class);
        } else throw new IllegalArgumentException("OAuth client  with such id doesn't exists");
    }

    @Override
    public OAuthClientDto getOAuthClientByClientId(String clientId) {
        final Optional<OAuthClient> oAuthClient = oAuthClientRepository.findByClientId(clientId);
        if (oAuthClient.isPresent()) {
            return map(oAuthClient.get(), OAuthClientDto.class);
        } else throw new IllegalArgumentException("OAuth client with such client id doesn't exists");
    }

    @Override
    public List<OAuthClientDto> getAllOAuthClients() {
        return mapAll(oAuthClientRepository.findAll(), OAuthClientDto.class);
    }

    private void checkOAuthClientIdForUniqueness(OAuthClientDto dto) {
        if (!isEmpty(dto.getClientId())) {
            final Optional<OAuthClient> oAuthClient = oAuthClientRepository.findByClientId(dto.getClientId());
            if (oAuthClient.isPresent()) {
                throw new IllegalArgumentException("OAuth client with such client id already exists.");
            }
        }
    }
}
