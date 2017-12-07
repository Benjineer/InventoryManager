/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cardinalstone.services;

import com.cardinalstone.entities.Item;
import com.cardinalstone.entities.UserEntity;
import java.util.List;
import java.util.Objects;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Response;

/**
 *
 * @author NiyiO
 */
@Stateless
public class WareHouseMgmt {

    @PersistenceContext
    private EntityManager em;

    public Response storeItem(String token, String name, String description, Double price) {

        if (Objects.isNull(token) || token.isEmpty()) {

            return Response.ok("Name is empty").build();

        }
        if (Objects.isNull(name) || name.isEmpty()) {

            return Response.ok("Name is empty").build();

        }

        if (Objects.isNull(description) || description.isEmpty()) {

            return Response.ok("Description is empty").build();

        }

        if (Objects.isNull(price) || price.isNaN()) {

            return Response.ok("Price is not a number or is empty").build();

        }

        //Probably have to do annotated security or cache.
        List<UserEntity> userEntities = em.createQuery("SELECT u FROM UserEntity u WHERE u.token =:token", UserEntity.class).setParameter("token", token).getResultList();

        if (userEntities.isEmpty()) {
            return Response.ok("Either no such user exists, or token has expired or user logged out").build();

        }

        List<Item> items = em.createQuery("SELECT i FROM Item i WHERE i.name =: name", Item.class).setParameter("name", name).getResultList();

        if (!items.isEmpty()) {

            Response.ok("Item with same name already exists").build();
        }

        Item item = new Item();

        item.setName(name);
        item.setDescription(description);
        item.setPrice(price);

        em.persist(item);

        return Response.ok(item.toJson()).build();

    }

    public Response fetchItem(String token, String itemName) {

        if (Objects.isNull(token) || token.isEmpty()) {

            return Response.ok("Token is empty").build();

        }

        if (Objects.isNull(itemName) || itemName.isEmpty()) {

            return Response.ok("Name is empty").build();

        }

        //Probably have to do annotated security or cache.
        List<UserEntity> userEntities = em.createQuery("SELECT u FROM UserEntity u WHERE u.token =:token", UserEntity.class).setParameter("token", token).getResultList();

        if (userEntities.isEmpty()) {
            return Response.ok("Either no such user exists, or token has expired or user logged out").build();

        }

        //to do: make all simple queries named queries
        List<Item> items = em.createQuery("SELECT i FROM Item i WHERE i.name =:name", Item.class).setParameter("name", itemName).getResultList();

        if (items.isEmpty()) {

            Response.ok("Item does not exist").build();
        }

        Item item = items.get(0);

        return Response.ok(item.toJson()).build();

    }

    public Response fetchAllItems(String token) {

        if (Objects.isNull(token) || token.isEmpty()) {

            return Response.ok("Token is empty").build();

        }

        //Probably have to do annotated security or cache.
        List<UserEntity> userEntities = em.createQuery("SELECT u FROM UserEntity u WHERE u.token =:token", UserEntity.class).setParameter("token", token).getResultList();

        if (userEntities.isEmpty()) {
            return Response.ok("Either no such user exists, or token has expired or user logged out").build();

        }

        //to do: make all simple queries named queries
        List<Item> items = em.createQuery("SELECT i FROM Item i", Item.class).getResultList();

        if (items.isEmpty()) {

            Response.ok("There are no items in the store").build();
        }

        return Response.ok(items).build();

    }

    public Response updateItem(String token, String itemName, String description) {

        if (Objects.isNull(token) || token.isEmpty()) {

            return Response.ok("Token is empty").build();

        }

        if (Objects.isNull(itemName) || itemName.isEmpty()) {

            return Response.ok("Name is empty").build();

        }

        //Probably have to do annotated security or cache.
        List<UserEntity> userEntities = em.createQuery("SELECT u FROM UserEntity u WHERE u.token =:token", UserEntity.class).setParameter("token", token).getResultList();

        if (userEntities.isEmpty()) {
            return Response.ok("Either no such user exists, or token has expired or user logged out").build();

        }

        //to do: make all simple queries named queries
        List<Item> items = em.createQuery("SELECT i FROM Item i WHERE i.name =:name", Item.class).setParameter("name", itemName).getResultList();

        if (items.isEmpty()) {

            Response.ok("Item does not exist").build();
        }

        Item item = items.get(0);

        item.setName(itemName);
        item.setDescription(description);

        em.merge(item);

        return Response.ok("updated Item successfully").build();

    }

    public Response deleteItem(String token, String itemName) {

        if (Objects.isNull(token) || token.isEmpty()) {

            return Response.ok("Token is empty").build();

        }

        if (Objects.isNull(itemName) || itemName.isEmpty()) {

            return Response.ok("Name is empty").build();

        }

        //Probably have to do annotated security or cache.
        List<UserEntity> userEntities = em.createQuery("SELECT u FROM UserEntity u WHERE u.token =:token", UserEntity.class).setParameter("token", token).getResultList();

        if (userEntities.isEmpty()) {
            return Response.ok("Either no such user exists, or token has expired or user logged out").build();

        }

        //to do: make all simple queries named queries
        List<Item> items = em.createQuery("SELECT i FROM Item i WHERE i.name =:name", Item.class).setParameter("name", itemName).getResultList();

        if (items.isEmpty()) {

            Response.ok("Item does not exist").build();
        }

        Item item = items.get(0);

        em.remove(item);

        return Response.ok("Item has been deleted").build();

    }

}
