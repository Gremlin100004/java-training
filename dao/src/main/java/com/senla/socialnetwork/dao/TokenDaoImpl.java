package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.domain.LogoutToken;
import com.senla.socialnetwork.domain.LogoutToken_;
import com.senla.socialnetwork.domain.SystemUser;
import com.senla.socialnetwork.domain.SystemUser_;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

@Repository
@Slf4j
public class TokenDaoImpl extends AbstractDao<LogoutToken, Long> implements TokenDao {
    public TokenDaoImpl() {
        setType(LogoutToken.class);
    }

    @Override
    public String getLogoutToken(final String email) {
        log.debug("[getLogoutToken]");
        log.trace("[email: {}]", email);
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<String> criteriaQuery = criteriaBuilder.createQuery(String.class);
            Root<LogoutToken> logoutTokenRoot = criteriaQuery.from(LogoutToken.class);
            Join<LogoutToken, SystemUser> logoutTokenSystemUserJoin = logoutTokenRoot.join(LogoutToken_.systemUser);
            criteriaQuery.select(logoutTokenRoot.get(LogoutToken_.VALUE));
            criteriaQuery.where(criteriaBuilder.equal(logoutTokenSystemUserJoin.get(SystemUser_.email), email));
            return entityManager.createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException exception) {
            log.error("[{}]", exception.getMessage());
            return null;
        }
    }

}
