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
@ApiModel(value = "Public Message")
public class PublicMessageDto extends GeneralDto {
    private Date creationDate;
    private UserProfileDto author;
    private String tittle;
    private String content;
    private boolean isDeleted;

}
