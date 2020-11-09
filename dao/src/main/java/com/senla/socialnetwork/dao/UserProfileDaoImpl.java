package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.domain.Location;
import com.senla.socialnetwork.domain.School;
import com.senla.socialnetwork.domain.SystemUser;
import com.senla.socialnetwork.domain.SystemUser_;
import com.senla.socialnetwork.domain.University;
import com.senla.socialnetwork.domain.UserProfile;
import com.senla.socialnetwork.domain.UserProfile_;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Date;
import java.util.List;

@Repository
@Slf4j
public class UserProfileDaoImpl extends AbstractDao<UserProfile, Long> implements UserProfileDao {
    private static final String PARAMETER_MONTH = "month";
    private static final String PARAMETER_DAY = "day";
    private static final int FIRST_RESULT = 0;
    private static final int MAX_RESULTS = 1;
    public UserProfileDaoImpl() {
        setType(UserProfile.class);
    }

    @Override
    public UserProfile findByEmail(String email) {
        log.debug("[findByEmail]");
        log.trace("[email: {}]", email);
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<UserProfile> criteriaQuery = criteriaBuilder.createQuery(UserProfile.class);
            Root<UserProfile> userProfileRoot = criteriaQuery.from(UserProfile.class);
            Join<UserProfile, SystemUser> userProfileSystemUserJoin = userProfileRoot.join(UserProfile_.systemUser);
            criteriaQuery.select(userProfileRoot);
            criteriaQuery.where(criteriaBuilder.equal(userProfileSystemUserJoin.get(SystemUser_.email), email));
            return entityManager.createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException exception) {
            log.error("[{}]", exception.getMessage());
            return null;
        }
    }

    @Override
    public Location getLocation(String email) {
        log.debug("[getLocation]");
        log.trace("[email: {}]", email);
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Location> criteriaQuery = criteriaBuilder.createQuery(Location.class);
            Root<UserProfile> userProfileRoot = criteriaQuery.from(UserProfile.class);
            Join<UserProfile, SystemUser> masterOrderJoin = userProfileRoot.join(UserProfile_.systemUser);
            criteriaQuery.select(userProfileRoot.get(UserProfile_.location));
            criteriaQuery.where(criteriaBuilder.equal(masterOrderJoin.get(SystemUser_.email), email));
            return entityManager.createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException exception) {
            log.error("[{}]", exception.getMessage());
            return null;
        }
    }

    @Override
    public List<UserProfile> getUserProfilesSortByName(int firstResult, int maxResults) {
        log.debug("[getUserProfilesSortByName]");
        log.trace("[firstResult: {}, maxResults: {}]", firstResult, maxResults);
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<UserProfile> criteriaQuery = criteriaBuilder.createQuery(UserProfile.class);
            Root<UserProfile> userProfileRoot = criteriaQuery.from(UserProfile.class);
            criteriaQuery.select(userProfileRoot);
            criteriaQuery.orderBy(criteriaBuilder.asc(userProfileRoot.get(UserProfile_.name)));
            TypedQuery<UserProfile> typedQuery = entityManager.createQuery(criteriaQuery);
            typedQuery.setFirstResult(firstResult);
            typedQuery.setMaxResults(maxResults);
            return typedQuery.getResultList();
        } catch (NoResultException exception) {
            log.error("[{}]", exception.getMessage());
            return null;
        }
    }

    @Override
    public List<UserProfile> getUserProfilesSortByRegistrationDate(int firstResult, int maxResults) {
        log.debug("[getUserProfilesSortByRegistrationDate]");
        log.trace("[firstResult: {}, maxResults: {}]", firstResult, maxResults);
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<UserProfile> criteriaQuery = criteriaBuilder.createQuery(UserProfile.class);
            Root<UserProfile> userProfileRoot = criteriaQuery.from(UserProfile.class);
            criteriaQuery.select(userProfileRoot);
            criteriaQuery.orderBy(criteriaBuilder.desc(userProfileRoot.get(UserProfile_.registrationDate)));
            TypedQuery<UserProfile> typedQuery = entityManager.createQuery(criteriaQuery);
            typedQuery.setFirstResult(firstResult);
            typedQuery.setMaxResults(maxResults);
            return typedQuery.getResultList();
        } catch (NoResultException exception) {
            log.error("[{}]", exception.getMessage());
            return null;
        }
    }

    @Override
    public List<UserProfile> getUserProfilesSortByNumberOfFriends(int firstResult, int maxResults) {
        log.debug("[getUserProfilesSortByNumberOfFriends]");
        log.trace("[firstResult: {}, maxResults: {}]", firstResult, maxResults);
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<UserProfile> criteriaQuery = criteriaBuilder.createQuery(UserProfile.class);
            Root<UserProfile> userProfileRoot = criteriaQuery.from(UserProfile.class);
            Join<UserProfile, UserProfile> userProfileUserProfileListJoin = userProfileRoot.join(UserProfile_.friends);
            criteriaQuery.select(userProfileRoot);
            criteriaQuery.orderBy(
                criteriaBuilder.desc(criteriaBuilder.count(userProfileUserProfileListJoin.get(UserProfile_.friends))));
            TypedQuery<UserProfile> typedQuery = entityManager.createQuery(criteriaQuery);
            typedQuery.setFirstResult(firstResult);
            typedQuery.setMaxResults(maxResults);
            return typedQuery.getResultList();
        } catch (NoResultException exception) {
            log.error("[{}]", exception.getMessage());
            return null;
        }
    }

    @Override
    public List<UserProfile> getUserProfilesFilteredByLocation(Location location, int firstResult, int maxResults) {
        log.debug("[getUserProfilesFilteredByLocation]");
        log.trace("[location: {}, firstResult: {}, maxResults: {}]", location, firstResult, maxResults);
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<UserProfile> criteriaQuery = criteriaBuilder.createQuery(UserProfile.class);
            Root<UserProfile> userProfileRoot = criteriaQuery.from(UserProfile.class);
            criteriaQuery.select(userProfileRoot);
            criteriaQuery.where(criteriaBuilder.equal(userProfileRoot.get(UserProfile_.location), location));
            TypedQuery<UserProfile> typedQuery = entityManager.createQuery(criteriaQuery);
            typedQuery.setFirstResult(firstResult);
            typedQuery.setMaxResults(maxResults);
            return typedQuery.getResultList();
        } catch (NoResultException exception) {
            log.error("[{}]", exception.getMessage());
            return null;
        }
    }

    @Override
    public List<UserProfile> getUserProfilesFilteredBySchool(School school, int firstResult, int maxResults) {
        log.debug("[getUserProfilesFilteredBySchool]");
        log.trace("[school: {}, firstResult: {}, maxResults: {}]", school, firstResult, maxResults);
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<UserProfile> criteriaQuery = criteriaBuilder.createQuery(UserProfile.class);
            Root<UserProfile> userProfileRoot = criteriaQuery.from(UserProfile.class);
            criteriaQuery.select(userProfileRoot);
            criteriaQuery.where(criteriaBuilder.equal(userProfileRoot.get(UserProfile_.school), school));
            TypedQuery<UserProfile> typedQuery = entityManager.createQuery(criteriaQuery);
            typedQuery.setFirstResult(firstResult);
            typedQuery.setMaxResults(maxResults);
            return typedQuery.getResultList();
        } catch (NoResultException exception) {
            log.error("[{}]", exception.getMessage());
            return null;
        }
    }

    @Override
    public List<UserProfile> getUserProfilesFilteredByUniversity(University university,
                                                                 int firstResult,
                                                                 int maxResults) {
        log.debug("[getUserProfilesFilteredByUniversity]");
        log.trace("[university: {}, firstResult: {}, maxResults: {}]", university, firstResult, maxResults);
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<UserProfile> criteriaQuery = criteriaBuilder.createQuery(UserProfile.class);
            Root<UserProfile> userProfileRoot = criteriaQuery.from(UserProfile.class);
            criteriaQuery.select(userProfileRoot);
            criteriaQuery.where(criteriaBuilder.equal(userProfileRoot.get(UserProfile_.university), university));
            TypedQuery<UserProfile> typedQuery = entityManager.createQuery(criteriaQuery);
            typedQuery.setFirstResult(firstResult);
            typedQuery.setMaxResults(maxResults);
            return typedQuery.getResultList();
        } catch (NoResultException exception) {
            log.error("[{}]", exception.getMessage());
            return null;
        }
    }

    @Override
    public List<UserProfile> getUserProfilesFilteredByAge(Date startPeriodDate,
                                                          Date endPeriodDate,
                                                          int firstResult,
                                                          int maxResults) {
        log.debug("[getDeletedOrdersSortByExecutionDate]");
        log.trace("[startPeriodDate: {}, endPeriodDate: {}, firstResult: {}, maxResults: {}]",
             startPeriodDate, endPeriodDate, firstResult, maxResults);
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<UserProfile> criteriaQuery = criteriaBuilder.createQuery(UserProfile.class);
            Root<UserProfile> orderRoot = criteriaQuery.from(UserProfile.class);
            criteriaQuery.select(orderRoot);
            criteriaQuery.where(
                criteriaBuilder.between(orderRoot.get(UserProfile_.dateOfBirth), startPeriodDate, endPeriodDate));
            criteriaQuery.orderBy(criteriaBuilder.asc(orderRoot.get(UserProfile_.dateOfBirth)));
            TypedQuery<UserProfile> typedQuery = entityManager.createQuery(criteriaQuery);
            typedQuery.setFirstResult(firstResult);
            typedQuery.setMaxResults(maxResults);
            return typedQuery.getResultList();
        } catch (NoResultException exception) {
            log.error("[{}]", exception.getMessage());
            return null;
        }
    }

    @Override
    public UserProfile getNearestBirthdayByCurrentDate() {
        log.debug("[getNearestBirthdayByCurrentDate]");
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            Expression<Date> currentDate = criteriaBuilder.currentDate();
            Expression<Integer> month = criteriaBuilder.function(PARAMETER_MONTH, Integer.class, currentDate);
            Expression<Integer> day = criteriaBuilder.function(PARAMETER_DAY, Integer.class, currentDate);
            CriteriaQuery<UserProfile> criteriaQuery = criteriaBuilder.createQuery(UserProfile.class);
            Root<UserProfile> userProfileRoot = criteriaQuery.from(UserProfile.class);
            criteriaQuery.select(userProfileRoot);
            Predicate predicateEqualMonth = criteriaBuilder.equal(
                criteriaBuilder.function(PARAMETER_MONTH, Integer.class, userProfileRoot.get(
                    UserProfile_.dateOfBirth)), month);
            Predicate predicateEqualGreaterMonth = criteriaBuilder.greaterThan(
                criteriaBuilder.function(PARAMETER_MONTH, Integer.class, userProfileRoot.get(
                    UserProfile_.dateOfBirth)), month);
            Predicate predicateEqualOrGreaterDate = criteriaBuilder.greaterThanOrEqualTo(
                criteriaBuilder.function(PARAMETER_DAY, Integer.class, userProfileRoot.get(
                    UserProfile_.dateOfBirth)), day);
            criteriaQuery.where(criteriaBuilder.or(criteriaBuilder.and(
                predicateEqualMonth, predicateEqualOrGreaterDate), predicateEqualGreaterMonth));
            criteriaQuery.orderBy(criteriaBuilder.asc(
                criteriaBuilder.function(PARAMETER_MONTH, Integer.class, userProfileRoot.get(
                    UserProfile_.dateOfBirth))), criteriaBuilder.asc(criteriaBuilder.function(
                        PARAMETER_DAY, Integer.class, userProfileRoot.get(UserProfile_.dateOfBirth))));
            TypedQuery<UserProfile> typedQuery = entityManager.createQuery(criteriaQuery);
            typedQuery.setFirstResult(FIRST_RESULT);
            typedQuery.setMaxResults(MAX_RESULTS);
            return typedQuery.getSingleResult();
        } catch (NoResultException exception) {
            log.error("[{}]", exception.getMessage());
            return null;
        }
    }

    @Override
    public UserProfile getNearestBirthdayFromTheBeginningOfTheYear() {
        log.debug("[getNearestBirthdayFromTheBeginningOfTheYear]");
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<UserProfile> criteriaQuery = criteriaBuilder.createQuery(UserProfile.class);
            Root<UserProfile> userProfileRoot = criteriaQuery.from(UserProfile.class);
            criteriaQuery.select(userProfileRoot);
            criteriaQuery.orderBy(criteriaBuilder.asc(
                criteriaBuilder.function(PARAMETER_MONTH, Integer.class, userProfileRoot.get(
                    UserProfile_.dateOfBirth))), criteriaBuilder.asc(criteriaBuilder.function(
                        PARAMETER_DAY, Integer.class, userProfileRoot.get(UserProfile_.dateOfBirth))));
            TypedQuery<UserProfile> typedQuery = entityManager.createQuery(criteriaQuery);
            typedQuery.setFirstResult(FIRST_RESULT);
            typedQuery.setMaxResults(MAX_RESULTS);
            return typedQuery.getSingleResult();
        } catch (NoResultException exception) {
            log.error("[{}]", exception.getMessage());
            return null;
        }
    }

    @Override
    public List<UserProfile> getFriends(String email, int firstResult, int maxResults) {
        log.debug("[getFriends]");
        log.trace("[email: {}]", email);
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<UserProfile> criteriaQuery = criteriaBuilder.createQuery(UserProfile.class);
            Root<UserProfile> userProfileRoot = criteriaQuery.from(UserProfile.class);
            Join<UserProfile, SystemUser> userProfileUserProfileListJoin = userProfileRoot.join(UserProfile_.systemUser);
            criteriaQuery.select(userProfileRoot.get(UserProfile_.FRIENDS));
            criteriaQuery.where(criteriaBuilder.equal(userProfileUserProfileListJoin.get(SystemUser_.email), email));
            TypedQuery<UserProfile> typedQuery = entityManager.createQuery(criteriaQuery);
            typedQuery.setFirstResult(firstResult);
            typedQuery.setMaxResults(maxResults);
            return typedQuery.getResultList();
        } catch (NoResultException exception) {
            log.error("[{}]", exception.getMessage());
            return null;
        }
    }

}
