package com.automation.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CustomException extends Throwable implements ExceptionMapper<Throwable> {
	
	private static final long serialVersionUID = 1L;

	public Response toResponse(Throwable exception) {
		return Response.status(500).entity("Exception Occured on Serverside while posting agent data from extension !!!").type("text/plain").build();
	}
}
