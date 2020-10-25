package com.senla.carservice.domain;

import com.senla.carservice.domain.enumaration.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@ToString(exclude = "masters")
@NoArgsConstructor
public class Order extends AEntity {
    @Column(name = "creation_time")
    private Date creationTime;
    @Column(name = "execution_start_time")
    private Date executionStartTime;
    @Column(name = "lead_time")
    private Date leadTime;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;
    @Column(name = "automaker")
    private String automaker;
    @Column(name = "model")
    private String model;
    @Column(name = "registration_number")
    private String registrationNumber;
    @Column(name = "price")
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status = OrderStatus.WAIT;
    @Column(name = "is_deleted")
    private boolean deleteStatus = false;
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "orders_masters", joinColumns = @JoinColumn(name = "order_id"),
        inverseJoinColumns = @JoinColumn(name = "master_id"))
    private List<Master> masters = new ArrayList<>();

}
