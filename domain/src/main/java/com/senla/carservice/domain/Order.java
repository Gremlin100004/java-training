package com.senla.carservice.domain;

import com.senla.carservice.domain.enumaration.StatusOrder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
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
    private StatusOrder status;
    @Column(name = "is_deleted")
    private boolean deleteStatus;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(
        name = "orders_masters",
        joinColumns = @JoinColumn(name = "order_id"),
        inverseJoinColumns = @JoinColumn(name = "master_id")
    )
    private List<Master> masters = new ArrayList<>();

    public Order() {
    }

    public Order(String automaker, String model, String registrationNumber) {
        this.creationTime = new Date();
        this.executionStartTime = new Date();
        this.leadTime = new Date();
        this.automaker = automaker;
        this.model = model;
        this.registrationNumber = registrationNumber;
        this.status = StatusOrder.WAIT;
        this.deleteStatus = false;
        this.place = new Place();
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(final Date creationTime) {
        this.creationTime = creationTime;
    }

    public Date getExecutionStartTime() {
        return executionStartTime;
    }

    public void setExecutionStartTime(final Date executionStartTime) {
        this.executionStartTime = executionStartTime;
    }

    public Date getLeadTime() {
        return leadTime;
    }

    public void setLeadTime(final Date leadTime) {
        this.leadTime = leadTime;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(final Place place) {
        this.place = place;
    }

    public String getAutomaker() {
        return automaker;
    }

    public void setAutomaker(final String automaker) {
        this.automaker = automaker;
    }

    public String getModel() {
        return model;
    }

    public void setModel(final String model) {
        this.model = model;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(final String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(final BigDecimal price) {
        this.price = price;
    }

    public StatusOrder getStatus() {
        return status;
    }

    public void setStatus(StatusOrder status) {
        this.status = status;
    }

    public boolean isDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public List<Master> getMasters() {
        return masters;
    }

    public void setMasters(final List<Master> masters) {
        this.masters = masters;
    }

    @Override
    public String toString() {
        return "Order{" + "id=" + id + ", creationTime=" + creationTime + ", executionStartTime=" + executionStartTime +
               ", leadTime=" + leadTime + ", automaker='" + automaker + '\'' + ", model='" + model + '\'' +
               ", registrationNumber='" + registrationNumber + '\'' + ", price=" + price + ", status=" + status +
               ", deleteStatus=" + deleteStatus + '}';
    }
}