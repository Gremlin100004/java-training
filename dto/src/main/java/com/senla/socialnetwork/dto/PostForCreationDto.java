package com.senla.socialnetwork.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ApiModel(value = "Post For Create")
public class PostForCreationDto {
    @ApiModelProperty(value = "Tittle of post",
        example = "Join to us")
    private String title;
    @ApiModelProperty(value = "Content of post",
        example = "Join us to hear from Huawei Corporate Senior Vice President Catherine Chen, on how gender "
                  + "balance is crucial for building a diverse and inclusive digital economy, and education is a key"
                  + " to empower women in the tech industry.")
    private String content;

}
