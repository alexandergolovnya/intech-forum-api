package org.intech.forum.config;

import lombok.RequiredArgsConstructor;
import org.intech.forum.domain.entity.Account;
import org.intech.forum.domain.entity.AccountAuthority;
import org.intech.forum.domain.repository.AccountAuthorityRepository;
import org.intech.forum.domain.repository.AccountRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class creates initial data for account and account authorities
 * if there are no default users and roles at the database
 *
 * @author: Alexander Golovnya <mail@alexandergolovnya.ru>
 * @created: 2019/09/19
 */
@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {

    private final AccountRepository accountRepository;
    private final AccountAuthorityRepository accountAuthorityRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {

        if (!accountAuthorityRepository.findByAuthorityName("ROLE_USER").isPresent()) {
            final AccountAuthority user = new AccountAuthority();
            user.setAuthorityName("ROLE_USER");
            accountAuthorityRepository.save(user);
        }

        if (!accountAuthorityRepository.findByAuthorityName("ROLE_ADMIN").isPresent()) {
            final AccountAuthority admin = new AccountAuthority();
            admin.setAuthorityName("ROLE_ADMIN");
            accountAuthorityRepository.save(admin);
        }


        if (!accountRepository.findByEmail("admin@admin").isPresent()) {
            final AccountAuthority roleAdmin = accountAuthorityRepository.findByAuthorityName("ROLE_ADMIN")
                    .orElseThrow(() -> new IllegalArgumentException("Admin authority doesn't exists"));

            final Account admin = new Account();
            admin.setEmail("admin@admin");
            admin.setPhone("+79187776655");
            admin.setPassword(passwordEncoder.encode("Qwerty123$"));
            admin.setFirstName("Александр");
            admin.setMiddleName("Константинович");
            admin.setLastName("Головня");
            admin.setAccountAuthorities(Stream.of(roleAdmin).collect(Collectors.toSet()));
            admin.setAccountNonExpired(true);
            admin.setAccountNonLocked(true);
            admin.setCredentialsNonExpired(true);
            admin.setEnabled(true);

            accountRepository.save(admin);
        }

        if (!accountRepository.findByEmail("user@user").isPresent()) {
            final AccountAuthority roleUser = accountAuthorityRepository.findByAuthorityName("ROLE_USER")
                    .orElseThrow(() -> new IllegalArgumentException("User authority doesn't exists"));

            final Account user = new Account();
            user.setEmail("user@user");
            user.setPhone("+79187776644");
            user.setPassword(passwordEncoder.encode("Qwerty123$"));
            user.setFirstName("Иван");
            user.setMiddleName("Иванович");
            user.setLastName("Иванов");
            user.setAccountAuthorities(Stream.of(roleUser).collect(Collectors.toSet()));
            user.setAccountNonExpired(true);
            user.setAccountNonLocked(true);
            user.setCredentialsNonExpired(true);
            user.setEnabled(true);

            accountRepository.save(user);
        }
    }
}
