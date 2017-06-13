package com.automation.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
//Test url
//http://localhost:8082/TimeTracker/rest/hello/raju
@Path("/hello")
public class TestService {

	@GET
	@Path("/{param}")
	public Response getMsg(@PathParam("param") String msg) {

		String output = "Hello : " + msg;

		return Response.status(200).entity(output).build();

	}

}
