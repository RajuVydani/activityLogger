package com.automation.exceptions;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ValidationException extends Exception implements ExceptionMapper<ValidationException> {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public ValidationException() {
		super("Validation Failed.");
	}
	
	/**
	 * @param message
	 */
	public ValidationException(String message) {
		super(message);
	}
		
	/* (non-Javadoc)
	 * @see javax.ws.rs.ext.ExceptionMapper#toResponse(java.lang.Throwable)
	 */
	public Response toResponse(ValidationException vExc) {
		//String input = "{\"singer\":\"Metallica\",\"title\":\"Fade To Black\"}";
		/*return Response.status(400).entity(vExc.getMessage())
                .type("text/plain").build();*/
		/*return Response.status(400)
				.entity(input)
				.type(MediaType.APPLICATION_JSON)
				.build();*/
		return Response.status(400)
						.entity(vExc.getMessage())
						.type(MediaType.TEXT_PLAIN)
						.build();
	}

}
