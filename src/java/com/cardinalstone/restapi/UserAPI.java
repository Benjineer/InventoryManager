/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cardinalstone.restapi;

import com.cardinalstone.enums.Role;
import com.cardinalstone.services.UserMgmt;
import com.cardinalstone.utils.Auth;
import java.util.Optional;
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
    private UserMgmt userMgmt;

    @Path("create")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(@QueryParam("username") Optional<String> username, @QueryParam("password") Optional<String> password, @QueryParam("role") Optional<String> role) {

        return userMgmt.createUser(username, password, role);

    }

    @Path("login")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@QueryParam("username") Optional<String> username, @QueryParam("password") Optional<String> password) {

        return userMgmt.login(username, password);

    }

    @Path("logout")
    @PUT
    @Auth({Role.USER})
    @Produces(MediaType.APPLICATION_JSON)
    public Response logout() {

        return userMgmt.logout();

    }

}
