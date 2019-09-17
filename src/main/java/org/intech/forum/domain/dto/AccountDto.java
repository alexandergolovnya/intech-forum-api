package org.intech.forum.domain.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * @author: Alexander Golovnya <mail@alexandergolovnya.ru>
 * @created: 2019/09/16
 */
@Data
public class AccountDto {

    private int id;

    @Email(message = "The format of the e-mail address is incorrect")
    @NotBlank(message = "Account email may not be blank")
    @Size(min = 5, max = 254, message = "Account email length should be between 5 and 254 symbols")
    private String email;

    @Size(min = 8, max = 15, message = "Account phone length should be between 8 and 15 symbols")
    private String phone;

    @NotBlank(message = "Account password may not be blank")
    @Size(min = 8, max = 60, message = "Account password length should be between 8 and 60 symbols")
    private String password;

    @NotBlank(message = "Account first name may not be blank")
    @Size(max = 50, message = "Account first name length should be less than 50 symbols")
    private String firstName;

    @NotBlank(message = "Account last name may not be blank")
    @Size(max = 50, message = "Account last name length should be less than 50 symbols")
    private String lastName;

    @Size(max = 50, message = "Account middle name length should be less than 50 symbols")
    private String middleName;

    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private Set<AccountAuthorityDto> accountAuthorityDtos;
}
