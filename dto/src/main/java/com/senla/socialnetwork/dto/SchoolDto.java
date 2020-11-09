package com.senla.socialnetwork.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SchoolDto extends GeneralDto {
    private String name;
    private LocationDto location;

}
