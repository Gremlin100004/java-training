package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.domain.Community;
import com.senla.socialnetwork.dto.CommunityProjectionDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface CommunitySpringDataProjectionNativeQueryDao extends Repository<Community, Long> {
    String SQL_REQUEST_FIND_BY_ID_AND_EMAIL = "SELECT communities.id as communityId, communities.author_id as author, "
       + "communities.creation_date as creationDate, communities.type as type, communities.is_deleted as deleted, "
       + "communities.title as title, communities.information as information FROM communities INNER JOIN user_profiles "
       + "ON communities.author_id=user_profiles.id INNER JOIN users ON user_profiles.user_id=users.id WHERE "
       + "communities.id=?1 AND users.email=?2";
    String SQL_REQUEST_GET_COMMUNITIES = "SELECT communities.id as communityId, communities.author_id as author, "
       + "communities.creation_date as creationDate, communities.type as type, communities.is_deleted as deleted, "
       + "communities.title as title, communities.information as information FROM communities WHERE communities"
       + ".is_deleted=false";
    String SQL_REQUEST_GET_COMMUNITIES_BY_TYPE = "SELECT communities.id as communityId, communities.author_id as "
       + "author, communities.creation_date as creationDate, communities.type as type, communities.is_deleted as "
       + "deleted, communities.title as title, communities.information as information FROM communities INNER JOIN "
       + "user_profiles ON communities.author_id=user_profiles.id INNER JOIN locations ON user_profiles.location_id="
       + "locations.id INNER JOIN schools ON user_profiles.school_id=schools.id INNER JOIN universities ON "
       + "user_profiles.university_id=universities.id WHERE communities.is_deleted=false AND communities.type=?1";
    String SQL_REQUEST_GET_COMMUNITIES_SORTIED_BY_NUMBER_OF_SUBSCRIBERS = "SELECT communities.id as communityId, "
       + "communities.author_id as author, communities.creation_date as creationDate, communities.type as type, "
       + "communities.is_deleted as deleted, communities.title as title, communities.information as information FROM "
       + "communities INNER JOIN user_profiles ON communities.author_id=user_profiles.id WHERE communities.is_deleted="
       + "false ORDER BY (SELECT COUNT(DISTINCT community_user.users_id) FROM community_user WHERE community_user"
       + ".communities_id=communities.id)";
    String SQL_REQUEST_GET_COMMUNITIES_BY_EMAIL = "SELECT communities.id as communityId, communities.author_id as "
       + "author, communities.creation_date as creationDate, communities.type as type, communities.is_deleted as "
       + "deleted, communities.title as title, communities.information as information FROM communities INNER JOIN "
       + "user_profiles ON communities.author_id=user_profiles.id INNER JOIN users ON user_profiles.user_id=users.id "
       + "WHERE users.email=?1";
    String SQL_REQUEST_GET_SUBSCRIBED_COMMUNITIES_BY_EMAIL = "SELECT communities.id as communityId, communities"
       + ".author_id as author, communities.creation_date as creationDate, communities.type as type, communities"
       + ".is_deleted as deleted, communities.title as title, communities.information as information FROM communities "
       + "INNER JOIN user_profiles ON communities.author_id=user_profiles.id INNER JOIN community_user ON communities"
       + ".id=community_user.communities_id WHERE community_user.users_id=(SELECT user_profiles.id FROM user_profiles "
       + "INNER JOIN users ON user_profiles.user_id=users.id WHERE users.email=?1)";
    String SQL_FIND_ALL = "SELECT communities.id as communityId, communities.author_id as author, communities"
       + ".creation_date as creationDate, communities.type as type, communities.is_deleted as deleted, communities"
       + ".title as title, communities.information as information FROM communities ";
    String SQL_FIND_BY_ID = "SELECT communities.id as communityId, communities.author_id as author, communities"
       + ".creation_date as creationDate, communities.type as type, communities.is_deleted as deleted, communities"
       + ".title as title, communities.information as information FROM communities WHERE communities.id=?1";

    @Query(value = SQL_REQUEST_FIND_BY_ID_AND_EMAIL, nativeQuery = true)
    Optional<CommunityProjectionDto> findByIdAndEmail(Long id, String Email);

    @Query(value = SQL_REQUEST_GET_COMMUNITIES, nativeQuery = true)
    List<CommunityProjectionDto> findByIsDeletedFalse(Pageable pageable);

    @Query(value = SQL_REQUEST_GET_COMMUNITIES_BY_TYPE, nativeQuery = true)
    List<CommunityProjectionDto> getCommunitiesByType(String communityType, Pageable pageable);

    @Query(value = SQL_REQUEST_GET_COMMUNITIES_SORTIED_BY_NUMBER_OF_SUBSCRIBERS, nativeQuery = true)
    List<CommunityProjectionDto> getCommunitiesSortiedByNumberOfSubscribers(Pageable pageable);

    @Query(value = SQL_REQUEST_GET_COMMUNITIES_BY_EMAIL, nativeQuery = true)
    List<CommunityProjectionDto> getCommunitiesByEmail(String email, Pageable pageable);

    @Query(value = SQL_REQUEST_GET_SUBSCRIBED_COMMUNITIES_BY_EMAIL, nativeQuery = true)
    List<CommunityProjectionDto> getSubscribedCommunitiesByEmail(String email, Pageable pageable);

    @Query(value = SQL_FIND_ALL, nativeQuery = true)
    List<CommunityProjectionDto> findAll(Pageable pageable);

    @Query(value = SQL_FIND_BY_ID, nativeQuery = true)
    Optional<CommunityProjectionDto> findById(Long id);

}
