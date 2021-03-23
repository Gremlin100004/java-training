package com.senla.socialnetwork.service.util;

import com.senla.socialnetwork.service.security.UserPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;

public final class PrincipalUtil {
    private PrincipalUtil() {
    }

    public static String getUserName() {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext()
            .getAuthentication().getPrincipal();
        return userPrincipal.getUsername();
    }

}
