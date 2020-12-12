package com.senla.socialnetwork.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ApiModel(value = "Public Message Comment For Create")
public class PublicMessageCommentForCreateDto {
    @ApiModelProperty(value = "Comment content",
        example = "Bro why P40 doesn't have google?")
    @NotBlank(message = "content must be specified")
    private String content;

}
