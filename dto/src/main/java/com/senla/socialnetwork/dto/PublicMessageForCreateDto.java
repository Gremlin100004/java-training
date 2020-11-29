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
@ApiModel(value = "Public Message For Create")
public class PublicMessageForCreateDto {
    @ApiModelProperty(value = "Tittle of public message",
        example = "Local restaurant in Cleveland is shocked when a customer leaves a $3,000 tip")
    private String tittle;
    @ApiModelProperty(value = "Content of community",
        example = "The customer placed the check on the table and told the owner he would see him when they reopen.\n"
                  + "(CNN)A local restaurant in Cleveland received a holiday surprise as it was voluntarily closing due"
                  + " to the increase of Covid-19 cases in Ohio.\nBrendon Ring, who owns staple local establishment "
                  + "Nighttown, told CNN that he was having lunch at his restaurant when a semi-regular customer came"
                  + " over and left his check at his table that included a $3,000 tip.")
    private String content;

}
