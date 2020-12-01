package com.senla.socialnetwork.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "weather_conditions")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class WeatherCondition extends AEntity {
    @OneToOne
    @JoinColumn(name = "location_id")
    private Location location;
    @Column(name = "status")
    private String status;
    @Column(name = "registration_date")
    private Date registrationDate;

}
