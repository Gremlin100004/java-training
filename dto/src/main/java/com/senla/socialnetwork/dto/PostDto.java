package com.senla.socialnetwork.dto;

import com.senla.socialnetwork.domain.PostComment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

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
    private List<PostComment> postComments;

}
