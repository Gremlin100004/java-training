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
@ApiModel(value = "Public Message")
public class PublicMessageDto extends GeneralDto {
    @ApiModelProperty(value = "Create public message date",
        example = "2020-09-21 10:00")
    @Past
    @NotNull
    private Date creationDate;
    @ApiModelProperty(value = "Public message author")
    @NotNull
    private UserProfileDto author;
    @ApiModelProperty(value = "Tittle of public message",
        example = "One has only to add a little perseverance and believe in yourself, as success immediately "
                  + "becomes on your side!")
    @NotNull
    private String tittle;
    @ApiModelProperty(value = "Content of community",
        example = "I mean that you need to constantly work on yourself")
    private String content;
    private boolean isDeleted;

}
