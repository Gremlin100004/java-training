package com.senla.socialnetwork.dao.testdata;

import com.senla.socialnetwork.dao.enumaration.ArrayIndex;
import com.senla.socialnetwork.model.SystemUser;
import com.senla.socialnetwork.model.enumaration.RoleName;

import java.util.ArrayList;
import java.util.List;

public class UserTestData {
    private static final String[] EMAIL_ARRAY = {
            "user1@test.com",
            "user2@test.com",
            "user3@test.com",
            "user4@test.com",
            "admin@test.com"
    };
    private static final String PASSWORD = "$2a$10$ehU.2fP2fFtcFB9Fti8u2unzBrCIzQRvUei8r/ppUzxlBxSP86eH2";

    public static List<SystemUser> getSystemUsers() {
        List<SystemUser> systemUsers = new ArrayList<>();
        systemUsers.add(getUser(EMAIL_ARRAY[ArrayIndex.FIRST_INDEX_OF_ARRAY.index], PASSWORD, RoleName.ROLE_USER));
        systemUsers.add(getUser(EMAIL_ARRAY[ArrayIndex.SECOND_INDEX_OF_ARRAY.index], PASSWORD, RoleName.ROLE_USER));
        systemUsers.add(getUser(EMAIL_ARRAY[ArrayIndex.THIRD_INDEX_OF_ARRAY.index], PASSWORD, RoleName.ROLE_USER));
        systemUsers.add(getUser(EMAIL_ARRAY[ArrayIndex.FOURTH_INDEX_OF_ARRAY.index], PASSWORD, RoleName.ROLE_USER));
        systemUsers.add(getUser(EMAIL_ARRAY[ArrayIndex.FIFTH_INDEX_OF_ARRAY.index], PASSWORD, RoleName.ROLE_ADMIN));
        return systemUsers;
    }

    private static SystemUser getUser(String email, String password, RoleName roleName) {
        SystemUser systemUser = new SystemUser();
        systemUser.setEmail(email);
        systemUser.setPassword(password);
        systemUser.setRole(roleName);
        return systemUser;
    }

}
