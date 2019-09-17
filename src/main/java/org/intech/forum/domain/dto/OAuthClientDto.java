package org.intech.forum.domain.dto;

import lombok.Data;
import org.intech.forum.validation.OnCreate;
import org.intech.forum.validation.OnUpdate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * @author: Alexander Golovnya <mail@alexandergolovnya.ru>
 * @created: 2019/09/16
 */
@Data
public class OAuthClientDto {

    @Null(groups = OnCreate.class, message = "On oauth client creating id should be null")
    @NotNull(groups = OnUpdate.class, message = "On oauth client editing id couldn't be null")
    private Integer id;

    @NotBlank(message = "OAuth client id may not be blank")
    private String clientId;

    @NotBlank(message = "OAuth client secret may not be blank")
    private String clientSecret;

    @NotBlank(message = "OAuth client authorized grant types may not be blank")
    private String authorizedGrantTypes;

    @NotBlank(message = "OAuth client scopes may not be blank")
    private String scopes;

    @NotNull(message = "OAuth access token validity may not be null")
    private Integer accessTokenValidity;

    @NotNull(message = "OAuth refresh token validity may not be null")
    private Integer refreshTokenValidity;

    @NotNull(groups = OnUpdate.class, message = "On oauth client editing authorities couldn't be null")
    private String authorities;

    @NotNull(groups = OnUpdate.class, message = "On oauth client editing authorities couldn't be null")
    private String resourceIds;

    @NotNull(groups = OnUpdate.class, message = "On oauth client editing redirect uris couldn't be null")
    private String redirectUris;

    @NotNull(groups = OnUpdate.class, message = "On oauth client editing field 'auto approve' couldn't be null")
    private Boolean autoApprove;

    @NotNull(groups = OnUpdate.class, message = "On oauth client editing field 'secret required' couldn't be null")
    private Boolean secretRequired;

    @NotNull(groups = OnUpdate.class, message = "On oauth client editing field 'scoped' couldn't be null")
    private Boolean scoped;
}
