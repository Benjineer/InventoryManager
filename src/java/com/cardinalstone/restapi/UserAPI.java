/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cardinalstone.restapi;

import com.cardinalstone.enums.Role;
import com.cardinalstone.services.UserMgmt;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author NiyiO
 */
@Path("user")
@Stateless
public class UserAPI {

    @Inject
    private UserMgmt userService;

    @Path("create")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(@QueryParam("username") String username, @QueryParam("password") String password, @QueryParam("role") String role) {

        return userService.createUser(username, password, role);

    }

    @Path("login")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@QueryParam("username") String username, @QueryParam("password") String password) {

        return userService.login(username, password);

    }

    @Path("logout")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response logout(@QueryParam("token") String token) {

        return userService.logout(token);

    }

}
