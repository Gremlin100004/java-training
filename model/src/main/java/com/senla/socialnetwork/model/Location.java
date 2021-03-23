package com.senla.socialnetwork.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "locations")
@Getter
@Setter
@NoArgsConstructor
public class Location extends AEntity {
    @SuppressWarnings("checkstyle:MagicNumber")
    @Column(name = "country", nullable = false, length = 45)
    private String country;
    @SuppressWarnings("checkstyle:MagicNumber")
    @Column(name = "city", nullable = false, unique = true, length = 45)
    private String city;
    @OneToMany(mappedBy = "location")
    private List<School> schools;
    @OneToMany(mappedBy = "location")
    private List<University> universities;
    @OneToMany(mappedBy = "location")
    private List<WeatherCondition> weatherConditions;
    @OneToMany(mappedBy = "location")
    private List<UserProfile> userProfiles;

    @Override
    public String toString() {
        return "Location{"
               + "country='" + country + '\''
               + ", city='" + city + '\''
               + ", id=" + id
               + '}';
    }

}
