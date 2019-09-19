package org.intech.forum.service;

import org.intech.forum.domain.dto.AccountDto;
import org.intech.forum.exception.ForbiddenException;

import java.security.Principal;
import java.util.List;

/**
 * @author: Alexander Golovnya <mail@alexandergolovnya.ru>
 * @created: 2019/09/16
 */
public interface AccountService {

    AccountDto createAccount(AccountDto accountDto);

    AccountDto editAccount(int id, AccountDto accountDto, Principal principal) throws ForbiddenException;

    void deleteAccount(int id, Principal principal);

    AccountDto getAccount(int id);

    AccountDto getAccountByPhone(String phone);

    AccountDto getAccountByEmail(String email);

    List<AccountDto> getAllAccounts();
}
