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
    @ApiModelProperty(value = "Comment author")
    @NotNull
    private UserProfileDto author;
    @ApiModelProperty(value = "Community post")
    @NotNull
    private PostDto post;
    @ApiModelProperty(value = "Comment content",
        example = "Let's go!")
    @NotNull
    private String content;

}
