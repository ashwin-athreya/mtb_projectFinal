package com.cg.mts.service;

import com.cg.mts.entity.Theatre;
import com.cg.mts.exception.TheatreNotFoundException;

import java.util.List;

public interface TheatreService {
	
	public List<Theatre> getAllTheatres() throws TheatreNotFoundException;

	public Theatre findTheatres(int theatreId);

	public Theatre addTheatre(Theatre t) throws TheatreNotFoundException;

	public List<Theatre> updateTheatre(Theatre t);

	public List<Theatre> deleteTheatreById(int theatreId);
	
	public List<Theatre> findTheatresByMovie(Integer movieId) throws TheatreNotFoundException;
	
	public List<Theatre> findByTheatreLocation(String location);
}
