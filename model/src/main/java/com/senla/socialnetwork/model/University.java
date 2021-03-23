package com.senla.socialnetwork.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "universities")
@Getter
@Setter
@ToString(exclude = {"location", "userProfiles"})
@NoArgsConstructor
public class University extends AEntity {
    @SuppressWarnings("checkstyle:MagicNumber")
    @Column(name = "name", nullable = false, unique = true, length = 80)
    private String name;
    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;
    @OneToMany(mappedBy = "university", cascade = CascadeType.REMOVE)
    private List<UserProfile> userProfiles;

}
