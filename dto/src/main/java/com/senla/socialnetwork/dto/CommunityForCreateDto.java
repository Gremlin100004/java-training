package com.senla.socialnetwork.dto;

import com.senla.socialnetwork.domain.enumaration.CommunityType;
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
@ApiModel(value = "Community for create")
public class CommunityForCreateDto {
    @ApiModelProperty(value = "Community author")
    @NotNull
    private UserProfileDto author;
    @ApiModelProperty(value = "Type of community")
    @NotNull
    private CommunityType type;
    @ApiModelProperty(value = "Tittle of community",
        example = "Huawei Europe")
    @NotNull
    private String tittle;
    @ApiModelProperty(value = "Community information",
        example = "This is the official site of Huawei Europe, communicating the cultivation of digital change, " +
                  "affecting people & business.")
    private String information;

}
