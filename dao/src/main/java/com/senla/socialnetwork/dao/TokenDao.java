package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.domain.LogoutToken;

public interface TokenDao extends GenericDao<LogoutToken, Long> {
    String getLogoutToken(String email);

}
