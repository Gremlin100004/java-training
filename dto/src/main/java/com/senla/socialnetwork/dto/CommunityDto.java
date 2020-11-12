package com.senla.socialnetwork.dto;

import com.senla.socialnetwork.domain.enumaration.CommunityType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CommunityDto extends GeneralDto {
    private Date creationDate;
    private UserProfileDto author;
    private CommunityType type;
    private String tittle;
    private String information;
    private boolean isDeleted;

}
