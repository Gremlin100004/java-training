package com.senla.socialnetwork.dao.springdata;

import com.senla.socialnetwork.model.Community;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommunitySpringDataNativeQueryDao extends JpaRepository<Community, Long> {
    String SQL_REQUEST_FIND_BY_ID_AND_EMAIL = "SELECT * FROM communities c INNER JOIN user_profiles up ON c.author_id "
       + "INNER JOIN users u ON up.user_id LEFT OUTER JOIN universities ON up.university_id LEFT OUTER JOIN"
       + " schools ON up.school_id LEFT OUTER JOIN locations ON up.location_id WHERE c.id=?1 AND u.email=?2";
    String SQL_REQUEST_GET_COMMUNITIES_BY_TYPE = "SELECT * FROM communities c WHERE c.is_deleted=false AND c.type=?1";
    String SQL_REQUEST_GET_COMMUNITIES_SORTIED_BY_NUMBER_OF_SUBSCRIBERS = "SELECT com.id, up.user_id, loc.id, sch.id, "
       + "un.id, com.author_id, com.creation_date, com.information, com.is_deleted, com.title, com.type, "
       + "up.date_of_birth, up.location_id, up.name, up.registration_date, up.school_id, up.school_graduation_year, "
       + "up.surname, up.telephone_number, up.university_id, up.university_graduation_year, loc.city, loc.country, "
       + "sch.location_id, sch.name, un.location_id, un.name, FROM communities com LEFT OUTER JOIN user_profiles up ON "
       + "com.author_id=up.user_id LEFT OUTER JOIN locations loc ON up.location_id=loc.id LEFT OUTER JOIN schools sch "
       + "ON up.school_id=sch.id LEFT OUTER JOIN universities un ON up.university_id=un.id WHERE com.is_deleted=false "
       + "ORDER BY (select count(u.communities_id) FROM community_user u WHERE com.id = u.communities_id) DESC";
    String SQL_REQUEST_GET_COMMUNITIES_BY_EMAIL = "SELECT com.id, up.user_id, un.id, sch.id, loc.id, com.author_id, "
      + "com.creation_date, com.information, com.is_deleted, com.title, com.type, up.date_of_birth, up.location_id, "
      + "up.name, up.registration_date, up.school_id, up.school_graduation_year, up.surname, up.telephone_number, "
      + "up.university_id, up.university_graduation_year, un.location_id, un.name, sch.location_id, sch.name, "
      + "loc.city, loc.country, FROM communities com INNER JOIN user_profiles up ON com.author_id=up.user_id INNER JOIN"
      + " users u ON up.user_id=u.id LEFT OUTER JOIN universities un ON up.university_id=un.id LEFT OUTER JOIN schools "
      + "sch ON up.school_id=sch.id LEFT OUTER JOIN locations loc ON up.location_id=loc.id WHERE u.email=?1";
    String SQL_REQUEST_GET_SUBSCRIBED_COMMUNITIES_BY_EMAIL = "SELECT com.id, up.user_id, un.id, sch.id, loc.id, " 
      + "com.author_id, com.creation_date, com.information, com.is_deleted, com.title, com.type, up.date_of_birth," 
      + " up.location_id, up.name, up.registration_date, up.school_id, up.school_graduation_year, up.surname, " 
      + "up.telephone_number, up.university_id, up.university_graduation_year, un.location_id, un.name, sch.location_id," 
      + " sch.name, loc.city, loc.country, from communities com LEFT OUTER JOIN user_profiles up ON " 
      + "com.author_id=up.user_id LEFT OUTER JOIN universities un ON up.university_id=un.id LEFT OUTER JOIN schools sch "
      + "ON up.school_id=sch.id LEFT OUTER JOIN locations loc ON up.location_id=loc.id WHERE com.id IN (SELECT "
      + "com_sub.id FROM user_profiles up_sub INNER JOIN community_user cu ON up_sub.user_id=cu.user_profiles_id "
      + "INNER JOIN communities com_sub ON cu.communities_id=com_sub.id INNER JOIN users u ON up_sub.user_id=u.id "
      + "INNER JOIN community_user com_u ON up_sub.user_id=com_u.user_profiles_id INNER JOIN communities com_u_com "
      + "ON com_u.communities_id=com_u_com.id WHERE u.email=?1 and com_u_com.is_deleted=false)";

    @Query(value = SQL_REQUEST_FIND_BY_ID_AND_EMAIL, nativeQuery = true)
    Optional <Community> findByIdAndEmail(Long id, String email);

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
