package org.intech.forum.utils;

import lombok.experimental.UtilityClass;
import org.intech.forum.exception.ForbiddenException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: Alexander Golovnya <mail@alexandergolovnya.ru>
 * @created: 2019/09/19
 */
@UtilityClass
public class SecurityUtils {

    /**
     * Method checks user access right to a verifiable operation.
     * User can perform verifiable operation only if he is creator of verifiable object
     * or he has admin authority.
     * In all other cases will be thrown ForbiddenException
     *
     * @param principal          current user credentials
     * @param objectCreatorEmail email of the user that have created this verifiable object
     */
    public static void checkUserAccessRightsToThisMethod(Principal principal, String objectCreatorEmail) {
        Authentication userAuthentication = ((OAuth2Authentication) principal).getUserAuthentication();
        String currentUserEmail = userAuthentication.getPrincipal().toString();
        List<String> userAuthoritiesList = userAuthentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        if (!objectCreatorEmail.equals(currentUserEmail) && !userAuthoritiesList.contains("ROLE_ADMIN")) {
            throw new ForbiddenException("Current user doesn't have access rights to this operation");
        }
    }
}
