/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cardinalstone.services;

import com.cardinalstone.entities.UserEntity;
import com.cardinalstone.enums.Role;
import com.cardinalstone.utils.CacheManager;
import com.cardinalstone.utils.PasswordManager;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Response;

/**
 *
 * @author NiyiO
 */
@Stateless
public class UserMgmt {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private CacheManager cm;

    @Inject
    private PasswordManager pm;

    public Response createUser(String username, String password, String role) {

        if (Objects.isNull(username) || username.isEmpty()) {

            return Response.ok("Username or password is not present").build();

        }
        if (Objects.isNull(password) || password.isEmpty()) {

            return Response.ok("Username or password is not present").build();

        }

        List<UserEntity> userEntities = em.createQuery("SELECT u FROM UserEntity u WHERE u.username =:username", UserEntity.class).setParameter("username", username).getResultList();

        if (userEntities.size() > 0) {

            return Response.ok("Already Exists").build();
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setUsername(username);
        userEntity.setPassword(pm.hashPasword(password));
        if (Objects.isNull(role) || role.isEmpty()) {

            userEntity.setRole(Role.USER);

        } else {
            userEntity.setRole(Enum.valueOf(Role.class, role.toUpperCase()));
        }

        em.persist(userEntity);

        return Response.ok().status(Response.Status.CREATED).build();

    }

    public Response login(String username, String password) {

        if (Objects.isNull(username) || username.isEmpty()) {

            return Response.ok("Username or password is not present").build();

        }
        if (Objects.isNull(password) || password.isEmpty()) {

            return Response.ok("Username or password is not present").build();

        }

        List<UserEntity> userEntities = em.createQuery("SELECT u FROM UserEntity u WHERE u.username =:username", UserEntity.class).setParameter("username", username).getResultList();

        if (userEntities.size() < 1) {

            return Response.ok("Does not exist").build();
        }

        UserEntity userEntity = userEntities.get(0);

        String passwordHash = userEntity.getPassword();

        boolean match = pm.checkPassword(password, passwordHash);

        if (!match) {

            return Response.ok("Incorrect password").build();

        }

        userEntity.setToken(pm.generatePassword(24));

        em.merge(userEntity);
        
        cm.put(userEntity);

        return Response.ok(userEntity.toJson()).build();

    }

    public Response logout() {

//        List<UserEntity> userEntities = em.createQuery("SELECT u FROM UserEntity u WHERE u.token =:token", UserEntity.class).setParameter("token", token).getResultList();
//
//        if (userEntities.isEmpty()) {
//            //to do: will have to implement token expiration later
//            return Response.ok("Expired token or user is not logged in").build();
//
//        }
//
//        UserEntity userEntity = userEntities.get(0);
//        userEntity.setToken("");
//
//        em.merge(userEntity);
        return Response.ok("logged out").build();

    }

    public void changePassword() {
        //yet to be implemented
    }

    public void deleteUser() {

    }

}
