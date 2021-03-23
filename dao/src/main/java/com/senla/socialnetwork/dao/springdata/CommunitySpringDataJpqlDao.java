package com.senla.socialnetwork.dao.springdata;

import com.senla.socialnetwork.model.Community;
import com.senla.socialnetwork.model.Community_;
import com.senla.socialnetwork.model.UserProfile_;
import com.senla.socialnetwork.model.enumaration.CommunityType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommunitySpringDataJpqlDao extends JpaRepository<Community, Long> {
    String JPQL_REQUEST_FIND_BY_ID_AND_EMAIL = "SELECT c FROM Community c INNER JOIN c.author up INNER JOIN "
       + "up.systemUser su WHERE c.id=?1 AND su.email=?2";
    String JPQL_REQUEST_GET_COMMUNITIES_BY_TYPE = "SELECT c FROM Community c WHERE c.isDeleted=false AND c.type=?1";
    String JPQL_REQUEST_GET_COMMUNITIES_SORTIED_BY_NUMBER_OF_SUBSCRIBERS = "SELECT c FROM Community c WHERE "
       + "c.isDeleted=false ORDER BY size(c.subscribers)";
    String JPQL_REQUEST_GET_COMMUNITIES_BY_EMAIL = "SELECT c FROM Community c INNER JOIN c.author up INNER JOIN "
       + "up.systemUser su WHERE su.email=?1 AND c.isDeleted=false";
    String JPQL_REQUEST_GET_SUBSCRIBED_COMMUNITIES_BY_EMAIL = "SELECT c FROM Community c WHERE c.id in "
       + "(SELECT cst.id FROM UserProfile up INNER JOIN up.systemUser su INNER JOIN up.communitiesSubscribedTo cst "
       + "WHERE su.email=?1 AND cst.isDeleted=false)";
    String GRAPH_NAME = "graph.Community";
    String SEPARATION_SYMBOL = ".";

    @Query(JPQL_REQUEST_FIND_BY_ID_AND_EMAIL)
    @EntityGraph(value = GRAPH_NAME)
    Optional<Community> findByIdAndEmail(Long id, String email);

    @EntityGraph(value = GRAPH_NAME)
    List<Community> findByIsDeletedFalse(Pageable pageable);

    @Query(JPQL_REQUEST_GET_COMMUNITIES_BY_TYPE)
    @EntityGraph(value = GRAPH_NAME)
    List<Community> getCommunitiesByType(CommunityType communityType, Pageable pageable);

    @Query(JPQL_REQUEST_GET_COMMUNITIES_SORTIED_BY_NUMBER_OF_SUBSCRIBERS)
    @EntityGraph(value = GRAPH_NAME)
    List<Community> orderByCountBySubscribers(Pageable pageable);

    @Query(JPQL_REQUEST_GET_COMMUNITIES_BY_EMAIL)
    @EntityGraph(value = GRAPH_NAME)
    List<Community> getCommunitiesByEmail(String email, Pageable pageable);

    @Query(JPQL_REQUEST_GET_SUBSCRIBED_COMMUNITIES_BY_EMAIL)
    @EntityGraph(attributePaths = {
        Community_.AUTHOR, Community_.AUTHOR + SEPARATION_SYMBOL + UserProfile_.LOCATION, Community_.AUTHOR
            + SEPARATION_SYMBOL + UserProfile_.SCHOOL, Community_.AUTHOR + SEPARATION_SYMBOL + UserProfile_.UNIVERSITY})
    List<Community> getSubscribedCommunitiesByEmail(String email, Pageable pageable);

}
