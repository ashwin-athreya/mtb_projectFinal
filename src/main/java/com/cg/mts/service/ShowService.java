package com.cg.mts.service;

import java.time.LocalDate;
import java.util.List;

import com.cg.mts.entity.Show;

public interface ShowService {

	public Show addShow(Show show, Integer theatreId, Integer screenId);

	public Show updateShow(Show show, Integer theatreId, Integer screenId);

	public Show removeShow(int showid);

	public Show viewShow(int showid);

	public List<Show> viewAllShows();

	public List<Show> viewShowList(int date);

	public List<Show> viewShowsList(LocalDate date);
	
	public Show getShowById(int showid)throws Exception;

}
