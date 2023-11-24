package com.cg.mts.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cg.mts.entity.Booking;
import com.cg.mts.entity.Ticket;
import com.cg.mts.exception.TicketNotFoundException;
import com.cg.mts.repository.BookingRepository;
import com.cg.mts.repository.TicketRepository;


@SpringBootTest
class TicketServiceImplTest {

	 @MockBean
	    private TicketRepository ticketRepository;

	    @MockBean
	    private BookingRepository bookingRepository;

	    @Autowired
	    private TicketService ticketService;

	    @Test
	    public void testFindTicket() throws TicketNotFoundException {
	        int ticketId = 1;
	        Ticket expectedTicket = new Ticket(); 
	        when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(expectedTicket));
	        Ticket result = ticketService.findTicket(ticketId);
	        assertEquals(expectedTicket, result);
	        verify(ticketRepository, times(1)).findById(ticketId);
	    }

	    @Test
	    public void testViewTicketList() throws TicketNotFoundException {
	        List<Ticket> ticketList = new ArrayList<>();
	        ticketList.add(new Ticket()); 
	        when(ticketRepository.findAll()).thenReturn(ticketList);
	        List<Ticket> result = ticketService.viewTicketList();
	        assertEquals(ticketList, result);
	        verify(ticketRepository, times(1)).findAll();
	    }
        
	    @Test
	    public void testViewTicketListEmpty() {
	        List<Ticket> emptyList = new ArrayList<>();
	        when(ticketRepository.findAll()).thenReturn(emptyList);
	        assertThrows(TicketNotFoundException.class, () -> ticketService.viewTicketList());
	        verify(ticketRepository, times(1)).findAll();
	    }
 
	    
	    @Test
	    public void testAddTicketWithTransactionId() throws TicketNotFoundException {
	        Integer transactionId = 1;
	        Booking booking = new Booking(); 
	        Ticket ticket = new Ticket(); 
	        when(bookingRepository.findById(transactionId)).thenReturn(Optional.of(booking));
	        Ticket result = ticketService.addTicket(ticket, transactionId);
	        assertEquals(ticket, result);
	        assertEquals("Completed", booking.getTransactionStatus());
	        verify(bookingRepository, times(1)).findById(transactionId);
	        verify(ticketRepository, times(1)).saveAndFlush(ticket);
	    }

}
