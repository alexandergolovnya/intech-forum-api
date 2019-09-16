package org.intech.forum.domain;

import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.apache.commons.lang.StringUtils.isEmpty;
import static org.springframework.util.StringUtils.commaDelimitedListToSet;

/**
 * @author: Alexander Golovnya <mail@alexandergolovnya.ru>
 * @created: 2019/09/16
 */
@Entity
@Setter
@Table(name = "oauth_clients")
public class OAuthClient implements ClientDetails {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(nullable = false, unique = true, columnDefinition = "int")
    private int id;

    @Column(nullable = false, unique = true)
    private String clientId;

    @Column(nullable = false)
    private String clientSecret;

    @Column(nullable = false)
    private String authorizedGrantTypes;

    @Column(nullable = false)
    private String scopes;

    @Column(nullable = false, columnDefinition = "int")
    private int accessTokenValidity;

    @Column(nullable = false, columnDefinition = "int")
    private int refreshTokenValidity;

    private String authorities;
    private String resourceIds;
    private String redirectUris;
    private boolean autoApprove;
    private boolean secretRequired;
    private boolean scoped;

    @Override
    public String getClientId() {
        return clientId;
    }

    @Override
    public Set<String> getResourceIds() {
        return isEmpty(resourceIds) ? commaDelimitedListToSet(resourceIds) : new HashSet<>();
    }

    @Override
    public boolean isSecretRequired() {
        return secretRequired;
    }

    @Override
    public String getClientSecret() {
        return clientSecret;
    }

    @Override
    public boolean isScoped() {
        return scoped;
    }

    @Override
    public Set<String> getScope() {
        return isEmpty(scopes) ? commaDelimitedListToSet(scopes) : new HashSet<>();
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return isEmpty(authorizedGrantTypes) ? commaDelimitedListToSet(authorizedGrantTypes) : new HashSet<>();
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return isEmpty(redirectUris) ? commaDelimitedListToSet(redirectUris) : new HashSet<>();
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        Set<String> authoritiesSet = commaDelimitedListToSet(authorities);
        return authoritiesSet.stream()
                .map(authority -> (GrantedAuthority) () -> authority)
                .collect(Collectors.toSet());
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return accessTokenValidity;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return refreshTokenValidity;
    }

    @Override
    public boolean isAutoApprove(String s) {
        return autoApprove;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return null;
    }
}
