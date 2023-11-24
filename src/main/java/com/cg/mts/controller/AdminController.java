package com.cg.mts.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.mts.dto.BookingDTO;
import com.cg.mts.dto.CustomerDTO;
import com.cg.mts.dto.MovieDTO;
import com.cg.mts.dto.ScreenDTO;
import com.cg.mts.dto.ScreenResponseDTO;
import com.cg.mts.dto.SeatDTO;
import com.cg.mts.dto.SeatResponseDTO;
import com.cg.mts.dto.ShowDTO;
import com.cg.mts.dto.ShowResponeDTO;
import com.cg.mts.dto.TheatreDTO;
import com.cg.mts.dto.UserDTO;
import com.cg.mts.entity.Booking;
import com.cg.mts.entity.Customer;
import com.cg.mts.entity.Movie;
import com.cg.mts.entity.Screen;
import com.cg.mts.entity.Seat;
import com.cg.mts.entity.Show;
import com.cg.mts.entity.Theatre;
import com.cg.mts.entity.User;
import com.cg.mts.exception.AccessForbiddenException;
import com.cg.mts.exception.BookingNotFoundException;
import com.cg.mts.exception.CustomerNotFoundException;
import com.cg.mts.exception.MovieNotFoundException;
import com.cg.mts.exception.ScreenNotFoundException;
import com.cg.mts.exception.SeatNotFoundException;
import com.cg.mts.exception.TheatreNotFoundException;
import com.cg.mts.exception.UserNotFoundException;
import com.cg.mts.mapper.EntityDtoMapper;
import com.cg.mts.repository.CustomerRepository;
import com.cg.mts.service.CustomerService;
import com.cg.mts.service.IAdminService;
import com.cg.mts.service.IBookingService;
import com.cg.mts.service.ISeatService;
import com.cg.mts.service.IUserService;
import com.cg.mts.service.MoviesService;
import com.cg.mts.service.ScreenService;
import com.cg.mts.service.ShowService;
import com.cg.mts.service.TheatreService;
import com.cg.mts.service.TicketService;

import io.swagger.annotations.Api;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/admin")
@Api(value = "Swagger2DemoRestController")
public class AdminController {

	Logger logger = LoggerFactory.getLogger(AdminController.class);
	@Autowired
	IUserService userService;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	private IBookingService bookingService;
	@Autowired
	IAdminService adminService;
	@Autowired
	private MoviesService moviesService;
	@Autowired
	private ScreenService screenService;
	@Autowired
	private ISeatService seatService;
	
	@Autowired
	private ShowService showService;
	
	@Autowired
	private TheatreService theatreservice;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private TicketService ticketService;

//--------------------------admin--------------------------------------------------
	
	@PostMapping("/registeradmin/{username}/{password}")
	public HttpStatus registerAdmin(@PathVariable String username, @PathVariable String password) throws Exception {
		logger.info("-------------------Admin created-----------------");
		adminService.registerAdmin(username, password);
		return HttpStatus.CREATED;
	}
	
	
	@DeleteMapping("/removeadmin/{adminId}")
    public HttpStatus removeAdmin(@PathVariable int adminId) throws UserNotFoundException {
        logger.info("-------------------Admin removed-----------------");
        adminService.removeAdmin(adminId);
        return HttpStatus.OK;
    }
	
	
	
//--------------------------------------user---------------------------------------------------------

	
	
	@DeleteMapping("/removeuser")
	public ResponseEntity<UserDTO> removeUser(@Valid @RequestBody UserDTO userDTO) throws AccessForbiddenException {
		
		User user=EntityDtoMapper.convertToEntity(userDTO, User.class);
		logger.info("--------------------User Deleted------------------");
		User remUser=userService.removeUser(user);
		UserDTO remUserDTO=EntityDtoMapper.convertToDTO(remUser, UserDTO.class);
		return ResponseEntity.ok(remUserDTO);
	}
	
	
//--------------------customer--------------------------------------------------------------------------
	
