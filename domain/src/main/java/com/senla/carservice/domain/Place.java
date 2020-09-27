package com.senla.carservice.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "places")
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
public class Place extends AEntity {

    @Column(name = "number")
    @NonNull
    private Integer number;
    @Column(name = "is_busy")
    private Boolean isBusy = false;
    @Column(name = "is_deleted")
    private Boolean deleteStatus = false;
}