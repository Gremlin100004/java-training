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
@ApiModel(value = "Public Message")
public class PublicMessageDto extends GeneralDto {
    @ApiModelProperty(value = "Create public message date",
        example = "2020-07-21 10:00")
    @Past
    @NotNull(message = "date must be specified")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private Date creationDate;
    @ApiModelProperty(value = "Public message author")
    @NotNull(message = "user must be specified")
    private UserProfileForIdentificationDto author;
    @ApiModelProperty(value = "Title of public message",
        example = "I deleted everything.")
    private String title;
    @ApiModelProperty(value = "Content of community",
        example = "I deleted everything. I’m done. For those who wanted me to “address it” I did. I’m sure u can find"
                  + " it reposted somewhere. But I don’t want this energy in my life or on my timeline. I’m too "
                  + "sensitive for this shit and I’m done.")
    private String content;
    @ApiModelProperty(value = "Is public message deleted",
        example = "false")
    private Boolean deleted;

}
