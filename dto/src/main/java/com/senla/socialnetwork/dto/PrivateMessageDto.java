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
public class PrivateMessageDto extends GeneralDto {
    private Date departureDate;
    private UserProfileDto sender;
    private UserProfileDto recipient;
    private String content;
    private boolean isRead;
    private boolean isDeleted;

}
