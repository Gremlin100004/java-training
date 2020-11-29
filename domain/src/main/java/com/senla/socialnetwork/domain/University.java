package com.senla.socialnetwork.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "universities")
@Getter
@Setter
@ToString(exclude = "location")
@NoArgsConstructor
public class University extends AEntity {
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

}
