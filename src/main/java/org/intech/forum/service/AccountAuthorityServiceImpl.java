package org.intech.forum.service;

import lombok.RequiredArgsConstructor;
import org.intech.forum.domain.dto.AccountAuthorityDto;
import org.intech.forum.domain.entity.AccountAuthority;
import org.intech.forum.domain.repository.AccountAuthorityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.intech.forum.utils.ModelMapperUtils.map;
import static org.intech.forum.utils.ModelMapperUtils.mapAll;

/**
 * @author: Alexander Golovnya <mail@alexandergolovnya.ru>
 * @created: 2019/09/16
 */
@Service
@RequiredArgsConstructor
public class AccountAuthorityServiceImpl implements AccountAuthorityService {

    private final AccountAuthorityRepository accountAuthorityRepository;

    @Override
    public AccountAuthorityDto createAccountAuthority(AccountAuthorityDto accountAuthorityDto) {
        checkAccountAuthorityNameForUniqueness(accountAuthorityDto);

        final AccountAuthority accountAuthority = map(accountAuthorityDto, AccountAuthority.class);
        accountAuthorityRepository.save(accountAuthority);

        return map(accountAuthority, AccountAuthorityDto.class);
    }

    @Override
    public AccountAuthorityDto editAccountAuthority(int id, AccountAuthorityDto dto) {
        checkAccountAuthorityNameForUniqueness(dto);

        final Optional<AccountAuthority> authorityToEdit = accountAuthorityRepository.findById(id);
        if (authorityToEdit.isPresent()) {

            AccountAuthority accountAuthority = map(dto, AccountAuthority.class);
            accountAuthorityRepository.save(accountAuthority);
            return map(accountAuthority, AccountAuthorityDto.class);

        } else throw new IllegalArgumentException("Account authority with such id doesn't exists");
    }

    @Override
    public void deleteAccountAuthority(int id) {
        if (accountAuthorityRepository.existsById(id)) {

            accountAuthorityRepository.deleteById(id);

        } else throw new IllegalArgumentException("Account authority with such id doesn't exists");
    }

    @Override
    public AccountAuthorityDto getAccountAuthority(int id) {
        final Optional<AccountAuthority> accountAuthority = accountAuthorityRepository.findById(id);
        if (accountAuthority.isPresent()) {
            return map(accountAuthority, AccountAuthorityDto.class);
        } else throw new IllegalArgumentException("Account authority with such id doesn't exists");
    }

    @Override
    public List<AccountAuthorityDto> getAllAccountAuthorities() {
        return mapAll(accountAuthorityRepository.findAll(), AccountAuthorityDto.class);
    }

    private void checkAccountAuthorityNameForUniqueness(AccountAuthorityDto accountAuthorityDto) {
        final Optional<AccountAuthority> accountAuthority =
                accountAuthorityRepository.findByAuthorityName(accountAuthorityDto.getAuthorityName());

        if (accountAuthority.isPresent()) {
            throw new IllegalArgumentException("Account authority with such name already exists.");
        }
    }
}