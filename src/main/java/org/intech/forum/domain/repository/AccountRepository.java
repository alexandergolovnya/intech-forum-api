package org.intech.forum.domain.repository;

import org.intech.forum.domain.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author: Alexander Golovnya <mail@alexandergolovnya.ru>
 * @created: 2019/09/16
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    Optional<Account> findByEmail(String email);

    Optional<Account> findByPhone(String phone);
}
