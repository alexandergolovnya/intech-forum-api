package org.intech.forum.domain.dto;

import lombok.Data;
import org.intech.forum.validation.marker.OnCreate;
import org.intech.forum.validation.marker.OnUpdate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * @author: Alexander Golovnya <mail@alexandergolovnya.ru>
 * @created: 2019/09/16
 */
@Data
public class AccountAuthorityDto {

    @Null(groups = OnCreate.class, message = "On account authority creating id should be null")
    @NotNull(groups = OnUpdate.class, message = "On account authority editing id couldn't be null")
    private Integer id;

    @NotBlank(message = "Authority name may not be blank")
    private String authorityName;
}