	@DeleteMapping("/customer/delete/{customerId}")
	public ResponseEntity<CustomerDTO> removeCustomer(@PathVariable int customerId)
			throws CustomerNotFoundException, AccessForbiddenException {
		
		logger.info("----------------Customer Deleted Successfully--------------");
		Customer customer=customerService.removeCustomer(customerId);
		CustomerDTO resultDTO=EntityDtoMapper.convertToDTO(customer, CustomerDTO.class);
		return ResponseEntity.ok(resultDTO);
	}
	
	
	
	//------------------------------booking--------------------------------------------------------------
	
	

	@DeleteMapping("/booking/delete/{bookingId}")
	public ResponseEntity<BookingDTO> deleteTicketBookingById(@PathVariable int bookingId)
			throws BookingNotFoundException, AccessForbiddenException {
		logger.info("-------Booking With Booking Id " + bookingId + " Deleted Successfully---------");
		Booking booking=bookingService.cancelBooking(bookingId);
		BookingDTO resultDTO=EntityDtoMapper.convertToDTO(booking, BookingDTO.class);
		return ResponseEntity.ok(resultDTO);
	}
	
	
	@PutMapping(value = "/booking/update", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BookingDTO> updateTicketBooking(@Valid @RequestBody BookingDTO bookingDTO)
			throws BookingNotFoundException, AccessForbiddenException {
		Booking booking = EntityDtoMapper.convertToEntity(bookingDTO, Booking.class);
		logger.info("-------Booking Updated Successfully---------");
		Booking updtBooking=bookingService.updateBooking(booking);
		BookingDTO updtBookingDTO=EntityDtoMapper.convertToDTO(updtBooking, BookingDTO.class);
		return ResponseEntity.ok(updtBookingDTO);
	}
	
	//------------------------------movies------------------------------
	
	@PostMapping("/movies/add")
	public ResponseEntity<MovieDTO> addMovie(@Valid @RequestBody MovieDTO movieDTO)
			throws MovieNotFoundException, IOException {
		Movie movie=EntityDtoMapper.convertToEntity(movieDTO, Movie.class);
		Movie addedmovie = moviesService.addMovie(movie);
		MovieDTO resultDTO=EntityDtoMapper.convertToDTO(addedmovie, MovieDTO.class);
		logger.info("-------Movie Added Successfully---------");
		return new ResponseEntity<>(resultDTO, HttpStatus.CREATED);
	}

	@PutMapping("/movies/update")
	public ResponseEntity<MovieDTO> updateMovie(@RequestBody MovieDTO movieDTO)
			throws MovieNotFoundException {

		ResponseEntity<MovieDTO> response = null;
		Movie movie=EntityDtoMapper.convertToEntity(movieDTO, Movie.class);
		if (movie == null) {
			response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			Movie updtmovie = moviesService.updateMovie(movie);
			MovieDTO resultDTO=EntityDtoMapper.convertToDTO(updtmovie, MovieDTO.class);
			response = new ResponseEntity<>(resultDTO, HttpStatus.OK);
			logger.info("-------Movie Updated Successfully---------");
		}
		return response;
	}
	
	@DeleteMapping("/movies/delete/{movieId}")
	public ResponseEntity<MovieDTO> removeMovie(@PathVariable int movieId)
			throws MovieNotFoundException {

		ResponseEntity<MovieDTO> response = null;
		Movie movie = moviesService.viewMovie(movieId);
		if (movie == null) {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			moviesService.removeMovie(movieId);
			MovieDTO remMovieDTO=EntityDtoMapper.convertToDTO(movie, MovieDTO.class);
			response = new ResponseEntity<>(remMovieDTO, HttpStatus.OK);
			logger.info("-------Movie With Movie id " + movieId + " Deleted---------");
		}
		return response;
	}
	
	@PutMapping("/movies/map")
	public ResponseEntity<MovieDTO> addToShow(@Valid @RequestBody MovieDTO movieDTO,@RequestParam(required = true) Integer showId)
			throws MovieNotFoundException {

		ResponseEntity<MovieDTO> response = null;
		Movie movie=EntityDtoMapper.convertToEntity(movieDTO, Movie.class);
		if (movie == null) {
			response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			Movie shmovie = moviesService.addMovieToShow(movie,showId);
			MovieDTO resultDTO=EntityDtoMapper.convertToDTO(shmovie, MovieDTO.class);
			response = new ResponseEntity<>(resultDTO, HttpStatus.OK);
			logger.info("-------Movie Updated Successfully---------");
		}
		return response;
	}

//---------------------------screens----------------------------------------------------------------------
	
	
	@PostMapping("/screens/add")
	public ResponseEntity<ScreenResponseDTO> addAScreen(@Valid @RequestBody ScreenDTO screenDTO,
	        @RequestParam(required = true) Integer theatreId)
	        throws ScreenNotFoundException, TheatreNotFoundException {

	    logger.info("-------Screen Successfully added into Theatre " + theatreId + "---------");
	    Screen screen = new Screen();
	    screen.setScreenName(screenDTO.getScreenName());
	    screen.setRows(screenDTO.getRows());
	    screen.setColumns(screenDTO.getColumns());

	    Theatre theatre = theatreservice.findTheatres(theatreId);
	    
	    if (theatre == null) {
	        
	        throw new TheatreNotFoundException("Theatre with ID " + theatreId + " not found");
	    }
	    
	    screen.setTheatre(theatre);

	    Screen addedScreen = screenService.addScreen(screen, theatreId);
	    
	    if (addedScreen.getTheatre() == null) {
	        // Handle the case where the Theatre is not properly set in the Screen
	        throw new RuntimeException("Theatre not properly set in the added Screen");
	    }
	    
	    ScreenResponseDTO addedScreenDTO = new ScreenResponseDTO(
	            addedScreen.getScreenId(),
	            addedScreen.getScreenName(),
	            addedScreen.getRows(),
	            addedScreen.getColumns(),
	            addedScreen.getTheatre().getTheatreId(), 
	            addedScreen.getTheatre().getTheatreName(),
	            addedScreen.getTheatre().getTheatreCity()
	    );
	    return ResponseEntity.ok(addedScreenDTO);
	}

	
	@PutMapping("/screens/update/{theatreId}")
	public ResponseEntity<ScreenResponseDTO> updateScreen(@Valid @RequestBody ScreenDTO screenDTO,
	        @PathVariable(required = true) Integer theatreId)
	        throws ScreenNotFoundException {

	    ResponseEntity<ScreenResponseDTO> response = null;
	    Screen screen = new Screen();
	    screen.setScreenId(screenDTO.getScreenId()); // Assuming you have the screenId in the DTO
	    screen.setScreenName(screenDTO.getScreenName());
	    screen.setRows(screenDTO.getRows());
	    screen.setColumns(screenDTO.getColumns());

	    // Set other properties as needed

	    Screen updatedScreen = screenService.updateScreen(screen, theatreId);
	    if (updatedScreen == null) {
	        response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    } else {
	        ScreenResponseDTO updatedScreenDTO = new ScreenResponseDTO(
	                updatedScreen.getScreenId(),
	                updatedScreen.getScreenName(),
	                updatedScreen.getRows(),
	                updatedScreen.getColumns(),
	                updatedScreen.getTheatre().getTheatreId(), // Assuming these properties exist in your Screen entity
	                updatedScreen.getTheatre().getTheatreName(),
	                updatedScreen.getTheatre().getTheatreCity()
	        );
	        response = new ResponseEntity<>(updatedScreenDTO, HttpStatus.OK);
	        logger.info("-------Screen Updated Successfully---------");
	    }
	    return response;
	}
//---------------------------------------seat-----------------------------------------------
	
	@PostMapping("/seats/addseat")
	public ResponseEntity<SeatResponseDTO> addASeat(@Valid @RequestBody SeatDTO seatDTO)
			throws AccessForbiddenException, SeatNotFoundException {
		Seat seat=EntityDtoMapper.convertToEntity(seatDTO, Seat.class);
		
		Seat addedseat = seatService.addSeat(seat);
		SeatResponseDTO addedseatDTO=EntityDtoMapper.convertToDTO(addedseat, SeatResponseDTO.class);
		logger.info("-------Seat Added Successfully---------");
		return new ResponseEntity<>(addedseatDTO, HttpStatus.CREATED);
	}
	
//----------------------------------------shows-------------------------------------------------	

	@PostMapping("/shows/add")
	public ResponseEntity<ShowResponeDTO> addShow(@Valid @RequestBody ShowDTO showDTO, @RequestParam(required = false) Integer theatreId,
			@RequestParam(required = false) Integer screenId)  {
		Show show = new Show();
		show.setShowName(showDTO.getShowName());
		show.setShowStartTime(showDTO.getShowStartTime());
		show.setShowEndTime(showDTO.getShowEndTime());
		show.setShowDate(showDTO.getShowDate());
		
		Show addedShow = showService.addShow(show, theatreId, screenId);
		ShowResponeDTO addedShowDTO = new ShowResponeDTO(
		      addedShow.getShowId(),
		      addedShow.getShowStartTime(),
		      addedShow.getShowEndTime(),
		      addedShow.getShowName(),
		      addedShow.getShowDate(),
		      addedShow.getTheatre().getTheatreId(),
		      addedShow.getTheatre().getTheatreName(),
		      addedShow.getTheatre().getTheatreCity(),
		      addedShow.getScreen().getScreenId()
		     );
		
		return ResponseEntity.ok(addedShowDTO);
	}

	
	@DeleteMapping("/shows/delete/{showId}")
	public ResponseEntity<ShowDTO> removeShow(@PathVariable int showId)  {

		ResponseEntity<ShowDTO> response = null;
		Show show = showService.viewShow(showId);
		if (show == null) {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			Show removedShow=showService.removeShow(showId);
			ShowDTO removedShowDTO=EntityDtoMapper.convertToDTO(removedShow, ShowDTO.class);
			response = new ResponseEntity<>(removedShowDTO, HttpStatus.OK);
			logger.info("-------Show with ShowId " + showId + " Deleted Successfully---------");
		}
		return response;
	}
	
	
	@PutMapping("/shows/update")
	public ResponseEntity<ShowDTO> updateShow(@Valid @RequestBody ShowDTO showDTO, @RequestParam(required = false) Integer theatreId,
			@RequestParam(required = false) Integer screenId)  {

		ResponseEntity<ShowDTO> response = null;
		Show show = EntityDtoMapper.convertToEntity(showDTO, Show.class);
		if (show == null) {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			
			Show updatedShow=showService.updateShow(show, theatreId, screenId);
			ShowDTO updatedShowDTO = EntityDtoMapper.convertToDTO(updatedShow, ShowDTO.class);
			response = new ResponseEntity<>(updatedShowDTO, HttpStatus.OK);
			logger.info("-------Show Updated Successfully---------");
		}
		return response;
	}
//--------------------------------theatre------------------------------------------------------	
	
	@PostMapping("/theatre/insert")
	public ResponseEntity<TheatreDTO> addTheatre(@Valid @RequestBody TheatreDTO theatreDTO)
			throws TheatreNotFoundException {

		logger.info("-------Theatre Added Successfully---------");
		Theatre theatre=EntityDtoMapper.convertToEntity(theatreDTO, Theatre.class);
		Theatre addtheatre=theatreservice.addTheatre(theatre);
		TheatreDTO addtheatreDTO=EntityDtoMapper.convertToDTO(addtheatre, TheatreDTO.class);
		return new ResponseEntity<>(addtheatreDTO,HttpStatus.CREATED);
	}


	@DeleteMapping("/theatre/delete/{theatreId}")
	public ResponseEntity<List<TheatreDTO>> deleteMoviesById(@PathVariable int theatreId)
			throws TheatreNotFoundException {

		logger.info("-------Theatre Deleted with Theatre id" + theatreId + "---------");
		List<Theatre> theatre=theatreservice.deleteTheatreById(theatreId);
		List<TheatreDTO> delTheatreDTO=EntityDtoMapper.convertToDTOList(theatre, TheatreDTO.class);
		return ResponseEntity.ok(delTheatreDTO);
	}	
}