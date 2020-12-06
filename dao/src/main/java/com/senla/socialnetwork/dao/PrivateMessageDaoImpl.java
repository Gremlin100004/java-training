package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.domain.PrivateMessage;
import com.senla.socialnetwork.domain.PrivateMessage_;
import com.senla.socialnetwork.domain.SystemUser;
import com.senla.socialnetwork.domain.SystemUser_;
import com.senla.socialnetwork.domain.UserProfile;
import com.senla.socialnetwork.domain.UserProfile_;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

@Repository
@Slf4j
public class PrivateMessageDaoImpl extends AbstractDao<PrivateMessage, Long> implements PrivateMessageDao {
    public PrivateMessageDaoImpl() {
        setType(PrivateMessage.class);
    }

    @Override
    public List<PrivateMessage> getByEmail(final String email, final int firstResult, final int maxResults) {
        log.debug("[email: {}, firstResult: {},maxResults: {}]", email, firstResult, maxResults);
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<PrivateMessage> criteriaQuery = criteriaBuilder.createQuery(PrivateMessage.class);
            Root<PrivateMessage> privateMessageRoot = criteriaQuery.from(PrivateMessage.class);
            Join<PrivateMessage, UserProfile> privateMessageSenderJoin = privateMessageRoot.join(
                PrivateMessage_.sender);
            Join<UserProfile, SystemUser> senderSystemUserJoin = privateMessageSenderJoin.join(UserProfile_.systemUser);
            Join<PrivateMessage, UserProfile> privateMessageRecipientJoin = privateMessageRoot.join(
                PrivateMessage_.recipient);
            Join<UserProfile, SystemUser> recipientSystemUserJoin = privateMessageRecipientJoin.join(
                UserProfile_.systemUser);
            criteriaQuery.select(privateMessageRoot);
            Predicate predicateSender = criteriaBuilder.equal(senderSystemUserJoin.get(SystemUser_.email), email);
            Predicate predicateRecipient = criteriaBuilder.equal(recipientSystemUserJoin.get(SystemUser_.email), email);
            Predicate predicateDeleted = criteriaBuilder.equal(privateMessageRoot.get(PrivateMessage_.isDeleted), false);
            criteriaQuery.where(criteriaBuilder.or(criteriaBuilder.and(
                predicateSender, predicateDeleted), criteriaBuilder.and(predicateRecipient, predicateDeleted)));
            criteriaQuery.orderBy(criteriaBuilder.asc(privateMessageRoot.get(PrivateMessage_.departureDate)));
            TypedQuery<PrivateMessage> typedQuery = entityManager.createQuery(criteriaQuery);
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
    public List<PrivateMessage> getDialogue(final String email,
                                            final Long idUser,
                                            final int firstResult,
                                            final int maxResults) {
        log.debug("[email: {}, idUser: {}, firstResult: {},maxResults: {}]", email, idUser, firstResult, maxResults);
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<PrivateMessage> criteriaQuery = criteriaBuilder.createQuery(PrivateMessage.class);
            Root<PrivateMessage> privateMessageRoot = criteriaQuery.from(PrivateMessage.class);
            Join<PrivateMessage, UserProfile> privateMessageSenderJoin = privateMessageRoot.join(
                PrivateMessage_.sender);
            Join<UserProfile, SystemUser> senderSystemUserJoin = privateMessageSenderJoin.join(UserProfile_.systemUser);
            Join<PrivateMessage, UserProfile> privateMessageRecipientJoin = privateMessageRoot.join(
                PrivateMessage_.recipient);
            Join<UserProfile, SystemUser> recipientSystemUserJoin = privateMessageRecipientJoin.join(
                UserProfile_.systemUser);
            criteriaQuery.select(privateMessageRoot);
            Predicate predicateUserSender = criteriaBuilder.equal(
                senderSystemUserJoin.get(SystemUser_.id), idUser);
            Predicate predicateUserRecipient = criteriaBuilder.equal(
                recipientSystemUserJoin.get(SystemUser_.id), idUser);
            Predicate predicateOwnProfileSender = criteriaBuilder.equal(
                senderSystemUserJoin.get(SystemUser_.email), email);
            Predicate predicateOwnProfileRecipient = criteriaBuilder.equal(
                recipientSystemUserJoin.get(SystemUser_.email), email);
            Predicate predicateDeleted = criteriaBuilder.equal(
                privateMessageRoot.get(PrivateMessage_.isDeleted), false);
            criteriaQuery.where(criteriaBuilder.or(
                criteriaBuilder.and(
                    predicateUserSender, predicateOwnProfileRecipient, predicateDeleted), criteriaBuilder.and(
                    predicateOwnProfileSender, predicateUserRecipient, predicateDeleted)));
            criteriaQuery.orderBy(criteriaBuilder.asc(privateMessageRoot.get(PrivateMessage_.departureDate)));
            TypedQuery<PrivateMessage> typedQuery = entityManager.createQuery(criteriaQuery);
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
    public List<PrivateMessage> getUnreadMessages(final String email, final int firstResult, final int maxResults) {
        log.trace("[debug: {}]", email);
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<PrivateMessage> criteriaQuery = criteriaBuilder.createQuery(PrivateMessage.class);
            Root<PrivateMessage> privateMessageRoot = criteriaQuery.from(PrivateMessage.class);
            Join<PrivateMessage, UserProfile> privateMessageRecipientJoin = privateMessageRoot.join(
                PrivateMessage_.recipient);
            Join<UserProfile, SystemUser> recipientSystemUserJoin = privateMessageRecipientJoin.join(
                UserProfile_.systemUser);
            criteriaQuery.select(privateMessageRoot);
            Predicate predicateRecipient = criteriaBuilder.equal(
                recipientSystemUserJoin.get(SystemUser_.email), email);
            Predicate predicateUnreadMessages = criteriaBuilder.equal(
                privateMessageRoot.get(PrivateMessage_.isRead), false);
            Predicate predicateDeleted = criteriaBuilder.equal(
                privateMessageRoot.get(PrivateMessage_.isDeleted), false);
            criteriaQuery.where(criteriaBuilder.and(predicateRecipient, predicateUnreadMessages, predicateDeleted));
            criteriaQuery.orderBy(criteriaBuilder.asc(privateMessageRoot.get(PrivateMessage_.departureDate)));
            TypedQuery<PrivateMessage> typedQuery = entityManager.createQuery(criteriaQuery);
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
    public List<PrivateMessage> getMessageFilteredByPeriod(final String email,
                                                           final Date startPeriodDate,
                                                           final Date endPeriodDate,
                                                           final int firstResult,
                                                           final int maxResults) {
        log.debug("[email: {}, startPeriodDate: {}, endPeriodDate: {}, firstResult: {}, maxResults: {}]",
                  email, startPeriodDate, endPeriodDate, firstResult, maxResults);
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<PrivateMessage> criteriaQuery = criteriaBuilder.createQuery(PrivateMessage.class);
            Root<PrivateMessage> privateMessageRoot = criteriaQuery.from(PrivateMessage.class);
            Join<PrivateMessage, UserProfile> privateMessageSenderJoin = privateMessageRoot.join(
                PrivateMessage_.sender);
            Join<UserProfile, SystemUser> senderSystemUserJoin = privateMessageSenderJoin.join(UserProfile_.systemUser);
            Join<PrivateMessage, UserProfile> privateMessageRecipientJoin = privateMessageRoot.join(
                PrivateMessage_.recipient);
            Join<UserProfile, SystemUser> recipientSystemUserJoin = privateMessageRecipientJoin.join(
                UserProfile_.systemUser);
            criteriaQuery.select(privateMessageRoot);
            Predicate predicateSender = criteriaBuilder.equal(senderSystemUserJoin.get(SystemUser_.email), email);
            Predicate predicateRecipient = criteriaBuilder.equal(recipientSystemUserJoin.get(SystemUser_.email), email);
            Predicate predicateDeleted = criteriaBuilder.equal(privateMessageRoot.get(
                PrivateMessage_.isDeleted), false);
            Predicate predicatePeriod = criteriaBuilder.between(
                privateMessageRoot.get(PrivateMessage_.departureDate), startPeriodDate, endPeriodDate);
            criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.or(criteriaBuilder.and(
                predicateSender, predicateDeleted), criteriaBuilder.and(
                    predicateRecipient, predicateDeleted))), predicatePeriod);
            criteriaQuery.orderBy(criteriaBuilder.asc(privateMessageRoot.get(PrivateMessage_.departureDate)));
            TypedQuery<PrivateMessage> typedQuery = entityManager.createQuery(criteriaQuery);
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
    public PrivateMessage findByIdAndEmail(final String email, final Long messageId) {
        log.debug("[email: {}, messageId: {}]", email, messageId);
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<PrivateMessage> criteriaQuery = criteriaBuilder.createQuery(PrivateMessage.class);
            Root<PrivateMessage> privateMessageRoot = criteriaQuery.from(PrivateMessage.class);
            Join<PrivateMessage, UserProfile> privateMessageSenderJoin = privateMessageRoot.join(
                PrivateMessage_.sender);
            Join<UserProfile, SystemUser> senderSystemUserJoin = privateMessageSenderJoin.join(
                UserProfile_.systemUser);
            Join<PrivateMessage, UserProfile> privateMessageRecipientJoin = privateMessageRoot.join(
                PrivateMessage_.recipient);
            Join<UserProfile, SystemUser> recipientSystemUserJoin = privateMessageRecipientJoin.join(
                UserProfile_.systemUser);
            criteriaQuery.select(privateMessageRoot).distinct(true);
            criteriaQuery.where(criteriaBuilder.or(criteriaBuilder.and(criteriaBuilder.equal(
                senderSystemUserJoin.get(SystemUser_.email), email), criteriaBuilder.equal(
                    privateMessageRoot.get(PrivateMessage_.id), messageId)), criteriaBuilder.and(
                        criteriaBuilder.equal(recipientSystemUserJoin.get(
                            SystemUser_.email), email), criteriaBuilder.equal(privateMessageRoot.get(
                                PrivateMessage_.id), messageId))));
            return entityManager.createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException exception) {
            log.error("[{}]", exception.getMessage());
            return null;
        }
    }

}
