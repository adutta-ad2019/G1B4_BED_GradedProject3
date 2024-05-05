package com.application.TicketTracker.Service.Implementations;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.TicketTracker.Entities.Ticket;
import com.application.TicketTracker.Exceptions.TicketNotFoundException;
import com.application.TicketTracker.Repositories.TicketRepo;
import com.application.TicketTracker.Service.TicketService;

@Service
public class TicketServiceImpl implements TicketService {

	@Autowired
	private TicketRepo ticketRepo;
	
	@Override
	public Ticket createTicket(Ticket ticket) {
		LocalDate currentDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy");
        String formattedDate = currentDate.format(formatter);
		ticket.setCreadtedDate(formattedDate);
		return ticketRepo.save(ticket);
	}

	@Override
	public Ticket updateTicket(Ticket ticket, Integer id) {
		Ticket ticketFound = ticketRepo.findById(id).orElseThrow(() -> 
			new TicketNotFoundException("Ticket Not Found with Id: " + id));
		ticketFound.setTitle(ticket.getTitle());
		ticketFound.setDescription(ticket.getDescription());
		ticketFound.setContent(ticket.getContent());
		return ticketRepo.save(ticketFound);
	}

	@Override
	public void deleteTicket(Integer id) {
		Ticket ticketFound = ticketRepo.findById(id).orElseThrow(() -> 
			new TicketNotFoundException("Ticket Not Found with Id: " + id));
		ticketRepo.delete(ticketFound);

	}

	@Override
	public Ticket getTicketById(Integer id) {
		return ticketRepo.findById(id).orElseThrow(() -> 
			new TicketNotFoundException("Ticket Not Found with Id: " + id));
	}

	@Override
	public List<Ticket> getAllTickets() {
		return ticketRepo.findAll();
	}
	
	@Override
	public List<Ticket> searchTicket(String keyword) {
		return ticketRepo.findByTitleContainingOrDescriptionContaining(keyword, keyword);
	}
	
	public String extractTextFromHtml(String htmlContent) {
        Document doc = Jsoup.parse(htmlContent);
        Elements elements = doc.select("body").first().children();
        StringBuilder textContent = new StringBuilder();
        for (Element element : elements) {
            textContent.append(element.text()).append(" ");
        }
        return textContent.toString().trim();
    }

}
