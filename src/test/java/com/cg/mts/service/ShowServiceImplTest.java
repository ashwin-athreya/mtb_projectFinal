package com.cg.mts.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cg.mts.entity.Show;
import com.cg.mts.repository.ShowRepository;


@SpringBootTest
class ShowServiceImplTest {
	
	@MockBean
	private ShowRepository showRepository;
	@Autowired
	private ShowService showService;

	  @Test
	    public void testViewShow() {
	        // Arrange
	        int showId = 1;
	        Show expectedShow = new Show();
	        expectedShow.setShowId(showId);
	 
	        when(showRepository.findById(showId)).thenReturn(Optional.of(expectedShow));
	 
	        // Act
	        Show viewedShow = showService.viewShow(showId);
	 
	        // Assert
	        assertNotNull(viewedShow);
	        assertEquals(expectedShow, viewedShow);
	 
	        verify(showRepository, times(1)).findById(showId);

}
	  @Test
	    public void testViewAllShows() {
	        // Arrange
	        List<Show> expectedShows = Arrays.asList(new Show(), new Show(), new Show());
	 
	        when(showRepository.findAll()).thenReturn(expectedShows);
	 
	        // Act
	        List<Show> allShows = showService.viewAllShows();
	 
	        // Assert
	        assertNotNull(allShows);
	        assertEquals(expectedShows.size(), allShows.size());
	        assertEquals(expectedShows, allShows);
	 
	        verify(showRepository, times(1)).findAll();
}
	  
	 
}