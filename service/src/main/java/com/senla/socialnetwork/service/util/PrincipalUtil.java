package com.senla.socialnetwork.service.util;

import com.senla.socialnetwork.service.security.UserPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;

public class PrincipalUtil {

    public static String getUserName() {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext()
            .getAuthentication().getPrincipal();
        return userPrincipal.getUsername();
    }

}
