package com.senla.carservice.domain;

import com.senla.carservice.domain.enumaration.NameRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Role extends AEntity {
    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false, unique = true)
    private NameRole name;

}
