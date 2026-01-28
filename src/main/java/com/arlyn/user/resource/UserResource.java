package com.arlyn.user.resource;

import com.arlyn.account.orchestrator.AccountOrchestrator;
import com.arlyn.user.dto.RegisterRequestDTO;
import com.arlyn.common.dto.UserInternalDTO;
import com.arlyn.user.service.UserService;

import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.validation.Valid;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    UserService userService;

    @Inject
    AccountOrchestrator accountOrchestrator;

    @POST
    @Path("/register")
    @PermitAll
    public Response create(@Valid RegisterRequestDTO dto) {

        UserInternalDTO createdUser = userService.create(dto);
        accountOrchestrator.createAccount(createdUser);

        return Response
                .status(Response.Status.CREATED)
                .entity(createdUser)
                .build();
    }
}
