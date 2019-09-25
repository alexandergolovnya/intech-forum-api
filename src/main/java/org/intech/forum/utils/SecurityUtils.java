package org.intech.forum.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
    public static void checkUserAccessRightsToThisMethod(Principal principal, String objectCreatorEmail) throws ForbiddenException {
        final String currentUserEmail = getCurrentUserEmailFromPrincipal(principal);
        final List<String> userAuthoritiesList = getUserAuthoritiesListFromPrincipal(principal);

        if (!objectCreatorEmail.equals(currentUserEmail) && !userAuthoritiesList.contains("ROLE_ADMIN")) {
            log.debug(String.format("objectCreatorEmail = %s", objectCreatorEmail));
            log.debug(String.format("currentUserEmail = %s", currentUserEmail));
            log.debug(String.format("userAuthoritiesList = %s", userAuthoritiesList));

            throw new ForbiddenException("Current user doesn't have access rights to this operation");
        }
    }

    /**
     * Method returns email of the current user from user principal.
     *
     * @param principal current user credentials
     */
    public static String getCurrentUserEmailFromPrincipal(Principal principal) {
        final Authentication userAuthentication = ((OAuth2Authentication) principal).getUserAuthentication();
        return userAuthentication.getPrincipal().toString();
    }

    /**
     * Method returns list of user authorities of the current user from user principal.
     *
     * @param principal current user credentials
     */
    private static List<String> getUserAuthoritiesListFromPrincipal(Principal principal) {
        final Authentication userAuthentication = ((OAuth2Authentication) principal).getUserAuthentication();
        return userAuthentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }
}
