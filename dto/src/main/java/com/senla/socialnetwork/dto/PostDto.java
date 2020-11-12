package com.senla.socialnetwork.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PostDto extends GeneralDto {
    private Date creationDate;
    private String tittle;
    private String content;
    private CommunityDto community;
    private boolean isDeleted;

}
