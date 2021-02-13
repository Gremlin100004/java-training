package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.domain.Community;
import com.senla.socialnetwork.domain.enumaration.CommunityType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommunitySpringJpqlDataDao extends JpaRepository<Community, Long> {
    String JPQL_REQUEST_FIND_BY_ID_AND_EMAIL = "SELECT c FROM Community c INNER JOIN c.author up INNER JOIN "
       + "up.systemUser su WHERE c.id=?1 AND su.email=?2";
    String JPQL_REQUEST_GET_COMMUNITIES_BY_TYPE = "SELECT c FROM Community c WHERE c.isDeleted=false AND c.type=?1";
    String JPQL_REQUEST_GET_COMMUNITIES_SORTIED_BY_NUMBER_OF_SUBSCRIBERS = "SELECT c FROM Community c ORDER BY "
       + "size(c.subscribers)";
    String JPQL_REQUEST_GET_COMMUNITIES_BY_EMAIL = "SELECT c FROM Community c INNER JOIN c.author up INNER JOIN "
       + "up.systemUser su WHERE su.email=?1 AND c.isDeleted=false";
    String JPQL_REQUEST_GET_SUBSCRIBED_COMMUNITIES_BY_EMAIL = "SELECT c FROM UserProfile up INNER JOIN up.systemUser su "
       + "INNER JOIN up.communitiesSubscribedTo c WHERE su.email=?1 AND c.isDeleted=false";

    @Query(JPQL_REQUEST_FIND_BY_ID_AND_EMAIL)
    Optional<Community> findByIdAndEmail(Long id, String Email);

    List<Community> findByIsDeletedFalse(Pageable pageable);

    @Query(JPQL_REQUEST_GET_COMMUNITIES_BY_TYPE)
    List<Community> getCommunitiesByType(CommunityType communityType, Pageable pageable);

    @Query(JPQL_REQUEST_GET_COMMUNITIES_SORTIED_BY_NUMBER_OF_SUBSCRIBERS)
    List<Community> OrderByCountBySubscribers(Pageable pageable);

    @Query(JPQL_REQUEST_GET_COMMUNITIES_BY_EMAIL)
    List<Community> getCommunitiesByEmail(String email, Pageable pageable);

    @Query(JPQL_REQUEST_GET_SUBSCRIBED_COMMUNITIES_BY_EMAIL)
    List<Community> getSubscribedCommunitiesByEmail(String email, Pageable pageable);

}
