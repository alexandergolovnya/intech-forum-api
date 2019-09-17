package org.intech.forum.domain.dto;

import lombok.Data;
import org.intech.forum.validation.OnCreate;
import org.intech.forum.validation.OnUpdate;
import org.intech.forum.validation.Password;

import javax.validation.constraints.*;
import java.util.Set;

/**
 * @author: Alexander Golovnya <mail@alexandergolovnya.ru>
 * @created: 2019/09/16
 */
@Data
public class AccountDto {

    @Null(groups = OnCreate.class, message = "On account creating account id should be null")
    @NotNull(groups = OnUpdate.class, message = "On account editing account id couldn't be null")
    private Integer id;

    @Email(message = "The format of the e-mail address is incorrect")
    @NotBlank(message = "Account email may not be blank")
    @Size(min = 5, max = 254, message = "Account email length should be between 5 and 254 symbols")
    private String email;

    @Size(min = 8, max = 15, message = "Account phone length should be between 8 and 15 symbols")
    private String phone;

    @Password
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

    @NotNull(groups = OnUpdate.class, message = "On account editing field 'account non expired' couldn't be null")
    private Boolean accountNonExpired;

    @NotNull(groups = OnUpdate.class, message = "On account editing field 'account non locked' couldn't be null")
    private Boolean accountNonLocked;

    @NotNull(groups = OnUpdate.class, message = "On account editing field 'credentials non expired' couldn't be null")
    private Boolean credentialsNonExpired;

    @NotNull(groups = OnUpdate.class, message = "On account editing field 'enabled' couldn't be null")
    private Boolean enabled;

    @Null(message = "Account authorities couldn't be changed or assigned directly, it should be null.")
    private Set<AccountAuthorityDto> accountAuthorities;
}
