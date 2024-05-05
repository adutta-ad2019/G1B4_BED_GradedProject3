package com.application.TicketTracker.Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
@Entity
@Table(name = "TICKET")
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@NotBlank(message = "Title Required")
	private String title;
	@NotBlank(message = "Description Required")
	private String description;
	private String content;
	private String creadtedDate;
	
}
