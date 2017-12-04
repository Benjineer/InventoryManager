/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cardinalstone.services;

import com.cardinalstone.entities.UserEntity;
import com.cardinalstone.enums.Role;
import com.cardinalstone.utils.PasswordManager;
import java.util.List;
import java.util.Objects;
import javax.ejb.EJB;
import javax.ejb.Stateless;
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

    @EJB
    private PasswordManager pm;

    public Response createUser(String username, String password, String role) {

        if (Objects.isNull(username) || username.isEmpty()) {

            return Response.ok("Username is empty").build();

        }

        if (Objects.isNull(password) || password.isEmpty()) {

            return Response.ok("Password is empty").build();

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
            userEntity.setRole(Enum.valueOf(Role.class, role));
        }

        em.persist(userEntity);

        return Response.ok(userEntity.toJson()).status(Response.Status.CREATED).build();

    }

    public Response login(String username, String password) {

        if (Objects.isNull(username) || username.isEmpty()) {

            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Username is empty").build();

        }

        if (Objects.isNull(password) || password.isEmpty()) {

            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Password is empty").build();

        }

        List<UserEntity> userEntities = em.createQuery("SELECT u FROM UserEntity u WHERE u.username =:username", UserEntity.class).setParameter("username", username).getResultList();

        if (userEntities.size() < 1) {

            return Response.status(Response.Status.NOT_FOUND).entity("Does not exist").build();
        }

        UserEntity userEntity = userEntities.get(0);

        String passwordHash = userEntity.getPassword();

        boolean match = pm.checkPassword(password, passwordHash);

        if (!match) {

            return Response.status(Response.Status.CONFLICT).entity("Incorrect password").build();

        }

        userEntity.setToken(pm.generatePassword(24));

        return Response.ok(userEntity.toJson()).build();

    }

    public Response logout(String token) {

        if (Objects.isNull(token) || token.isEmpty()) {

            return Response.ok("Token is empty").build();

        }

        List<UserEntity> userEntities = em.createQuery("SELECT u FROM UserEntity u WHERE u.token =:token", UserEntity.class).setParameter("token", token).getResultList();

        if (userEntities.isEmpty()) {
            //to do: will have to implement token expiration later
            return Response.ok("Expired token or user is not logged in").build();

        }

        UserEntity userEntity = userEntities.get(0);
        userEntity.setToken("");

        em.merge(userEntity);

        return Response.ok(userEntity.toJson()).build();

    }

    public void changePassword() {
        //yet to be implemented
    }

    public void deleteUser() {

    }

}
