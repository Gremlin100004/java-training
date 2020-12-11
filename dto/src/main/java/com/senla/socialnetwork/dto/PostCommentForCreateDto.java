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
@ApiModel(value = "Post Comment For Create")
public class PostCommentForCreateDto {
    @ApiModelProperty(value = "Comment content",
        example = "Let's go!")
    @NotNull(message = "content must be specified")
    private String content;

}
