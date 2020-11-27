package com.senla.socialnetwork.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ApiModel(value = "Post")
public class PostDto extends GeneralDto {
    @ApiModelProperty(value = "Create post date",
        example = "2020-09-21 10:00")
    @Past
    @NotNull
    private Date creationDate;
    @ApiModelProperty(value = "Tittle of post",
        example = "Night Hockey League Supports Support for the Women's Amateur League")
    private String tittle;
    @ApiModelProperty(value = "Content of post",
        example = "It is planned that the new season of the Women's Hockey League will start in early 2021, the "
                  + "final will be held in May as part of the Night Hockey League festival in Sochi, where the "
                  + "athletes will compete for the main women's amateur hockey trophy in the Amazon division.")
    private String content;
    @ApiModelProperty(value = "Community")
    @NotNull
    private CommunityDto community;
    private boolean isDeleted;

}
