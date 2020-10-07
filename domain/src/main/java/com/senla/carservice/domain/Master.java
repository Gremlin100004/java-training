package com.senla.carservice.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "masters")
@Getter
@Setter
@ToString(exclude = "orders")
@NoArgsConstructor
public class Master extends AEntity {

    @Column(name = "name")
    private String name;
    @ManyToMany(mappedBy = "masters", fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();
    @Column(name = "number_orders")
    private Integer numberOrders = 0;
    @Column(name = "is_deleted")
    private Boolean deleteStatus = false;

}
