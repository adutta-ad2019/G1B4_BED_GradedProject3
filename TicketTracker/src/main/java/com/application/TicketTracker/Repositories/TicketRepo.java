package com.application.TicketTracker.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.application.TicketTracker.Entities.Ticket;

@Repository
public interface TicketRepo extends JpaRepository<Ticket, Integer>{

	@Query(value = "select * from ticket where title LIKE :keyword1 OR description LIKE :keyword2", nativeQuery = true)
    List<Ticket> findByTitleContainingOrDescriptionContaining(@Param("keyword1") String keyword1,@Param("keyword2") String keyword2);
}
