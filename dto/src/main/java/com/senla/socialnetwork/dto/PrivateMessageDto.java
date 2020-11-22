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
@ApiModel(value = "Private Message")
public class PrivateMessageDto extends GeneralDto {
    private Date departureDate;
    private UserProfileDto sender;
    private UserProfileDto recipient;
    private String content;
    private boolean isRead;
    private boolean isDeleted;

}
