package com.application.TicketTracker.Service;

import java.util.List;

import com.application.TicketTracker.Entities.Ticket;

public interface TicketService {

	Ticket createTicket(Ticket ticket);
	Ticket updateTicket(Ticket ticket, Integer id);
	void deleteTicket(Integer id);
	Ticket getTicketById(Integer id);
	List<Ticket> getAllTickets();
	List<Ticket> searchTicket(String keyword);
}
