package com.senla.socialnetwork.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "locations")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Location extends AEntity {
    @Column(name = "country", nullable = false)
    private String country;
    @Column(name = "city", nullable = false, unique = true)
    private String city;

}
