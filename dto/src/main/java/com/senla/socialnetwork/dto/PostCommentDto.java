package com.senla.socialnetwork.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ApiModel(value = "Post Comment")
public class PostCommentDto extends GeneralDto {
    @ApiModelProperty(value = "Create community date",
        example = "2020-10-02 09:23")
    @Past
    @NotNull(message = "date must be specified")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private Date creationDate;
    @ApiModelProperty(value = "Comment author")
    @NotNull(message = "user must be specified")
    private UserProfileForIdentificationDto author;
    @ApiModelProperty(value = "Community post")
    @NotNull(message = "post must be specified")
    private PostDto post;
    @ApiModelProperty(value = "Comment content",
        example = "Do lockdowns, we need them.")
    @NotBlank(message = "content must be specified")
    private String content;
    @ApiModelProperty(value = "Is post comment deleted",
        example = "false")
    private Boolean deleted;

}
