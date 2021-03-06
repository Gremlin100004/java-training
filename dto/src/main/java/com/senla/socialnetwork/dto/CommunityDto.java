package com.senla.socialnetwork.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.senla.socialnetwork.model.enumaration.CommunityType;
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
@ApiModel(value = "Community")
public class CommunityDto extends GeneralDto {
    @ApiModelProperty(value = "Create community date",
        example = "2020-09-21 10:00")
    @Past
    @NotNull(message = "date must be specified")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private Date creationDate;
    @ApiModelProperty(value = "Community author")
    @NotNull(message = "title must be specified")
    private UserProfileForIdentificationDto author;
    @ApiModelProperty(value = "Type of community",
        example = "MEDICINE")
    @NotNull(message = "type must be selected")
    private CommunityType type;
    @ApiModelProperty(value = "Tittle of community",
        example = "COVID-19: Updates for the US")
    @NotBlank(message = "title must be specified")
    private String title;
    @ApiModelProperty(value = "Community information",
        example = "This page is a timeline of Tweets with the latest information and advice from the CDC, HHS, "
                  + "NIH and public health authorities across the country. For more, visit "
                  + "https://https://cdc.gov/coronavirus.")
    private String information;
    @ApiModelProperty(value = "Is community deleted",
        example = "false")
    private Boolean deleted;

}
