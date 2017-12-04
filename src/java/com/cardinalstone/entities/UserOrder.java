/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cardinalstone.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author NiyiO
 */
@Entity
public class UserOrder implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private UserEntity userEntity;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date orderDate;

    private String deliveryAddress;

    private Boolean delivered;

    public Long getId() {
        return id;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public Boolean getDelivered() {
        return delivered;
    }

    public void setDelivered(Boolean delivered) {
        this.delivered = delivered;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.id);
        hash = 59 * hash + Objects.hashCode(this.userEntity);
        hash = 59 * hash + Objects.hashCode(this.orderDate);
        hash = 59 * hash + Objects.hashCode(this.deliveryAddress);
        hash = 59 * hash + Objects.hashCode(this.delivered);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UserOrder other = (UserOrder) obj;
        if (!Objects.equals(this.deliveryAddress, other.deliveryAddress)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.userEntity, other.userEntity)) {
            return false;
        }
        if (!Objects.equals(this.orderDate, other.orderDate)) {
            return false;
        }
        return Objects.equals(this.delivered, other.delivered);
    }

//    @Override
//    public String toString() {
//        return "Order{" + "orderDate=" + orderDate + ", deliveryAddress=" + deliveryAddress + ", delivered=" + delivered + '}';
//    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("orderDate", orderDate.toString())
                .add("deliveryAddress", deliveryAddress)
                .build();
    }

}
