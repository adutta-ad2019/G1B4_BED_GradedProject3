package com.application.TicketTracker.Exceptions;

public class TicketNotFoundException extends RuntimeException{

	public TicketNotFoundException(String message) {
		super(message);
	}
	
}
