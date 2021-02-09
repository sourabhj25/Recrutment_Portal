package com.rmportal.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author saurabh
 * Http Status Response messages and code
 */

@Getter // Generate getter
@AllArgsConstructor //Generate all args constructor
public enum HttpStatusConstants {

	
	 OK("OK.",200),
	 NON_AUTHORITATIVE_INFORMATION("Non-Authoritative Information.",203),
	 NO_CONTENT("No Content.",204),
	 RESET_CONTENT("Reset Content.",205),
	 PARTIAL_CONTENT("Partial Content.",206),
	 BAD_REQUEST("Bad Request.",400),
	 UNAUTHORIZED("Unauthorized.",401),
	 FORBIDDEN("Forbidden.",403),
	 NOT_FOUND("Not Found.",404),
	 REQUEST_ENTITY_TOO_LARGE("Request Entity Too Large.",413),
	 INTERNAL_SERVER_ERROR("Internal Server Error.",500),
	 NOT_IMPLEMENTED("Not Implemented.",501),
	 BAD_GATEWAY("Bad Gateway.",502);
	 
	public String status;
	public int id;

}
