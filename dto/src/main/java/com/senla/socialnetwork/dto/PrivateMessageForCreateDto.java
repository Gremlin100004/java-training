package com.senla.socialnetwork.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ApiModel(value = "Private Message For Create")
public class PrivateMessageForCreateDto {
    @ApiModelProperty(value = "User to whom the message is addressed")
    @NotNull(message = "user must be specified")
    private UserProfileForIdentificationDto recipient;
    @ApiModelProperty(value = "Message content",
        example = "Hi, Bro!")
    @NotBlank(message = "content must be specified")
    private String content;

}
