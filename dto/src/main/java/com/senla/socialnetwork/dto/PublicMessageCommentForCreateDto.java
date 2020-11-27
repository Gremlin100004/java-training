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
@ApiModel(value = "Public Message Comment For Create")
public class PublicMessageCommentForCreateDto {
    @ApiModelProperty(value = "Comment author")
    @NotNull
    private UserProfileDto author;
    @ApiModelProperty(value = "Public message to which the comment belongs")
    @NotNull
    private PublicMessageDto publicMessage;
    @ApiModelProperty(value = "Bro why P40 doesn't have google?")
    @NotNull
    private String content;

}
