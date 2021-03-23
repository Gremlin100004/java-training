package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.model.Location;
import com.senla.socialnetwork.model.Location_;
import com.senla.socialnetwork.model.School;
import com.senla.socialnetwork.model.School_;
import com.senla.socialnetwork.model.SystemUser;
import com.senla.socialnetwork.model.SystemUser_;
import com.senla.socialnetwork.model.University;
import com.senla.socialnetwork.model.UserProfile;
import com.senla.socialnetwork.model.UserProfile_;
import org.springframework.stereotype.Repository;

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
public class UserProfileCriteriaApiDaoImpl extends AbstractDao<UserProfile, Long> implements UserProfileDao {
    private static final String PARAMETER_MONTH = "month";
    private static final String PARAMETER_DAY = "day";
    private static final int FIRST_RESULT = 0;
    private static final int MAX_RESULTS = 1;

    public UserProfileCriteriaApiDaoImpl() {
        setType(UserProfile.class);
    }

    @Override
    public UserProfile findByEmail(final String email) {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<UserProfile> criteriaQuery = criteriaBuilder.createQuery(UserProfile.class);
            Root<UserProfile> userProfileRoot = criteriaQuery.from(UserProfile.class);
            Join<UserProfile, SystemUser> userProfileSystemUserJoin = userProfileRoot.join(UserProfile_.systemUser);
            criteriaQuery.select(userProfileRoot);
            criteriaQuery.where(criteriaBuilder.equal(userProfileSystemUserJoin.get(SystemUser_.email), email));
            return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    @Override
    public List<UserProfile> getUserProfilesSortBySurname(final int firstResult, final int maxResults) {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<UserProfile> criteriaQuery = criteriaBuilder.createQuery(UserProfile.class);
            Root<UserProfile> userProfileRoot = criteriaQuery.from(UserProfile.class);
            criteriaQuery.select(userProfileRoot);
            criteriaQuery.orderBy(criteriaBuilder.asc(userProfileRoot.get(UserProfile_.surname)));
            TypedQuery<UserProfile> typedQuery = entityManager.createQuery(criteriaQuery);
            typedQuery.setFirstResult(firstResult);
            if (maxResults != 0) {
                typedQuery.setMaxResults(maxResults);
            }
            return typedQuery.getResultList();
    }

    @Override
    public List<UserProfile> getUserProfilesSortByRegistrationDate(final int firstResult, final int maxResults) {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<UserProfile> criteriaQuery = criteriaBuilder.createQuery(UserProfile.class);
            Root<UserProfile> userProfileRoot = criteriaQuery.from(UserProfile.class);
            criteriaQuery.select(userProfileRoot);
            criteriaQuery.orderBy(criteriaBuilder.desc(userProfileRoot.get(UserProfile_.registrationDate)));
            TypedQuery<UserProfile> typedQuery = entityManager.createQuery(criteriaQuery);
            typedQuery.setFirstResult(firstResult);
            if (maxResults != 0) {
                typedQuery.setMaxResults(maxResults);
            }
            return typedQuery.getResultList();
    }

    @Override
    public List<UserProfile> getUserProfilesFilteredByLocation(final Long locationId,
                                                               final int firstResult,
                                                               final int maxResults) {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<UserProfile> criteriaQuery = criteriaBuilder.createQuery(UserProfile.class);
            Root<UserProfile> userProfileRoot = criteriaQuery.from(UserProfile.class);
            Join<UserProfile, Location> userProfileLocationJoin = userProfileRoot.join(UserProfile_.location);
            criteriaQuery.select(userProfileRoot);
            criteriaQuery.where(criteriaBuilder.equal(userProfileLocationJoin.get(Location_.id), locationId));
            TypedQuery<UserProfile> typedQuery = entityManager.createQuery(criteriaQuery);
            typedQuery.setFirstResult(firstResult);
            if (maxResults != 0) {
                typedQuery.setMaxResults(maxResults);
            }
            return typedQuery.getResultList();
    }

    @Override
    public List<UserProfile> getUserProfilesFilteredBySchool(final Long schoolId,
                                                             final int firstResult,
                                                             final int maxResults) {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<UserProfile> criteriaQuery = criteriaBuilder.createQuery(UserProfile.class);
            Root<UserProfile> userProfileRoot = criteriaQuery.from(UserProfile.class);
            Join<UserProfile, School> userProfileSchoolJoin = userProfileRoot.join(UserProfile_.school);
            criteriaQuery.select(userProfileRoot);
            criteriaQuery.where(criteriaBuilder.equal(userProfileSchoolJoin.get(School_.id), schoolId));
            TypedQuery<UserProfile> typedQuery = entityManager.createQuery(criteriaQuery);
            typedQuery.setFirstResult(firstResult);
            if (maxResults != 0) {
                typedQuery.setMaxResults(maxResults);
            }
            return typedQuery.getResultList();
    }

    @Override
    public List<UserProfile> getUserProfilesFilteredByUniversity(final Long universityId,
                                                                 final int firstResult,
                                                                 final int maxResults) {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<UserProfile> criteriaQuery = criteriaBuilder.createQuery(UserProfile.class);
            Root<UserProfile> userProfileRoot = criteriaQuery.from(UserProfile.class);
            Join<UserProfile, University> userProfileUniversityJoin = userProfileRoot.join(UserProfile_.university);
            criteriaQuery.select(userProfileRoot);
            criteriaQuery.where(criteriaBuilder.equal(userProfileUniversityJoin.get(Location_.id), universityId));
            TypedQuery<UserProfile> typedQuery = entityManager.createQuery(criteriaQuery);
            typedQuery.setFirstResult(firstResult);
            if (maxResults != 0) {
                typedQuery.setMaxResults(maxResults);
            }
            return typedQuery.getResultList();
    }

    @Override
    public List<UserProfile> getUserProfilesFilteredByAge(final Date startPeriodDate,
                                                          final Date endPeriodDate,
                                                          final int firstResult,
                                                          final int maxResults) {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<UserProfile> criteriaQuery = criteriaBuilder.createQuery(UserProfile.class);
            Root<UserProfile> orderRoot = criteriaQuery.from(UserProfile.class);
            criteriaQuery.select(orderRoot);
            criteriaQuery.where(
                criteriaBuilder.between(orderRoot.get(UserProfile_.dateOfBirth), startPeriodDate, endPeriodDate));
            criteriaQuery.orderBy(criteriaBuilder.asc(orderRoot.get(UserProfile_.dateOfBirth)));
            TypedQuery<UserProfile> typedQuery = entityManager.createQuery(criteriaQuery);
            typedQuery.setFirstResult(firstResult);
            if (maxResults != 0) {
                typedQuery.setMaxResults(maxResults);
            }
            return typedQuery.getResultList();
    }

    @Override
    public UserProfile getNearestBirthdayByCurrentDate(final String email) {
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
    }

    @Override
    public UserProfile getNearestBirthdayFromTheBeginningOfTheYear(final String email) {
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
    }

    @Override
    public List<UserProfile> getFriendsSortByAge(final String email, final int firstResult, final int maxResults) {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<UserProfile> criteriaQuery = criteriaBuilder.createQuery(UserProfile.class);
            Root<UserProfile> userProfileRoot = fillCriteriaQueryFriend(criteriaQuery, criteriaBuilder, email);
            criteriaQuery.orderBy(criteriaBuilder.asc(userProfileRoot.get(UserProfile_.id)));
            TypedQuery<UserProfile> typedQuery = entityManager.createQuery(criteriaQuery);
            typedQuery.setFirstResult(firstResult);
            return typedQuery.getResultList();
    }

    @Override
    public List<UserProfile> getFriendsSortByName(final String email, final int firstResult, final int maxResults) {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<UserProfile> criteriaQuery = criteriaBuilder.createQuery(UserProfile.class);
            Root<UserProfile> userProfileRoot = fillCriteriaQueryFriend(criteriaQuery, criteriaBuilder, email);
            criteriaQuery.orderBy(criteriaBuilder.asc(userProfileRoot.get(UserProfile_.name)));
            TypedQuery<UserProfile> typedQuery = entityManager.createQuery(criteriaQuery);
            typedQuery.setFirstResult(firstResult);
            return typedQuery.getResultList();
    }

    @Override
    public List<UserProfile> getFriendsSortByNumberOfFriends(final String email, final int firstResult, final int maxResults) {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<UserProfile> criteriaQuery = criteriaBuilder.createQuery(UserProfile.class);
            Root<UserProfile> userProfileRoot = fillCriteriaQueryFriend(criteriaQuery, criteriaBuilder, email);
            criteriaQuery.orderBy(criteriaBuilder.asc(criteriaBuilder.sum(
                criteriaBuilder.size(userProfileRoot.get(UserProfile_.friends)), criteriaBuilder.size(
                    userProfileRoot.get(UserProfile_.mappedByFriends)))));
            TypedQuery<UserProfile> typedQuery = entityManager.createQuery(criteriaQuery);
            typedQuery.setFirstResult(firstResult);
            return typedQuery.getResultList();
    }

    @Override
    public List<UserProfile> getFriends(final String email, final int firstResult, final int maxResults) {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<UserProfile> criteriaQuery = criteriaBuilder.createQuery(UserProfile.class);
            fillCriteriaQueryFriend(criteriaQuery, criteriaBuilder, email);
            TypedQuery<UserProfile> typedQuery = entityManager.createQuery(criteriaQuery);
            typedQuery.setFirstResult(firstResult);
            if (maxResults != 0) {
                typedQuery.setMaxResults(maxResults);
            }
            return typedQuery.getResultList();
    }

    @Override
    public UserProfile getFriend(final String email, final Long userProfileId) {
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
    }

    @Override
    public List<UserProfile> getSignedFriends(final String email, final int firstResult, final int maxResults) {
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
    }

    @Override
    public UserProfile getSignedFriend(final String email, final Long userProfileId) {
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
    }

    @Override
    public UserProfile getFutureFriend(final String email, final Long userProfileId) {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<UserProfile> criteriaQuery = criteriaBuilder.createQuery(UserProfile.class);
            Subquery<Long> subquery = criteriaQuery.subquery(Long.class);
            Root<UserProfile> userProfileRoot = subquery.from(UserProfile.class);
            Join<UserProfile, UserProfile> userProfileUserProfileJoin = userProfileRoot.join(UserProfile_.friends);
            Join<UserProfile, SystemUser> userProfileSystemUserJoin = userProfileRoot.join(UserProfile_.systemUser);
            subquery.select(userProfileUserProfileJoin.get(UserProfile_.id)).distinct(true);
            subquery.where(criteriaBuilder.equal(userProfileSystemUserJoin.get(SystemUser_.email), email));
            Subquery<Long> subqueryFriends = criteriaQuery.subquery(Long.class);
            Root<UserProfile> profileSubqueryFriendRoot = subqueryFriends.from(UserProfile.class);
            Join<UserProfile, UserProfile> userProfileUserProfileSetJoin = profileSubqueryFriendRoot.join(
                UserProfile_.mappedByFriends);
            Join<UserProfile, SystemUser> profileSystemUserJoin = profileSubqueryFriendRoot.join(UserProfile_.systemUser);
            subqueryFriends.select(userProfileUserProfileSetJoin.get(UserProfile_.id)).distinct(true);
            subqueryFriends.where(criteriaBuilder.equal(profileSystemUserJoin.get(SystemUser_.email), email));
            Subquery<Long> subqueryFriendShipRequest = criteriaQuery.subquery(Long.class);
            Root<UserProfile> profileFriendShipRequestRoot = subqueryFriendShipRequest.from(UserProfile.class);
            Join<UserProfile, UserProfile> userProfileUserProfileSubqueryJoin = profileFriendShipRequestRoot.join(
                UserProfile_.mappedByFriendshipRequests);
            subqueryFriendShipRequest.select(userProfileUserProfileSubqueryJoin.get(UserProfile_.id)).distinct(true);
            subqueryFriendShipRequest.where(criteriaBuilder.equal(userProfileUserProfileSubqueryJoin.get(
                UserProfile_.id), userProfileId));
            Root<UserProfile> futureFriendRoot = criteriaQuery.from(UserProfile.class);
            criteriaQuery.select(futureFriendRoot);
            criteriaQuery.where(criteriaBuilder.and(futureFriendRoot.get(UserProfile_.id).in(
                subqueryFriends).not(), criteriaBuilder.equal(futureFriendRoot.get(
                    UserProfile_.id), userProfileId), futureFriendRoot.get(
                        UserProfile_.id).in(subquery).not(), futureFriendRoot.get(UserProfile_.id).in(
                            subqueryFriendShipRequest).not()));
           return entityManager.createQuery(criteriaQuery).getSingleResult();
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
