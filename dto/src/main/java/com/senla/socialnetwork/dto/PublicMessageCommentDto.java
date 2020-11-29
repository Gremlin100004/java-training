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
@ApiModel(value = "Public Message Comment")
public class PublicMessageCommentDto extends GeneralDto {
    @ApiModelProperty(value = "Create comment date",
        example = "2020-07-21 11:23")
    @Past
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private Date creationDate;
    @ApiModelProperty(value = "Comment author")
    @NotNull
    private UserProfileForIdentificationDto author;
    @ApiModelProperty(value = "Public message to which the comment belongs")
    @NotNull
    private PublicMessageDto publicMessage;
    @ApiModelProperty(value = "Comment content",
        example = "I know I’m a couple months late but everyone saying Shane the same person he was when he started "
                  + "YouTube, can you see his transformed into this caring youtuber, yeah he made funny sketches "
                  + "which may offend some people but at the time he was posting it to make people laugh")
    @NotNull
    private String content;
    @ApiModelProperty(value = "Is comment deleted",
        example = "false")
    private boolean deleted;

}
