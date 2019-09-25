package org.intech.forum.service.account;

import lombok.RequiredArgsConstructor;
import org.intech.forum.domain.dto.AccountDto;
import org.intech.forum.domain.entity.Account;
import org.intech.forum.domain.entity.AccountAuthority;
import org.intech.forum.domain.repository.AccountAuthorityRepository;
import org.intech.forum.domain.repository.AccountRepository;
import org.intech.forum.exception.ForbiddenException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.apache.commons.lang.StringUtils.isEmpty;
import static org.intech.forum.utils.ModelMapperUtils.map;
import static org.intech.forum.utils.ModelMapperUtils.mapAll;
import static org.intech.forum.utils.SecurityUtils.checkUserAccessRightsToThisMethod;

/**
 * @author: Alexander Golovnya <mail@alexandergolovnya.ru>
 * @created: 2019/09/16
 */
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountAuthorityRepository accountAuthorityRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        checkAccountEmailForUniqueness(accountDto);
        checkAccountPhoneForUniqueness(accountDto);

        final Account account = map(accountDto, Account.class);
        account.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        account.setAccountAuthorities(getAccountAuthorityUser());
        account.setCredentialsNonExpired(true);
        account.setAccountNonLocked(true);
        account.setAccountNonExpired(true);
        account.setEnabled(true);
        accountRepository.save(account);

        return map(account, AccountDto.class);
    }

    @Override
    public AccountDto editAccount(int id, AccountDto dto, Principal principal) throws ForbiddenException {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Account with such id doesn't exists"));

        final Set<AccountAuthority> accountAuthorities = account.getAccountAuthorities();

        // check if current user has access rights to this method
        checkUserAccessRightsToThisMethod(principal, account.getEmail());

        // check email and phone for uniqueness if it was edited
        if (!dto.getEmail().equals(account.getEmail())) {
            checkAccountEmailForUniqueness(dto);
        }
        if (!isEmpty(dto.getPhone()) && !dto.getPhone().equals(account.getPhone())) {
            checkAccountPhoneForUniqueness(dto);
        }

        account = map(dto, Account.class);
        account.setPassword(passwordEncoder.encode(dto.getPassword()));
        account.setAccountAuthorities(accountAuthorities);
        accountRepository.save(account);

        return map(account, AccountDto.class);
    }

    @Override
    public void deleteAccount(int id, Principal principal) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Account with such id doesn't exists"));

        // check if current user has access rights to this method
        checkUserAccessRightsToThisMethod(principal, account.getEmail());

        accountRepository.deleteById(id);
    }

    @Override
    public AccountDto getAccount(int id) {
        final Optional<Account> account = accountRepository.findById(id);
        if (account.isPresent()) {
            return map(account.get(), AccountDto.class);
        } else throw new IllegalArgumentException("Account with such id doesn't exists");
    }

    @Override
    public AccountDto getAccountByPhone(String phone) {
        final Optional<Account> account = accountRepository.findByPhone(phone);
        if (account.isPresent()) {
            return map(account.get(), AccountDto.class);
        } else throw new IllegalArgumentException("Account with such phone doesn't exists");
    }

    @Override
    public AccountDto getAccountByEmail(String email) {
        final Optional<Account> account = accountRepository.findByEmail(email);
        if (account.isPresent()) {
            return map(account.get(), AccountDto.class);
        } else throw new IllegalArgumentException("Account with such email doesn't exists");
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        return mapAll(accountRepository.findAll(), AccountDto.class);
    }

    private Set<AccountAuthority> getAccountAuthorityUser() {
        final Optional<AccountAuthority> accountAuthority = accountAuthorityRepository.findByAuthorityName("ROLE_USER");

        if (accountAuthority.isPresent()) {
            final Set<AccountAuthority> accountAuthorities = new HashSet<>();
            accountAuthorities.add(accountAuthority.get());
            return accountAuthorities;

        } else {
            final AccountAuthority accountAuthorityUser = new AccountAuthority();
            accountAuthorityUser.setAuthorityName("ROLE_USER");
            accountAuthorityRepository.saveAndFlush(accountAuthorityUser);

            final Set<AccountAuthority> accountAuthorities = new HashSet<>();
            accountAuthorities.add(accountAuthorityUser);
            return accountAuthorities;
        }
    }

    private void checkAccountEmailForUniqueness(AccountDto accountDto) {
        if (!isEmpty(accountDto.getEmail())) {
            final Optional<Account> account = accountRepository.findByEmail(accountDto.getEmail());
            if (account.isPresent()) {
                throw new IllegalArgumentException("Account with such email already exists.");
            }
        }
    }

    private void checkAccountPhoneForUniqueness(AccountDto accountDto) {
        if (!isEmpty(accountDto.getPhone())) {
            final Optional<Account> account = accountRepository.findByPhone(accountDto.getPhone());
            if (account.isPresent()) {
                throw new IllegalArgumentException("Account with such phone already exists.");
            }
        }
    }
}
