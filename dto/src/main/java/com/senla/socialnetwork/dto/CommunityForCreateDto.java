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
@ApiModel(value = "Community For Create")
public class CommunityForCreateDto {
    @ApiModelProperty(value = "Type of community")
    @NotNull(message = "type must be selected")
    private CommunityType type;
    @ApiModelProperty(value = "Title of community",
        example = "Huawei Europe")
    @NotNull(message = "title must be specified")
    private String title;
    @ApiModelProperty(value = "Community information",
        example = "This is the official site of Huawei Europe, communicating the cultivation of digital change, "
                  + "affecting people & business.")
    private String information;

}
