package com.senla.socialnetwork.dao.springdata;

import com.senla.socialnetwork.dto.CommunityProjectionDto;
import com.senla.socialnetwork.model.Community;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommunitySpringDataProjectionNativeQueryDao extends JpaRepository<Community, Long> {
    String SQL_REQUEST_FIND_BY_ID_AND_EMAIL = "SELECT com.id AS communityId, com.author_id AS author, com.creation_date"
       + " AS creationDate, com.information AS information, com.is_deleted AS deleted, com.title AS title, com.type AS"
       + " type, up.name AS name, up.surname AS surname, FROM communities com INNER JOIN user_profiles up ON "
       + "com.author_id=up.user_id INNER JOIN users u ON up.user_id=u.id WHERE u.email=?2 AND com.id=?1";
    String SQL_REQUEST_GET_COMMUNITIES = "SELECT communities.id as communityId, communities.author_id as author, "
       + "communities.creation_date as creationDate, communities.type as type, communities.is_deleted as deleted, "
       + "communities.title as title, communities.information as information FROM communities WHERE communities"
       + ".is_deleted=false";
    String SQL_REQUEST_GET_COMMUNITIES_BY_TYPE = "SELECT com.id AS communityId, com.author_id AS author, "
       + "com.creation_date AS creationDate, com.information AS information, com.is_deleted AS deleted, com.title AS "
       + "title, com.type AS type, up.name AS name, up.surname AS surname, FROM communities com LEFT OUTER JOIN "
       + "user_profiles up ON com.author_id=up.user_id WHERE com.type=?1";
    String SQL_REQUEST_GET_COMMUNITIES_SORTIED_BY_NUMBER_OF_SUBSCRIBERS = "SELECT com.id AS communityId, com.author_id "
       + "AS author, com.creation_date AS creationDate, com.information AS information, com.is_deleted AS deleted, "
       + "com.title AS title, com.type AS type, up.name AS name, up.surname AS surname, FROM communities com LEFT OUTER"
       + " JOIN user_profiles up ON com.author_id=up.user_id WHERE com.is_deleted=false ORDER BY (SELECT COUNT("
       + "subscriber5_.communities_id) FROM community_user subscriber5_ WHERE com.id = subscriber5_.communities_id) DESC";
    String SQL_REQUEST_GET_COMMUNITIES_BY_EMAIL = "SELECT com.id AS communityId, com.author_id AS author, "
       + "com.creation_date AS creationDate, com.information AS information, com.is_deleted AS deleted, com.title AS "
       + "title, com.type AS type, up.name AS name, up.surname AS surname, FROM communities com INNER JOIN user_profiles"
       + " up ON com.author_id=up.user_id INNER JOIN users u ON up.user_id=u.id WHERE u.email=?1";
    String SQL_REQUEST_GET_SUBSCRIBED_COMMUNITIES_BY_EMAIL = "SELECT com.id AS communityId, com.author_id AS author, "
       + "com.creation_date AS creationDate, com.information AS information, com.is_deleted AS deleted, com.title AS "
       + "title, com.type AS type, up.name AS name, up.surname AS surname, FROM communities com LEFT OUTER JOIN "
       + "user_profiles up ON com.author_id=up.user_id WHERE com.id in (SELECT com_sub.id FROM user_profiles up_sub "
       + "INNER JOIN community_user cu ON up_sub.user_id=cu.user_profiles_id INNER JOIN communities com_sub ON "
       + "cu.communities_id=com_sub.id INNER JOIN users u ON up_sub.user_id=u.id INNER JOIN community_user cu_sub "
       + "ON up_sub.user_id=cu_sub.user_profiles_id INNER JOIN communities cu_com ON cu_sub.communities_id=cu_com.id "
       + "WHERE u.email=?1 AND cu_com.is_deleted=false)";
    String SQL_FIND_ALL = "SELECT communities.id as communityId, communities.author_id as author, communities"
       + ".creation_date as creationDate, communities.type as type, communities.is_deleted as deleted, communities"
       + ".title as title, communities.information as information FROM communities ";
    String SQL_FIND_BY_ID = "SELECT communities.id as communityId, communities.author_id as author, communities"
       + ".creation_date as creationDate, communities.type as type, communities.is_deleted as deleted, communities"
       + ".title as title, communities.information as information FROM communities WHERE communities.id=?1";

    @Query(value = SQL_REQUEST_FIND_BY_ID_AND_EMAIL, nativeQuery = true)
    Optional<CommunityProjectionDto> findByIdAndEmail(Long id, String email);

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
    List<CommunityProjectionDto> getAll(Pageable pageable);

    @Query(value = SQL_FIND_BY_ID, nativeQuery = true)
    Optional<CommunityProjectionDto> getById(Long id);

}
