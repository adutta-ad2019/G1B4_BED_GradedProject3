package com.application.TicketTracker.Controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.application.TicketTracker.Entities.Ticket;
import com.application.TicketTracker.Service.TicketService;

@Controller
@RequestMapping("/admin")
public class TicketController {

	@Autowired
	private TicketService ticketService;
	
	@GetMapping("/tickets/newTicket")
	public String createTicketPage(Model model) {
		model.addAttribute("ticket", new Ticket());
		return "create";
	}
	
	@PostMapping("/tickets/newTicket")
	public String createTicket(@Valid @ModelAttribute("ticket") Ticket ticket, 
			BindingResult result, Model model, HttpSession session) {
		try {
			if(result.hasErrors()) {
				
				System.out.println("ERROR" + result.toString());
				model.addAttribute("ticket", ticket);
				return "create";
			}
			
			ticketService.createTicket(ticket);
			model.addAttribute("ticket", new Ticket());
			session.setAttribute("message", "Ticket Created Successfully");
			
			return "redirect:/admin/tickets";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("ticket", ticket);
			session.setAttribute("message", "Ticket Creation Falied");
			
			return "create";
		}
		
	}
	
	@GetMapping("/tickets")
	public String showAllTickets(Model model) {
		model.addAttribute("tickets", ticketService.getAllTickets());
		return "tickets";
	}
	
	@GetMapping("/tickets/search/{query}")
	public ResponseEntity<?> showTicketBySearch( @PathVariable("query") String query) {
		return ResponseEntity.ok(ticketService.searchTicket("%" + query + "%"));
	}
	
	@GetMapping("/delete/{id}")
	public String deleteTicket(@PathVariable("id") Integer id, Model model, 
			HttpSession session) {
		ticketService.deleteTicket(id);
		session.setAttribute("message", "Ticket Deleted Successfully");
		
		return "redirect:/admin/tickets";
	}
	
	@GetMapping("/tickets/{id}/edit")
	public ModelAndView updateTicketPage(@PathVariable("id") Integer id) {
		ModelAndView view = new ModelAndView("update-ticket-form");
		Ticket ticket = ticketService.getTicketById(id);
		view.addObject("ticket", ticket);
		
		return view;
	}
	
	@GetMapping("/tickets/{id}")
	public ModelAndView viewTicketPage(@PathVariable("id") Integer id) {
		ModelAndView view = new ModelAndView("view-ticket-form");
		Ticket ticket = ticketService.getTicketById(id);
		view.addObject("ticket", ticket);
		
		return view;
	}
	
	@PostMapping("/tickets/{id}/edit")
	public String editUser(@Valid @ModelAttribute("ticket") Ticket ticket,
			@PathVariable("id") int id, BindingResult result, 
			Model model, HttpSession session) {
		try {
			if(result.hasErrors()) {
				
				System.out.println("ERROR" + result.toString());
				model.addAttribute("ticket", ticket);
				return "update-ticket-form";
			}
			ticketService.updateTicket(ticket, id);
			session.setAttribute("message", "Ticket Updated Successfully");
			
			return "redirect:/admin/tickets";
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("message", "Ticket Update Falied");
			
			return "update-ticket-form";
		}
	}
}
