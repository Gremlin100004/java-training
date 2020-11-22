package com.senla.socialnetwork.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ApiModel(value = "Public Message Comment")
public class PublicMessageCommentDto extends GeneralDto {
    private Date creationDate;
    private UserProfileDto author;
    private PublicMessageDto publicMessage;
    private String content;
    private boolean isDeleted;

}
