package com.senla.carservice.domain;

import com.senla.carservice.domain.enumaration.NamePrivilege;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "privileges")
@Getter
@Setter
@ToString(exclude = "roles")
@NoArgsConstructor
public class Privilege extends AEntity {
    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false, unique = true)
    private NamePrivilege name;
    @ManyToMany(mappedBy = "privileges", fetch = FetchType.LAZY)
    private Set<Role> roles;

}
