package com.cg.mts.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cg.mts.entity.Movie;
import com.cg.mts.exception.MovieNotFoundException;
import com.cg.mts.repository.MoviesRepository;


@SpringBootTest
class MoviesServiceImplTest {
	@MockBean
	private MoviesRepository moviesrepository;
	@Autowired
	  private MoviesService movieService;
	 
    @Test
    public void testAddMovie() throws MovieNotFoundException {
        Movie movieToAdd = new Movie();
        when(moviesrepository.existsById(movieToAdd.getMovieId())).thenReturn(false);
        when(moviesrepository.saveAndFlush(movieToAdd)).thenReturn(movieToAdd);
        Movie result = movieService.addMovie(movieToAdd);
        assertEquals(movieToAdd, result);
        verify(moviesrepository, times(1)).existsById(movieToAdd.getMovieId());
        verify(moviesrepository, times(1)).saveAndFlush(movieToAdd);
    }
    
    @Test
    public void testAddMovieWithExistingId() {
        Movie movieToAdd = new Movie();
        when(moviesrepository.existsById(movieToAdd.getMovieId())).thenReturn(true);
        assertThrows(MovieNotFoundException.class, () -> {
            movieService.addMovie(movieToAdd);
        });
        verify(moviesrepository, times(1)).existsById(movieToAdd.getMovieId());
        verify(moviesrepository, never()).saveAndFlush(movieToAdd);
    }

    @Test
    public void testUpdateMovie()throws Exception {
        Movie originalMovie = new Movie();
        when(moviesrepository.saveAndFlush(originalMovie)).thenReturn(originalMovie);
        when(moviesrepository.getOne(originalMovie.getMovieId())).thenReturn(originalMovie);
        Movie result = movieService.updateMovie(originalMovie);
        assertEquals(originalMovie, result);
        verify(moviesrepository, times(1)).saveAndFlush(originalMovie);
        verify(moviesrepository, times(1)).getOne(originalMovie.getMovieId());
    }
	

}
