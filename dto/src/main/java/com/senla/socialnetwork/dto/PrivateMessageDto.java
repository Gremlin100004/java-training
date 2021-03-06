package com.senla.socialnetwork.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@ApiModel(value = "Private Message")
public class PrivateMessageDto extends GeneralDto {
    @ApiModelProperty(value = "Departure date of message",
        example = "2020-11-11 12:00")
    @Past
    @NotNull(message = "date must be specified")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private Date departureDate;
    @ApiModelProperty(value = "User who is sending the message")
    @NotNull(message = "user must be specified")
    private UserProfileForIdentificationDto sender;
    @ApiModelProperty(value = "User to whom the message is addressed")
    @NotNull(message = "user must be specified")
    private UserProfileForIdentificationDto recipient;
    @ApiModelProperty(value = "Message content",
        example = "Your great aunt just passed away. LOL")
    @NotBlank(message = "content must be specified")
    private String content;
    @ApiModelProperty(value = "Is message read",
        example = "false")
    private Boolean read;
    @ApiModelProperty(value = "Is message deleted",
        example = "false")
    private Boolean deleted;

}
