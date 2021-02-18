package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.domain.Community;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
//Todo join tables to solve problem N + 1
public interface CommunitySpringDataNativeQueryDao extends JpaRepository<Community, Long> {
    String SQL_REQUEST_FIND_BY_ID_AND_EMAIL = "SELECT communities.id, communities.author_id, communities.creation_date, "
       + "communities.type, communities.is_deleted, communities.title, communities.information, user_profiles.id, "
       + "user_profiles.name, user_profiles.surname FROM communities INNER JOIN user_profiles ON communities"
       + ".author_id=user_profiles.id INNER JOIN users ON user_profiles.user_id=users.id WHERE communities.id=?1 "
       + "AND users.email=?2";
    String SQL_REQUEST_GET_COMMUNITIES_BY_TYPE = "SELECT * FROM communities INNER JOIN user_profiles ON communities"
       + ".author_id=user_profiles.id INNER JOIN locations ON user_profiles.location_id=locations.id INNER JOIN schools"
       + " ON user_profiles.school_id=schools.id INNER JOIN universities ON user_profiles.university_id=universities.id"
       + " WHERE communities.is_deleted=false AND communities.type=?1";
    String SQL_REQUEST_GET_COMMUNITIES_SORTIED_BY_NUMBER_OF_SUBSCRIBERS = "SELECT communities.id, "
       + "communities.creation_date, communities.type, communities.is_deleted, communities.title, communities.author_id,"
       + " communities.information, user_profiles.id, user_profiles.name, user_profiles.surname FROM communities INNER"
       + " JOIN user_profiles ON communities.author_id=user_profiles.id WHERE communities.is_deleted=false ORDER BY "
       + "(SELECT COUNT(DISTINCT community_user.users_id) FROM community_user WHERE community_user"
       + ".communities_id=communities.id)";
    String SQL_REQUEST_GET_COMMUNITIES_BY_EMAIL = "SELECT communities.id, communities.creation_date, "
       + "communities.author_id, communities.type, communities.is_deleted, communities.title, communities.information, "
       + "user_profiles.id, user_profiles.name, user_profiles.surname FROM communities INNER JOIN user_profiles ON "
       + "communities.author_id=user_profiles.id INNER JOIN users ON user_profiles.user_id=users.id WHERE users.email=?1";
    String SQL_REQUEST_GET_SUBSCRIBED_COMMUNITIES_BY_EMAIL = "SELECT communities.id, communities.creation_date, "
       + "communities.author_id, communities.type, communities.is_deleted, communities.title, communities.information, "
       + "user_profiles.id, user_profiles.name, user_profiles.surname FROM communities INNER JOIN user_profiles ON "
       + "communities.author_id=user_profiles.id INNER JOIN community_user ON communities.id=community_user"
       + ".communities_id WHERE community_user.users_id=(SELECT user_profiles.id FROM user_profiles INNER JOIN users ON"
       + " user_profiles.user_id=users.id WHERE users.email=?1)";

    @Query(value = SQL_REQUEST_FIND_BY_ID_AND_EMAIL, nativeQuery = true)
    Optional<Community> findByIdAndEmail(Long id, String Email);

    @EntityGraph(value = "graph.Community")
    List<Community> findByIsDeletedFalse(Pageable pageable);

    @Query(value = SQL_REQUEST_GET_COMMUNITIES_BY_TYPE, nativeQuery = true)
    List<Community> getCommunitiesByType(String communityType, Pageable pageable);

    @Query(value = SQL_REQUEST_GET_COMMUNITIES_SORTIED_BY_NUMBER_OF_SUBSCRIBERS, nativeQuery = true)
    List<Community> getCommunitiesSortiedByNumberOfSubscribers(Pageable pageable);

    @Query(value = SQL_REQUEST_GET_COMMUNITIES_BY_EMAIL, nativeQuery = true)
    List<Community> getCommunitiesByEmail(String email, Pageable pageable);

    @Query(value = SQL_REQUEST_GET_SUBSCRIBED_COMMUNITIES_BY_EMAIL, nativeQuery = true)
    List<Community> getSubscribedCommunitiesByEmail(String email, Pageable pageable);

}
