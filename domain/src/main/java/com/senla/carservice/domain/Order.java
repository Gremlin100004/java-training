package com.senla.carservice.domain;

import com.senla.carservice.domain.enumaration.Status;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
public class Order extends AEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "creation_time")
    private Date creationTime;
    @Column(name = "execution_start_time")
    private Date executionStartTime;
    @Column(name = "lead_time")
    private Date leadTime;
    @OneToOne
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
    @Column(name = "status")
    private Status status;
    @Column(name = "is_deleted")
    private boolean deleteStatus;
    @ManyToMany
    @JoinTable(
        name = "orders_masters",
        joinColumns = @JoinColumn(name = "order_id"),
        inverseJoinColumns = @JoinColumn(name = "master_id")
    )
    private List<Master> masters;

    public Order(String automaker, String model, String registrationNumber) {
        this.creationTime = new Date();
        this.automaker = automaker;
        this.model = model;
        this.registrationNumber = registrationNumber;
        this.status = Status.WAIT;
        this.deleteStatus = false;
        this.masters = new ArrayList<>();
    }


    public String getAutomaker() {
        return automaker;
    }

    public String getModel() {
        return model;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public Status getStatus() {
        return status;
    }

    public Place getPlace() {
        return place;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public Date getExecutionStartTime() {
        return executionStartTime;
    }

    public Date getLeadTime() {
        return leadTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public boolean isDeleteStatus() {
        return deleteStatus;
    }

    public List<Master> getMasters() {
        return masters;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public void setLeadTime(Date leadTime) {
        this.leadTime = leadTime;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public void setAutomaker(String automaker) {
        this.automaker = automaker;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public void setDeleteStatus(boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public void setExecutionStartTime(Date executionStartTime) {
        this.executionStartTime = executionStartTime;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setMasters(List<Master> masters) {
        this.masters = masters;
    }

    @Override
    public String toString() {
        return "Order{" + "creationTime=" + creationTime + ", executionStartTime=" + executionStartTime +
               ", leadTime=" + leadTime + ", place=" + place + ", automaker='" + automaker + '\'' + ", model='" +
               model + '\'' + ", registrationNumber='" + registrationNumber + '\'' + ", price=" + price + ", status=" +
               status + ", deleteStatus=" + deleteStatus + ", masters=" + masters + '}';
    }
}