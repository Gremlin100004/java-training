package com.senla.socialnetwork.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Client Message")
public class ClientMessageDto {
    @ApiModelProperty(value = "Return message from server, may be an error message",
        example = "Subscription to community was successful; Error, the post has already been deleted")
    private String message;

}
