package org.intech.forum.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author: Alexander Golovnya <mail@alexandergolovnya.ru>
 * @created: 2019/09/16
 */
@Data
public class OAuthClientDto {

    private int id;

    @NotBlank(message = "OAuth client id may not be blank")
    private String clientId;

    @NotBlank(message = "OAuth client secret may not be blank")
    private String clientSecret;

    @NotBlank(message = "OAuth client authorized grant types may not be blank")
    private String authorizedGrantTypes;

    @NotBlank(message = "OAuth client scopes may not be blank")
    private String scopes;

    private int accessTokenValidity;
    private int refreshTokenValidity;
    private String authorities;
    private String resourceIds;
    private String redirectUris;
    private boolean autoApprove;
    private boolean secretRequired;
    private boolean scoped;
}
