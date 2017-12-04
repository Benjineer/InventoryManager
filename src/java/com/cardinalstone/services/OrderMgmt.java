/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cardinalstone.services;

import com.cardinalstone.entities.UserEntity;
import com.cardinalstone.entities.UserOrder;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Response;

/**
 *
 * @author NiyiO
 */
public class OrderMgmt {

    @PersistenceContext
    private EntityManager em;

    public Response makeOrder(String token, JsonObject jsonOrder) {

        if (Objects.isNull(token) || token.isEmpty()) {

            return Response.ok("Token is empty").build();

        }

        if (Objects.isNull(jsonOrder) || !jsonOrder.containsKey("deliveryAddress")) {

            return Response.ok("Request Body is empty or missing critical keys").build();

        }

        //Probably have to do annotated security or cache.
        List<UserEntity> userEntities = em.createQuery("SELECT u FROM UserEntity u WHERE u.token =:token", UserEntity.class).setParameter("token", token).getResultList();

        if (userEntities.isEmpty()) {
            return Response.ok("Either no such user exists, or token has expired or user logged out").build();

        }

        String deliveryAddress = jsonOrder.getString("deliveryAddress");

        UserOrder userOrder = new UserOrder();
        userOrder.setDelivered(Boolean.FALSE);
        userOrder.setDeliveryAddress(deliveryAddress);
        userOrder.setOrderDate(Date.from(Instant.now()));
        userOrder.setUserEntity(userEntities.get(0));

        em.persist(userOrder);

        return Response.ok(userOrder.toJson()).build();

    }

    public Response fetchOrder(String token, Long id) {

        if (Objects.isNull(token) || token.isEmpty()) {

            return Response.ok("Token is empty").build();

        }

        if (Objects.isNull(id)) {

            return Response.ok("Id is empty").build();

        }

        //Probably have to do annotated security or cache.
        List<UserEntity> userEntities = em.createQuery("SELECT u FROM UserEntity u WHERE u.token =:token", UserEntity.class).setParameter("token", token).getResultList();

        if (userEntities.isEmpty()) {
            return Response.ok("Either no such user exists, or token has expired or user logged out").build();

        }

        List<UserOrder> orders = em.createQuery("SELECT o FROM UserOrder o WHERE o.id =:id", UserOrder.class).setParameter("id", id).getResultList();

        if (orders.isEmpty()) {

            return Response.ok("Order with such Id does not exist").build();

        }

        UserOrder order = orders.get(0);

        return Response.ok(order.toJson()).build();

    }

    public Response fetchAllOrders(String token) {

        if (Objects.isNull(token) || token.isEmpty()) {

            return Response.ok("Token is empty").build();

        }

        //Probably have to do annotated security or cache.
        List<UserEntity> userEntities = em.createQuery("SELECT u FROM UserEntity u WHERE u.token =:token", UserEntity.class).setParameter("token", token).getResultList();

        if (userEntities.isEmpty()) {
            return Response.ok("Either no such user exists, or token has expired or user logged out").build();

        }

        List<UserOrder> orders = em.createQuery("SELECT o FROM UserOrder o", UserOrder.class).getResultList();

        return Response.ok(orders).build();

    }

    public Response editOrder(String token, JsonObject jsonOrder) {

        if (Objects.isNull(token) || token.isEmpty()) {

            return Response.ok("Token is empty").build();

        }

        if (Objects.isNull(jsonOrder) || !jsonOrder.containsKey("deliveryAddress")) {

            return Response.ok("Request Body is empty or missing critical keys").build();

        }

        //Probably have to do annotated security or cache.
        List<UserEntity> userEntities = em.createQuery("SELECT u FROM UserEntity u WHERE u.token =:token", UserEntity.class).setParameter("token", token).getResultList();

        if (userEntities.isEmpty()) {
            return Response.ok("Either no such user exists, or token has expired or user logged out").build();

        }

        String deliveryAddress = jsonOrder.getString("deliveryAddress");

        UserOrder userOrder = new UserOrder();
        userOrder.setDeliveryAddress(deliveryAddress);
        userOrder.setOrderDate(Date.from(Instant.now()));

        em.merge(userOrder);

        return Response.ok(userOrder.toJson()).build();

    }

}
