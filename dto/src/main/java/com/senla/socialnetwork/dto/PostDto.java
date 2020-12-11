package com.senla.socialnetwork.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
        example = "2020-10-01 20:11")
    @Past
    @NotNull(message = "date must be specified")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private Date creationDate;
    @ApiModelProperty(value = "Title of post",
        example = "El Paso County sees drop in COVID-19 hospitalizations")
    private String title;
    @ApiModelProperty(value = "Content of post",
        example = "COVID hospitalizations in El Paso have been going down for about 2 weeks now. The number of people "
                  + "in El Paso testing positive for COVID has been declining also. The positivity rate for all of "
                  + "Texas has declined for 14 consecutive days.")
    private String content;
    @ApiModelProperty(value = "Community")
    @NotNull(message = "community must be specified")
    private CommunityDto community;
    @ApiModelProperty(value = "Is post deleted",
        example = "false")
    private Boolean deleted;

}
