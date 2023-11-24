package com.cg.mts.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cg.mts.entity.Seat;
import com.cg.mts.entity.SeatStatus;
import com.cg.mts.exception.SeatNotFoundException;
import com.cg.mts.repository.SeatRepository;




@SpringBootTest
class SeatServiceImplTest {
	
	@Autowired
	private ISeatService IseatService;
	@MockBean
	private SeatRepository seatRepository;
 
	
	    

	    @Test
	    public void testUpdateSeat() {
	        Seat seat = new Seat();
	        seat.setSeatId(1); 
	        when(seatRepository.saveAndFlush(any(Seat.class))).thenReturn(seat);
	        Seat result = IseatService.updateSeat(seat);
	        assertNotNull(result);
	        assertEquals(1, result.getSeatId());
	        verify(seatRepository, times(1)).saveAndFlush(eq(seat));
	    }

	    @Test
	    public void testBookSeat() {
	        Seat seat = new Seat();
	        seat.setSeatId(1);
	        when(seatRepository.saveAndFlush(any(Seat.class))).thenReturn(seat);
	        Seat result = IseatService.bookSeat(seat);
	        assertNotNull(result);
	        assertEquals(1, result.getSeatId());
	        assertEquals(SeatStatus.BOOKED, result.getStatus());
	        verify(seatRepository, times(1)).saveAndFlush(eq(seat));
	    }
	    @Test
	    public void testBlockSeat() {
	        Seat originalSeat = new Seat();
	        when(seatRepository.saveAndFlush(originalSeat)).thenReturn(originalSeat);
	        Seat result = IseatService.blockSeat(originalSeat);
	        assertEquals(SeatStatus.BLOCKED, result.getStatus());
	        verify(seatRepository, times(1)).saveAndFlush(originalSeat);

	
}
	    @Test
	    public void testViewSeatList() throws SeatNotFoundException {
	        Seat seat1 = new Seat();
	        Seat seat2 = new Seat();
	        when(seatRepository.findAll()).thenReturn(Arrays.asList(seat1, seat2));
	        List<Seat> result = IseatService.viewSeatList();
	        assertNotNull(result);
	        assertEquals(2, result.size());
	        verify(seatRepository, times(1)).findAll();
	    }

	    @Test
	    public void testViewSeatListEmpty() {
	        when(seatRepository.findAll()).thenReturn(Arrays.asList());
	        assertDoesNotThrow(() -> {
	            List<Seat> result = IseatService.viewSeatList();
	            assertNotNull(result);
	            assertTrue(result.isEmpty());
	        });
	        verify(seatRepository, times(1)).findAll();
	    }

}
