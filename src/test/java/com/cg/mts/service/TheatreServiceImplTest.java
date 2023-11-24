package com.cg.mts.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.cg.mts.entity.Theatre;
import com.cg.mts.exception.TheatreNotFoundException;
import com.cg.mts.repository.TheatreRepository;


@SpringBootTest
class TheatreServiceImplTest {

	@InjectMocks
	private TheatreServiceImpl service;
	
	@Mock
    private TheatreRepository theatreRepository;
	
	@Test
    public void testGetAllTheatres() throws TheatreNotFoundException {
        
        MockitoAnnotations.initMocks(this); // Initialize the mocks

        List<Theatre> sampleTheatres = Collections.singletonList(new Theatre());

        
        when(theatreRepository.findAll()).thenReturn(sampleTheatres);

        List<Theatre> resultTheatres = service.getAllTheatres();

        // Assert
        assertNotNull(resultTheatres); // Ensure that the result is not null
        assertEquals(sampleTheatres, resultTheatres); // Ensure that the correct list of theatres is returned

        // Verify that the findAll method was called
        verify(theatreRepository, times(1)).findAll();

}
}
