package com.senla.socialnetwork.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ApiModel(value = "Private Message For Create")
public class PrivateMessageForCreateDto {
    @ApiModelProperty(value = "User who is sending the message")
    @NotNull
    private UserProfileDto sender;
    @ApiModelProperty(value = "User to whom the message is addressed")
    @NotNull
    private UserProfileDto recipient;
    @ApiModelProperty(value = "Message content",
        example = "Hi, Bro!")
    @NotNull
    private String content;

}
