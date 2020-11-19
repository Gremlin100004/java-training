package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.domain.Community;
import com.senla.socialnetwork.domain.Community_;
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
import javax.persistence.criteria.Subquery;
import java.util.Date;
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
    public UserProfile findByEmail(final String email) {
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
    public List<UserProfile> getCommunityUsers(final Long communityId) {
        log.debug("[getCommunityUsers]");
        log.trace("[communityId: {}]", communityId);
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<UserProfile> criteriaQuery = criteriaBuilder.createQuery(UserProfile.class);
            Root<Community> communityRoot = criteriaQuery.from(Community.class);
            criteriaQuery.select(communityRoot.get(Community_.SUBSCRIBERS));
            criteriaQuery.where(criteriaBuilder.equal(communityRoot.get(Community_.id), communityId));
            return entityManager.createQuery(criteriaQuery).getResultList();
        } catch (NoResultException exception) {
            log.error("[{}]", exception.getMessage());
            return null;
        }
    }

    @Override
    public List<UserProfile> getUserProfilesSortBySurname(final int firstResult, final int maxResults) {
        log.debug("[getUserProfilesSortByName]");
        log.trace("[firstResult: {}, maxResults: {}]", firstResult, maxResults);
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<UserProfile> criteriaQuery = criteriaBuilder.createQuery(UserProfile.class);
            Root<UserProfile> userProfileRoot = criteriaQuery.from(UserProfile.class);
            criteriaQuery.select(userProfileRoot);
            criteriaQuery.orderBy(criteriaBuilder.asc(userProfileRoot.get(UserProfile_.surname)));
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
    public List<UserProfile> getUserProfilesSortByRegistrationDate(final int firstResult, final int maxResults) {
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
    public List<UserProfile> getUserProfilesFilteredByLocation(final Location location,
                                                               final int firstResult,
                                                               final int maxResults) {
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
    public List<UserProfile> getUserProfilesFilteredBySchool(final School school,
                                                             final int firstResult,
                                                             final int maxResults) {
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
    public List<UserProfile> getUserProfilesFilteredByUniversity(final University university,
                                                                 final int firstResult,
                                                                 final int maxResults) {
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
    public List<UserProfile> getUserProfilesFilteredByAge(final Date startPeriodDate,
                                                          final Date endPeriodDate,
                                                          final int firstResult,
                                                          final int maxResults) {
        log.debug("[getUserProfilesFilteredByAge]");
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
    public UserProfile getNearestBirthdayByCurrentDate(final String email) {
        log.debug("[getNearestBirthdayByCurrentDate]");
        log.trace("[email: {}]", email);
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            Expression<java.sql.Date> currentDate = criteriaBuilder.currentDate();
            Expression<Integer> month = criteriaBuilder.function(PARAMETER_MONTH, Integer.class, currentDate);
            Expression<Integer> day = criteriaBuilder.function(PARAMETER_DAY, Integer.class, currentDate);
            CriteriaQuery<UserProfile> criteriaQuery = criteriaBuilder.createQuery(UserProfile.class);
            Root<UserProfile> userProfileRoot = fillCriteriaQueryFriend(criteriaQuery, criteriaBuilder, email);
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
    public UserProfile getNearestBirthdayFromTheBeginningOfTheYear(final String email) {
        log.debug("[getNearestBirthdayFromTheBeginningOfTheYear]");
        log.trace("[email: {}]", email);
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<UserProfile> criteriaQuery = criteriaBuilder.createQuery(UserProfile.class);
            Root<UserProfile> userProfileRoot = fillCriteriaQueryFriend(criteriaQuery, criteriaBuilder, email);
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
    public List<UserProfile> getFriendsSortByAge(final String email, final int firstResult, final int maxResults) {
        log.debug("[getFriendsSortByAge]");
        log.trace("[email: {}, firstResult: {}, maxResults: {}]", email, firstResult, maxResults);
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<UserProfile> criteriaQuery = criteriaBuilder.createQuery(UserProfile.class);
            Root<UserProfile> userProfileRoot = fillCriteriaQueryFriend(criteriaQuery, criteriaBuilder, email);
            criteriaQuery.orderBy(criteriaBuilder.asc(userProfileRoot.get(UserProfile_.id)));
            TypedQuery<UserProfile> typedQuery = entityManager.createQuery(criteriaQuery);
            typedQuery.setFirstResult(firstResult);
            return typedQuery.getResultList();
        } catch (NoResultException exception) {
            log.error("[{}]", exception.getMessage());
            return null;
        }
    }

    @Override
    public List<UserProfile> getFriendsSortByName(final String email, final int firstResult, final int maxResults) {
        log.debug("[getFriendsSortByName]");
        log.trace("[email: {}, firstResult: {}, maxResults: {}]", email, firstResult, maxResults);
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<UserProfile> criteriaQuery = criteriaBuilder.createQuery(UserProfile.class);
            Root<UserProfile> userProfileRoot = fillCriteriaQueryFriend(criteriaQuery, criteriaBuilder, email);
            criteriaQuery.orderBy(criteriaBuilder.asc(userProfileRoot.get(UserProfile_.name)));
            TypedQuery<UserProfile> typedQuery = entityManager.createQuery(criteriaQuery);
            typedQuery.setFirstResult(firstResult);
            return typedQuery.getResultList();
        } catch (NoResultException exception) {
            log.error("[{}]", exception.getMessage());
            return null;
        }
    }

    @Override
    public List<UserProfile> getFriendsSortByNumberOfFriends(final String email, final int firstResult, final int maxResults) {
        log.debug("[getFriendsSortByNumberOfFriends]");
        log.trace("[email: {}, firstResult: {}, maxResults: {}]", email, firstResult, maxResults);
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<UserProfile> criteriaQuery = criteriaBuilder.createQuery(UserProfile.class);
            Root<UserProfile> userProfileRoot = fillCriteriaQueryFriend(criteriaQuery, criteriaBuilder, email);
            criteriaQuery.orderBy(criteriaBuilder.asc(criteriaBuilder.sum(
                criteriaBuilder.size(userProfileRoot.get(UserProfile_.friends)), criteriaBuilder.size(
                    userProfileRoot.get(UserProfile_.mappedByFriends)))));
            TypedQuery<UserProfile> typedQuery = entityManager.createQuery(criteriaQuery);
            typedQuery.setFirstResult(firstResult);
            return typedQuery.getResultList();
        } catch (NoResultException exception) {
            log.error("[{}]", exception.getMessage());
            return null;
        }
    }

    @Override
    public List<UserProfile> getFriends(final String email, final int firstResult, final int maxResults) {
        log.debug("[getFriends]");
        log.trace("[email: {}]", email);
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<UserProfile> criteriaQuery = criteriaBuilder.createQuery(UserProfile.class);
            fillCriteriaQueryFriend(criteriaQuery, criteriaBuilder, email);
            TypedQuery<UserProfile> typedQuery = entityManager.createQuery(criteriaQuery);
            typedQuery.setFirstResult(firstResult);
            if (maxResults != 0) {
                typedQuery.setMaxResults(maxResults);
            }
            return typedQuery.getResultList();
        } catch (NoResultException exception) {
            log.error("[{}]", exception.getMessage());
            return null;
        }
    }

    @Override
    public UserProfile getFriend(final String email, final Long userProfileId) {
        log.debug("[getFriend]");
        log.trace("[email: {}, userProfileId: {}]", email, userProfileId);
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<UserProfile> criteriaQuery = criteriaBuilder.createQuery(UserProfile.class);
            Subquery<Long> subquery = criteriaQuery.subquery(Long.class);
            Root<UserProfile> userProfileRoot = subquery.from(UserProfile.class);
            Join<UserProfile, UserProfile> userProfileUserProfileJoin = userProfileRoot.join(
                UserProfile_.friends);
            Join<UserProfile, SystemUser> userProfileSystemUserJoin = userProfileRoot.join(UserProfile_.systemUser);
            subquery.select(userProfileUserProfileJoin.get(UserProfile_.id)).distinct(true);
            subquery.where(criteriaBuilder.equal(userProfileSystemUserJoin.get(SystemUser_.email), email));
            Subquery<Long> subqueryOther = criteriaQuery.subquery(Long.class);
            Root<UserProfile> profileRoot = subqueryOther.from(UserProfile.class);
            Join<UserProfile, UserProfile> userProfileUserProfileSetJoin = profileRoot.join(
                UserProfile_.mappedByFriends);
            Join<UserProfile, SystemUser> profileSystemUserJoin = profileRoot.join(UserProfile_.systemUser);
            subqueryOther.select(userProfileUserProfileSetJoin.get(UserProfile_.id)).distinct(true);
            subqueryOther.where(criteriaBuilder.equal(profileSystemUserJoin.get(SystemUser_.email), email));
            Root<UserProfile> friendRoot = criteriaQuery.from(UserProfile.class);
            criteriaQuery.select(friendRoot).distinct(true);
            criteriaQuery.where(criteriaBuilder.or(criteriaBuilder.and(friendRoot.get(UserProfile_.id).in(
                subqueryOther), criteriaBuilder.equal(friendRoot.get(
                UserProfile_.id), userProfileId)), criteriaBuilder.and(friendRoot.get(
                    UserProfile_.id).in(subquery), criteriaBuilder.equal(
                        friendRoot.get(UserProfile_.id), userProfileId))));
            return entityManager.createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException exception) {
            log.error("[{}]", exception.getMessage());
            return null;
        }
    }

    @Override
    public List<UserProfile> getSignedFriends(final String email, final int firstResult, final int maxResults) {
        log.debug("[getFriends]");
        log.trace("[email: {}]", email);
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<UserProfile> criteriaQuery = criteriaBuilder.createQuery(UserProfile.class);
            Subquery<Long> subquery = criteriaQuery.subquery(Long.class);
            Root<UserProfile> friendsRoot = subquery.from(UserProfile.class);
            Join<UserProfile, UserProfile> userProfileUserProfileJoin = friendsRoot.join(
                UserProfile_.friendshipRequests);
            Join<UserProfile, SystemUser> userProfileSystemUserJoin = friendsRoot.join(UserProfile_.systemUser);
            subquery.select(userProfileUserProfileJoin.get(UserProfile_.id)).distinct(true);
            subquery.where(criteriaBuilder.equal(userProfileSystemUserJoin.get(SystemUser_.email), email));
            Subquery<Long> subqueryOther = criteriaQuery.subquery(Long.class);
            Root<UserProfile> profileRoot = subqueryOther.from(UserProfile.class);
            Join<UserProfile, UserProfile> userProfileUserProfileSetJoin = profileRoot.join(
                UserProfile_.mappedByFriendshipRequests);
            Join<UserProfile, SystemUser> profileSystemUserJoin = profileRoot.join(UserProfile_.systemUser);
            subqueryOther.select(userProfileUserProfileSetJoin.get(UserProfile_.id)).distinct(true);
            subqueryOther.where(criteriaBuilder.equal(profileSystemUserJoin.get(SystemUser_.email), email));
            Root<UserProfile> userProfileRoot = criteriaQuery.from(UserProfile.class);
            criteriaQuery.select(userProfileRoot);
            criteriaQuery.where(criteriaBuilder.or(userProfileRoot.get(
                UserProfile_.id).in(subquery), userProfileRoot.get(UserProfile_.id).in(subqueryOther)));
            TypedQuery<UserProfile> typedQuery = entityManager.createQuery(criteriaQuery);
            typedQuery.setFirstResult(firstResult);
            if (maxResults != 0) {
                typedQuery.setMaxResults(maxResults);
            }
            return typedQuery.getResultList();
        } catch (NoResultException exception) {
            log.error("[{}]", exception.getMessage());
            return null;
        }
    }
    //ToDo check this method
    @Override
    public UserProfile getSignedFriend(final String email, final Long userProfileId) {
        log.debug("[getSignedFriend]");
        log.trace("[email: {}, userProfileId: {}]", email, userProfileId);
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<UserProfile> criteriaQuery = criteriaBuilder.createQuery(UserProfile.class);
            Subquery<Long> subquery = criteriaQuery.subquery(Long.class);
            Root<UserProfile> userProfileRoot = subquery.from(UserProfile.class);
            Join<UserProfile, UserProfile> userProfileUserProfileJoin = userProfileRoot.join(
                UserProfile_.friendshipRequests);
            Join<UserProfile, SystemUser> userProfileSystemUserJoin = userProfileRoot.join(UserProfile_.systemUser);
            subquery.select(userProfileUserProfileJoin.get(UserProfile_.id)).distinct(true);
            subquery.where(criteriaBuilder.equal(userProfileSystemUserJoin.get(SystemUser_.email), email));
            Subquery<Long> subqueryOther = criteriaQuery.subquery(Long.class);
            Root<UserProfile> profileRoot = subqueryOther.from(UserProfile.class);
            Join<UserProfile, UserProfile> userProfileUserProfileSetJoin = profileRoot.join(
                UserProfile_.mappedByFriendshipRequests);
            Join<UserProfile, SystemUser> profileSystemUserJoin = profileRoot.join(UserProfile_.systemUser);
            subqueryOther.select(userProfileUserProfileSetJoin.get(UserProfile_.id)).distinct(true);
            subqueryOther.where(criteriaBuilder.equal(profileSystemUserJoin.get(SystemUser_.email), email));
            Root<UserProfile> futureFriendRoot = criteriaQuery.from(UserProfile.class);
            criteriaQuery.select(futureFriendRoot);
            criteriaQuery.where(criteriaBuilder.or(criteriaBuilder.and(futureFriendRoot.get(UserProfile_.id).in(
                subqueryOther), criteriaBuilder.equal(futureFriendRoot.get(
                    UserProfile_.id), userProfileId)), criteriaBuilder.and(futureFriendRoot.get(
                        UserProfile_.id).in(subquery), criteriaBuilder.equal(
                            futureFriendRoot.get(UserProfile_.id), userProfileId))));
            return entityManager.createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException exception) {
            log.error("[{}]", exception.getMessage());
            return null;
        }
    }

    @Override
    public UserProfile getFutureFriend(final String email, final Long userProfileId) {
        log.debug("[getFriends]");
        log.trace("[email: {}, userProfileId: {}]", email, userProfileId);
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<UserProfile> criteriaQuery = criteriaBuilder.createQuery(UserProfile.class);
            Subquery<Long> subquery = criteriaQuery.subquery(Long.class);
            Root<UserProfile> userProfileRoot = subquery.from(UserProfile.class);
            Join<UserProfile, UserProfile> userProfileUserProfileJoin = userProfileRoot.join(UserProfile_.friends);
            Join<UserProfile, SystemUser> userProfileSystemUserJoin = userProfileRoot.join(UserProfile_.systemUser);
            subquery.select(userProfileUserProfileJoin.get(UserProfile_.id)).distinct(true);
            subquery.where(criteriaBuilder.equal(userProfileSystemUserJoin.get(SystemUser_.email), email));
            Subquery<Long> subqueryOther = criteriaQuery.subquery(Long.class);
            Root<UserProfile> profileRoot = subqueryOther.from(UserProfile.class);
            Join<UserProfile, UserProfile> userProfileUserProfileSetJoin = profileRoot.join(UserProfile_.mappedByFriends);
            Join<UserProfile, SystemUser> profileSystemUserJoin = profileRoot.join(UserProfile_.systemUser);
            subqueryOther.select(userProfileUserProfileSetJoin.get(UserProfile_.id)).distinct(true);
            subqueryOther.where(criteriaBuilder.equal(profileSystemUserJoin.get(SystemUser_.email), email));
            Root<UserProfile> futureFriendRoot = criteriaQuery.from(UserProfile.class);
            criteriaQuery.select(futureFriendRoot);
            criteriaQuery.where(criteriaBuilder.and(futureFriendRoot.get(UserProfile_.id).in(
                subqueryOther).not(), criteriaBuilder.equal(futureFriendRoot.get(
                    UserProfile_.id), userProfileId), futureFriendRoot.get(UserProfile_.id).in(subquery).not()));
           return entityManager.createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException exception) {
            log.error("[{}]", exception.getMessage());
            return null;
        }
    }

    private Root<UserProfile> fillCriteriaQueryFriend(final CriteriaQuery<UserProfile> criteriaQuery,
                                                      final CriteriaBuilder criteriaBuilder,
                                                      final String email) {
        Subquery<Long> subquery = criteriaQuery.subquery(Long.class);
        Root<UserProfile> friendsRoot = subquery.from(UserProfile.class);
        Join<UserProfile, UserProfile> userProfileUserProfileJoin = friendsRoot.join(
            UserProfile_.friends);
        Join<UserProfile, SystemUser> userProfileSystemUserJoin = friendsRoot.join(UserProfile_.systemUser);
        subquery.select(userProfileUserProfileJoin.get(UserProfile_.id)).distinct(true);
        subquery.where(criteriaBuilder.equal(userProfileSystemUserJoin.get(SystemUser_.email), email));
        Subquery<Long> subqueryOther = criteriaQuery.subquery(Long.class);
        Root<UserProfile> profileRoot = subqueryOther.from(UserProfile.class);
        Join<UserProfile, UserProfile> userProfileUserProfileSetJoin = profileRoot.join(
            UserProfile_.mappedByFriends);
        Join<UserProfile, SystemUser> profileSystemUserJoin = profileRoot.join(UserProfile_.systemUser);
        subqueryOther.select(userProfileUserProfileSetJoin.get(UserProfile_.id)).distinct(true);
        subqueryOther.where(criteriaBuilder.equal(profileSystemUserJoin.get(SystemUser_.email), email));
        Root<UserProfile> userProfileRoot = criteriaQuery.from(UserProfile.class);
        criteriaQuery.select(userProfileRoot);
        criteriaQuery.where(criteriaBuilder.or(userProfileRoot.get(
            UserProfile_.id).in(subquery), userProfileRoot.get(UserProfile_.id).in(subqueryOther)));
        return userProfileRoot;
    }

}
