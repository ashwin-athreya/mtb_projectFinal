package com.cg.mts.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cg.mts.entity.Booking;
import com.cg.mts.exception.BookingNotFoundException;
import com.cg.mts.repository.BookingRepository;


@SpringBootTest
class BookingServiceImplTest {
	@MockBean
	private BookingRepository bookingRepository;
	@Autowired
    IBookingService bookingService;
	
	
	
	@Test
	void testViewBookingList() throws BookingNotFoundException {
		Booking booking1 = new Booking();
        Booking booking2 = new Booking();

        List<Booking> mockBookings = Arrays.asList(booking1, booking2);

        when(bookingRepository.findAll()).thenReturn(mockBookings);

        // Act
        List<Booking> result = bookingService.viewBookingList();

        // Assert
        assertEquals(mockBookings, result);
	}

	@Test
    public void testUpdateBooking() throws BookingNotFoundException {
        // Create test data
        Booking booking = new Booking();
        booking.setTransactionId(1);
 
        // Mock repository behavior
        when(bookingRepository.getOne(booking.getTransactionId())).thenReturn(booking);
 
        // Call the method to be tested
        Booking result = bookingService.updateBooking(booking);
 
        // Verify that the repository methods were called with the correct parameters
        verify(bookingRepository, times(1)).saveAndFlush(booking);
        verify(bookingRepository, times(1)).getOne(booking.getTransactionId());
 
        // Assert the result
        assertNotNull(result);
        assertEquals(booking, result);
	}
}
