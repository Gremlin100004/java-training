package com.senla.socialnetwork.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "weather_conditions")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class WeatherCondition extends AEntity {
    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;
    @SuppressWarnings("checkstyle:MagicNumber")
    @Column(name = "status", length = 20)
    private String status;
    @Column(name = "registration_date")
    private Date registrationDate;

}
