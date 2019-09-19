package org.intech.forum.service.account;

import org.intech.forum.domain.dto.AccountAuthorityDto;

import java.util.List;

/**
 * @author: Alexander Golovnya <mail@alexandergolovnya.ru>
 * @created: 2019/09/16
 */
public interface AccountAuthorityService {

    AccountAuthorityDto createAccountAuthority(AccountAuthorityDto accountAuthorityDto);

    AccountAuthorityDto editAccountAuthority(int id, AccountAuthorityDto accountAuthorityDto);

    void deleteAccountAuthority(int id);

    AccountAuthorityDto getAccountAuthority(int id);

    List<AccountAuthorityDto> getAllAccountAuthorities();
}
