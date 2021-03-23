package com.senla.socialnetwork.dto;

import com.senla.socialnetwork.model.enumaration.CommunityType;

import java.util.Date;

public interface CommunityProjectionDto {

    Long getCommunityId();

    void setCommunityId(Long communityId);

    Date getCreationDate();

    void setCreationDate(Date creationDate);

    UserProfileProjectionDto getAuthor();

    void setAuthor(UserProfileProjectionDto author);

    CommunityType getType();

    void setType(CommunityType type);

    String getTitle();

    void setTitle(String title);

    String getInformation();

    void setInformation(String information);

    Boolean getDeleted();

    void setDeleted(Boolean deleted);

}
