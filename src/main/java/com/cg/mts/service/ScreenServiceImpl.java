package com.cg.mts.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.mts.entity.Screen;
import com.cg.mts.entity.Theatre;
import com.cg.mts.exception.ScreenNotFoundException;
import com.cg.mts.repository.ScreenRepository;
import com.cg.mts.repository.TheatreRepository;


@Service
public class ScreenServiceImpl implements ScreenService {

	@Autowired
	private ScreenRepository screenRepository;
	@Autowired
	private TheatreRepository theatreRepository;

	
	 public List<Screen> viewScreenList() throws ScreenNotFoundException {
	        List<Screen> screens = screenRepository.findAll();

	        // Use Optional to handle the case where the list is empty
	        return Optional.of(screens)
	                .filter(list -> !list.isEmpty())
	                .orElseThrow(() -> new ScreenNotFoundException("No screens found"));
	    }

	/**
	 * @return screen
	 */
	 @Override
	 public Screen addScreen(Screen screen, Integer theatreId) throws ScreenNotFoundException {
	     Theatre theatre = Optional.ofNullable(theatreId)
	             .map(theatreRepository::getOne)
	             .orElseThrow(() -> new ScreenNotFoundException("Theatre not found"));

	     return Optional.of(screen)
	             .filter(s -> !screenRepository.existsById(s.getScreenId()))
	             .map(s -> {
	                 s.setTheatre(theatre);
	                 return screenRepository.save(s);
	             })
	             .orElseThrow(() -> new ScreenNotFoundException("Screen already exists"));
	 }

	
	
	@Override
	public Screen viewScreen(int screenId) throws ScreenNotFoundException {
		
		return screenRepository.findById(screenId).get();
	}
	
	/**
	 * @return updatedScreen
	 */
	@Override
	public Screen updateScreen(Screen screen, Integer theatreId) {
	    Theatre theatre = Optional.ofNullable(theatreId)
	            .map(theatreRepository::getOne)
	            .orElse(new Theatre());

	    screen.setTheatre(theatre);
	    return screenRepository.save(screen);
	}


	@Override
	public Theatre getTheatre(int screenId) throws ScreenNotFoundException {
		Screen screen =screenRepository.findById(screenId).get();
		Theatre theatre=screen.getTheatre();
		return theatre;
	}
}
