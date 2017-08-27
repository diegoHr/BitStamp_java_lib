package com.diego_hernando.bitStamp_java_lib.exceptions;

public class BadResponseException extends Exception{
	
	private int responseStatus;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6722338729647792953L;

	public BadResponseException(int responseStatus) {
		super("Status code "+responseStatus);
		this.responseStatus=responseStatus;
        
    }
	
	public int getResponseStatus() {
		return responseStatus;
	}

}
