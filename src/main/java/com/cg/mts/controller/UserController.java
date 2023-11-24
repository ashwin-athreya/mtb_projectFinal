package com.cg.mts.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.mts.exception.CustomerNotFoundException;
import com.cg.mts.exception.MovieNotFoundException;
import com.cg.mts.exception.ScreenNotFoundException;
import com.cg.mts.exception.SeatNotFoundException;
import com.cg.mts.exception.ShowNotFoundException;
import com.cg.mts.exception.TheatreNotFoundException;
import com.cg.mts.exception.TicketNotFoundException;
import com.cg.mts.dto.BookingDTO;
import com.cg.mts.dto.BookingResponseDTO;
import com.cg.mts.dto.CustomerDTO;
import com.cg.mts.dto.MovieDTO;
import com.cg.mts.dto.ScreenDTO;
import com.cg.mts.dto.ScreenResponseDTO;
import com.cg.mts.dto.SeatDTO;
import com.cg.mts.dto.SeatResponseDTO;
import com.cg.mts.dto.ShowDTO;
import com.cg.mts.dto.ShowResponeDTO;
import com.cg.mts.dto.TheatreDTO;
import com.cg.mts.dto.TheatreDTO2;
import com.cg.mts.dto.TicketDTO;
import com.cg.mts.dto.UserDTO;
import com.cg.mts.entity.Booking;
import com.cg.mts.entity.Customer;
import com.cg.mts.entity.Movie;
import com.cg.mts.entity.Screen;
import com.cg.mts.entity.Seat;
import com.cg.mts.entity.Show;
import com.cg.mts.entity.Theatre;
import com.cg.mts.entity.Ticket;
import com.cg.mts.entity.User;
import com.cg.mts.exception.AccessForbiddenException;
import com.cg.mts.exception.BookingNotFoundException;
import com.cg.mts.exception.UserCreationError;
import com.cg.mts.mapper.EntityDtoMapper;
import com.cg.mts.repository.CustomerRepository;
import com.cg.mts.service.CustomerService;
import com.cg.mts.service.IBookingService;
import com.cg.mts.service.ISeatService;
import com.cg.mts.service.IUserService;
import com.cg.mts.service.LoginService;
import com.cg.mts.service.MoviesService;
import com.cg.mts.service.ScreenService;
import com.cg.mts.service.ShowService;
import com.cg.mts.service.TheatreService;
import com.cg.mts.service.TicketService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/user")
@Api(value = "Swagger2DemoRestController")
@Validated
public class UserController {

	Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	IUserService userService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private IBookingService bookingService;
	@Autowired
	CustomerRepository customerRepository;
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
	private TicketService ticketService;
	@Autowired
	LoginService logServ;
	
//---------------------------------user--------------------------------------------------	
	
	@PostMapping("/adduser")
	@ApiOperation(value = "add a user")
	public ResponseEntity<UserDTO> addUser(@Valid @RequestBody UserDTO userDTO)
			throws AccessForbiddenException, CustomerNotFoundException, UserCreationError {
		User user=EntityDtoMapper.convertToEntity(userDTO, User.class);
		if (userDTO.getRole().equalsIgnoreCase("CUSTOMER")) {
			Customer customer = new Customer(user.getUsername(), null, null, null, user.getPassword());
			logger.info("-----------------Customer Added---------------------");
			customerRepository.save(customer);
			user.setCustomer(customer);
		}
		logger.info("-----------------User Added---------------------");
		user=userService.addUser(user);
		UserDTO addedUserDTO=EntityDtoMapper.convertToDTO(user, UserDTO.class);
		return ResponseEntity.ok(addedUserDTO);
	}

	
	
	
	
	
	
//----------------------------customer------------------------------------------------------------------------
	
	
	
	
	
	
	
	@GetMapping("/customer/findall")
	public ResponseEntity<List<CustomerDTO>> viewCustomerList() throws AccessForbiddenException {
		
		logger.info("---------------Customer List-----------------");
		List<Customer> customer=customerService.viewCustomerList();
		List<CustomerDTO> resultDTO=EntityDtoMapper.convertToDTOList(customer, CustomerDTO.class);
		return ResponseEntity.ok(resultDTO);
	}
	
	
	@GetMapping("/customer/view/{customerId}")
	public ResponseEntity<CustomerDTO> viewACustomer(@PathVariable int customerId) throws CustomerNotFoundException {

		ResponseEntity<CustomerDTO> response = null;
		try {
			Customer customer = customerService.viewCustomer(customerId);
			CustomerDTO resultDTO=EntityDtoMapper.convertToDTO(customer, CustomerDTO.class);
			response = new ResponseEntity<>(resultDTO, HttpStatus.OK);
			logger.info("-------Movie With Movie id " + customerId + " Found---------");
		} catch (Exception e) {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			throw new CustomerNotFoundException("Customer with " + customerId + " id dosen't exist");
		}
		return response;
	}
	
	
	
	
	
//------------------------------booking------------------------------------------------------------------
	
	
	
	
	@PostMapping(value = "/booking/add", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BookingResponseDTO> addTicketBooking(
			@Valid @RequestBody BookingDTO bookingDTO,
			@RequestParam(required = true) Integer customerId,
			@RequestParam(required = true) Integer showId)
			throws AccessForbiddenException, BookingNotFoundException,Exception
	{
		Booking booking = EntityDtoMapper.convertToEntity(bookingDTO, Booking.class);
		
		Booking addedBooking=bookingService.addBooking(booking, customerId,showId);
		
		boolean isMovieLinked = false;
		boolean isTheatreLinked = false;
		Movie m = null;
		Theatre t = null;
		Show show = null;
		if(addedBooking!=null)
		{
			show = showService.getShowById(showId); // get show Info
			 m = show.getMovie();
			if(m != null)
			{
				isMovieLinked = true;
				
			}
			 t = show.getTheatre();
			if(t!=null)
			{
				isTheatreLinked = true;
			}
		}
		
		
		if(isMovieLinked == false | isTheatreLinked == false)
		{
			throw new Exception("Movie or Theatre Not Linked");
		}
		
		if(m!=null && t!=null && show != null)
		{
			BookingResponseDTO dto = new BookingResponseDTO();
			
			dto.setTransactionId(addedBooking.getTransactionId());
			dto.setCustomerName(addedBooking.getCustomer().getCustomerName()+"");
			dto.setSeatsCount(bookingDTO.getSeatCount());
			dto.setTotalCostPaid(addedBooking.getTotalCost());
			dto.setMovieName(m.getMovieName());
			dto.setTheatreName(t.getTheatreName());
			dto.setTheatreAddress(t.getTheatreCity());
			dto.setShowDate(show.getShowDate()+"");
			dto.setShowStartTime(show.getShowStartTime()+"");
			dto.setShowEndTime(show.getShowEndTime()+"");
			dto.setTransactionStatus(addedBooking.getTransactionStatus());
			dto.setPaymentMode(addedBooking.getTransactionMode());
			
			
			return new ResponseEntity<BookingResponseDTO>(dto,HttpStatus.OK);
			
		}
		
		return null;
		
	}

	
	@GetMapping("/booking/findall")
	public ResponseEntity<List<BookingResponseDTO>> viewAllBookings() throws AccessForbiddenException, BookingNotFoundException {
		
		logger.info("-------List Of Bookings Fetched Successfully---------");
		List<Booking> booking=bookingService.viewBookingList();
		List<BookingResponseDTO> vBookingDTO=EntityDtoMapper.convertToDTOList(booking, BookingResponseDTO.class);
		return ResponseEntity.ok(vBookingDTO);
	}
	
	
	@GetMapping("/booking/view/{bookingId}")
	public ResponseEntity<BookingDTO> viewBooking(@PathVariable int bookingId)
			throws BookingNotFoundException {
		ResponseEntity<BookingDTO> response = null;
		
		try {
			Booking booking = bookingService.viewBooking(bookingId);
			BookingDTO resultDTO=EntityDtoMapper.convertToDTO(booking, BookingDTO.class);
			response = new ResponseEntity<>(resultDTO, HttpStatus.OK);
			logger.info("-------Screen Found---------");
		} catch (Exception e) {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			throw new BookingNotFoundException("Booking dosen't exist");
		}
		return response;
	}
	
	
	@GetMapping("/booking/view/{movieId}")
	public ResponseEntity<List<BookingDTO>> viewMovieByMovieId(@PathVariable int movieId)
			throws AccessForbiddenException, BookingNotFoundException {
		
		logger.info("-------Bookings With MovieId " + movieId + " Fetched Successfully---------");
		List<Booking> booking=bookingService.showAllBookings(movieId);
		List<BookingDTO> resultDTO=EntityDtoMapper.convertToDTOList(booking, BookingDTO.class);
		return ResponseEntity.ok(resultDTO);
	}

	
	@GetMapping("/booking/viewMovieByDate/{date}")
	public ResponseEntity<List<BookingDTO>> viewMovieByLocalDateBooking(
			@RequestParam("bookingDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date)
			throws AccessForbiddenException, BookingNotFoundException {
		
		logger.info("-------Bookings With Date " + date + " Fetched Successfully---------");
		List<Booking> booking=bookingService.showAllBookings(date);
		List<BookingDTO> resultDTO=EntityDtoMapper.convertToDTOList(booking, BookingDTO.class);
		return ResponseEntity.ok(resultDTO);
	}

	
	@GetMapping("/booking/cost/{bookingId}")
	public double TotalBookingCost(@PathVariable int bookingId)
			throws AccessForbiddenException, BookingNotFoundException {
		
		logger.info("-------Total Cost Of Booking displayed Successfully---------");
		return bookingService.calculateTotalCost(bookingId);
	}
	
//----------------------------movie-------------------------------------------------------------------
	
	
	
	@GetMapping("/user/movies/findall")
	public ResponseEntity<List<MovieDTO>> viewMovieList() throws MovieNotFoundException {

		logger.info("-------Movie List Fetched---------");
		List<Movie> movie=moviesService.viewMovieList();
		List<MovieDTO> resultDTO=EntityDtoMapper.convertToDTOList(movie, MovieDTO.class);
		return ResponseEntity.ok(resultDTO);
	}

	
	@GetMapping("/user/movies/view/{movieId}")
	public ResponseEntity<MovieDTO> viewMovie(@PathVariable int movieId)
			throws MovieNotFoundException {

		ResponseEntity<MovieDTO> response = null;
		try {
			Movie movie = moviesService.viewMovie(movieId);
			MovieDTO resultDTO=EntityDtoMapper.convertToDTO(movie, MovieDTO.class);
			response = new ResponseEntity<>(resultDTO, HttpStatus.OK);
			logger.info("-------Movie With Movie id " + movieId + " Found---------");
		} catch (Exception e) {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			throw new MovieNotFoundException("Movie with " + movieId + " id dosen't exist");
		}
		return response;
	}


	@GetMapping("/movies/view/byTheatre/{theatreId}")
	public ResponseEntity<List<MovieDTO>> viewMovieByTheatreId(@PathVariable int theatreId)  {
		logger.info("-------Movies With TheatreId " + theatreId + " Found---------");
		List<Movie> movie=moviesService.viewMovieList(theatreId);
		List<MovieDTO> resultDTO=EntityDtoMapper.convertToDTOList(movie, MovieDTO.class);
		return ResponseEntity.ok(resultDTO);
	}


	@GetMapping("/movies/view/byLocDate/{date}")
	public ResponseEntity<List<MovieDTO>> viewMovieByLocalDateMovie(
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
		logger.info("-------Movies With Date " + date + " Found---------");
		List<Movie> movie=moviesService.viewMovieList(date);
		List<MovieDTO> resultDTO=EntityDtoMapper.convertToDTOList(movie, MovieDTO.class);
		return ResponseEntity.ok(resultDTO);
	}
	
	
//-------------------------screen------------------------------------------------------------------------
	
	
	
	 @GetMapping("/screens/findall")
	    public ResponseEntity<List<ScreenResponseDTO>> viewScreenList() throws ScreenNotFoundException {
	        List<Screen> viewScreenList = screenService.viewScreenList();
	        List<ScreenResponseDTO> viewScreenListDTO = viewScreenList.stream()
	                .map(screen -> new ScreenResponseDTO(
	                        screen.getScreenId(),
	                        screen.getScreenName(),
	                        screen.getRows(),
	                        screen.getColumns(),
	                        screen.getTheatre().getTheatreId(), // Assuming these properties exist in your Screen entity
	                        screen.getTheatre().getTheatreName(),
	                        screen.getTheatre().getTheatreCity()
	                ))
	                .collect(Collectors.toList());
	        return ResponseEntity.ok(viewScreenListDTO);
	    }
	
	
	 @GetMapping("/screens/theatre/{screenId}")
	    public ResponseEntity<TheatreDTO2> getTheatreById(@PathVariable int screenId) throws ScreenNotFoundException {
	        try {
	            Theatre theatre = screenService.getTheatre(screenId);
	            TheatreDTO2 theatreDTO2 = new TheatreDTO2(
	                    theatre.getTheatreId(),
	                    theatre.getTheatreName(),
	                    theatre.getTheatreCity(),
	                    theatre.getManagerName(),
	                    theatre.getManagerContact()
	            );
	            return new ResponseEntity<>(theatreDTO2, HttpStatus.OK);
	        } catch (ScreenNotFoundException e) {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	    }

	    @GetMapping("/screens/viewScreen/{screenId}")
	    public ResponseEntity<ScreenResponseDTO> viewScreen(@PathVariable int screenId) throws ScreenNotFoundException {
	        try {
	            Screen screen = screenService.viewScreen(screenId);
	            ScreenResponseDTO viewScreenDTO = new ScreenResponseDTO(
	                    screen.getScreenId(),
	                    screen.getScreenName(),
	                    screen.getRows(),
	                    screen.getColumns(),
	                    screen.getTheatre().getTheatreId(), // Assuming these properties exist in your Screen entity
	                    screen.getTheatre().getTheatreName(),
	                    screen.getTheatre().getTheatreCity()
	            );
	            return new ResponseEntity<>(viewScreenDTO, HttpStatus.OK);
	        } catch (ScreenNotFoundException e) {
	            throw new ScreenNotFoundException("Screen doesn't exist");
	        }
	    }
	
	
	

//-------------------------------------theatre-----------------------------------------------------------
	    
	
	@PutMapping("/theatre/update")
	public ResponseEntity<List<TheatreDTO>> updateTheatre(@Valid @RequestBody TheatreDTO theatreDTO)
			throws  TheatreNotFoundException {

		logger.info("-------Theatre Updated Successfully---------");
		Theatre theatre=EntityDtoMapper.convertToEntity(theatreDTO, Theatre.class);
		List<Theatre> listTheatre=theatreservice.updateTheatre(theatre);
		List<TheatreDTO> listTheatreDTO=EntityDtoMapper.convertToDTOList(listTheatre, TheatreDTO.class);
		return ResponseEntity.ok(listTheatreDTO);
	}
	
	@GetMapping("/theatre/location/{location}")
	public ResponseEntity<List<TheatreDTO2>> getAlltheatresByLocation(@PathVariable String location) throws  TheatreNotFoundException {

		logger.info("-------Theatre List Fetched---------");
		List<Theatre> allTheatres=theatreservice.findByTheatreLocation(location);
		List<TheatreDTO2> allTheatresDTO2 = new ArrayList<>();
		
		for (Theatre t : allTheatres) {
			
			TheatreDTO2 temp = new TheatreDTO2(t.getTheatreId(),t.getTheatreName(), t.getTheatreCity(), 
					t.getManagerName(), t.getManagerContact());
			allTheatresDTO2.add(temp);
		}
		
		
		return ResponseEntity.ok(allTheatresDTO2);
	}
	
	
	@GetMapping("/theatre/all")
	public ResponseEntity<List<TheatreDTO2>> getAlltheatres() throws  TheatreNotFoundException {

		logger.info("-------Theatre List Fetched---------");
		List<Theatre> allTheatres=theatreservice.getAllTheatres();
		//List<TheatreDTO> allTheatresDTO=EntityDtoMapper.convertToDTOList(allTheatres, TheatreDTO.class);
		List<TheatreDTO2> allTheatresDTO2 = new ArrayList<>();
		
		for (Theatre t : allTheatres) {
			
			TheatreDTO2 temp = new TheatreDTO2(t.getTheatreId(),t.getTheatreName(), t.getTheatreCity(), 
					t.getManagerName(), t.getManagerContact());
			allTheatresDTO2.add(temp);
		}
		
		
		return ResponseEntity.ok(allTheatresDTO2);
	}

	
	@GetMapping("/theatre/find/{theatreId}")
	public ResponseEntity<TheatreDTO> findTheatre(@PathVariable int theatreId)
			throws  TheatreNotFoundException {

		logger.info("-------Theatre Found with Theatre id" + theatreId + "---------");
		Theatre theatre= theatreservice.findTheatres(theatreId);
		TheatreDTO findtheatreDTO=EntityDtoMapper.convertToDTO(theatre, TheatreDTO.class);
		return ResponseEntity.ok(findtheatreDTO);
		
	}

	
	@GetMapping("/theatre/findbyMovie/{movieId}")
	public ResponseEntity<List<TheatreDTO>> findTheatreByMovieId(@PathVariable int movieId)
			throws  TheatreNotFoundException {
		List<Theatre> theatre=theatreservice.findTheatresByMovie(movieId);
		List<TheatreDTO> byIdtheaatreDTO=EntityDtoMapper.convertToDTOList(theatre, TheatreDTO.class);
		return ResponseEntity.ok(byIdtheaatreDTO);
	}
	
	
//------------------------------seats-------------------------------------------------------	
	
	
	@GetMapping("/findallseat")
	public ResponseEntity<List<SeatResponseDTO>> viewSeatList() throws AccessForbiddenException, SeatNotFoundException {
		
		logger.info("-------List of Seats Fetched Successfully---------");
		List<Seat> seat=seatService.viewSeatList();
		List<SeatResponseDTO> viewSeatDTO=EntityDtoMapper.convertToDTOList(seat, SeatResponseDTO.class);
		return ResponseEntity.ok(viewSeatDTO);
	}
 
	
	@PutMapping("/updateseat")
	public ResponseEntity<SeatResponseDTO> updateSeat(@Valid @RequestBody SeatDTO seatDTO)
			throws AccessForbiddenException, SeatNotFoundException {
		ResponseEntity<SeatResponseDTO> response = null;
		Seat seat=EntityDtoMapper.convertToEntity(seatDTO, Seat.class);
		if (seat == null) {
			response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			seat = seatService.updateSeat(seat);
			SeatResponseDTO resultSeatDTO=EntityDtoMapper.convertToDTO(seat, SeatResponseDTO.class);
			response = new ResponseEntity<>(resultSeatDTO, HttpStatus.OK);
			logger.info("-------Seat Updated Successfully---------");
		}
		return response;
	}
 
	
	@PutMapping("/bookseat")
	public ResponseEntity<SeatDTO> BookASeat(@Valid @RequestBody SeatDTO seatDTO)
			throws AccessForbiddenException, SeatNotFoundException {
		Seat seat=EntityDtoMapper.convertToEntity(seatDTO, Seat.class);
		seat = seatService.bookSeat(seat);
		logger.info("-------Seat booking Successfull---------");
		SeatDTO resultSeatDTO=EntityDtoMapper.convertToDTO(seat, SeatDTO.class);
		return new ResponseEntity<>(resultSeatDTO, HttpStatus.OK);
	}
 
	
	@PutMapping("/cancelseat")
	public ResponseEntity<SeatResponseDTO> CancelASeat(@Valid @RequestBody SeatDTO seatDTO)
			throws AccessForbiddenException, SeatNotFoundException {
		Seat seat=EntityDtoMapper.convertToEntity(seatDTO, Seat.class);
		seat = seatService.cancelSeatBooking(seat);
		SeatResponseDTO resultSeatDTO=EntityDtoMapper.convertToDTO(seat, SeatResponseDTO.class);
		logger.info("-------Seat Cancellation Successfull---------");
		return new ResponseEntity<>(resultSeatDTO, HttpStatus.OK);
	}
 
	
	@PutMapping("/blockseat")
	public ResponseEntity<SeatResponseDTO> BloclASeat(@Valid @RequestBody SeatDTO seatDTO)
			throws AccessForbiddenException, SeatNotFoundException {
		Seat seat=EntityDtoMapper.convertToEntity(seatDTO, Seat.class);
		seat = seatService.blockSeat(seat);
		SeatResponseDTO resultSeatDTO=EntityDtoMapper.convertToDTO(seat, SeatResponseDTO.class);
		logger.info("-------Seat blocking Successfull---------");
		return new ResponseEntity<>(resultSeatDTO, HttpStatus.OK);
	}
	
//--------------------------shows-----------------------------------------------------------------------------
	
	
	
	
	@GetMapping("/shows/view/{showId}")
	public ResponseEntity<ShowResponeDTO> viewShow(@PathVariable int showId) throws ShowNotFoundException{
	    ResponseEntity<ShowResponeDTO> response = null;
	    
	    try {
	        Show viewShow = showService.viewShow(showId);
	        
	        if (viewShow != null) {
	        	ShowResponeDTO viewShowDTO = new ShowResponeDTO(
	                viewShow.getShowId(),
	                viewShow.getShowStartTime(),
	                viewShow.getShowEndTime(),
	                viewShow.getShowName(),
	                viewShow.getShowDate(),
	                viewShow.getTheatre().getTheatreId(),
	                viewShow.getTheatre().getTheatreName(),
	                viewShow.getTheatre().getTheatreCity(),
	                viewShow.getScreen().getScreenId()
	               
	                
	            );
	            
	            response = new ResponseEntity<>(viewShowDTO, HttpStatus.OK);
	            logger.info("-------Show with ShowId " + showId + " Found Successfully---------");
	        } else {
	            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
	            logger.info("-------Show with ShowId " + showId + " Not Found---------");
	        }
	    } catch (Exception e) {
	       
	        response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        logger.error("Error while processing viewShow", e);
	    }
	    
	    return response;
	}


	@GetMapping("/shows/findall")
	public ResponseEntity<List<ShowDTO>> viewShowList() {

		logger.info("-------List Of Shows Fetched Successfully---------");
		//Show allShows=EntityDtoMapper.convertToEntity(showDTO, Show.class);
		List<Show> allShows=showService.viewAllShows();
		List<ShowDTO> allShowsDTO=EntityDtoMapper.convertToDTOList(allShows, ShowDTO.class);
		return ResponseEntity.ok(allShowsDTO);
	}

	
	@GetMapping("/shows/show_theatre/{theatreId}")
	public ResponseEntity<List<ShowResponeDTO>> viewShowByTheatreId(@PathVariable int theatreId)throws ShowNotFoundException {
	    List<Show> viewShowByTid = showService.viewShowList(theatreId);
	    List<ShowResponeDTO> viewShowByTidDTO = viewShowByTid.stream()
	        .map(show -> new ShowResponeDTO(
	            show.getShowId(),
	            show.getShowStartTime(),
	            show.getShowEndTime(),
	            show.getShowName(),
	            show.getShowDate(),
	            show.getTheatre().getTheatreId(),
	            show.getTheatre().getTheatreName(),
	            show.getTheatre().getTheatreCity(),
	            show.getScreen().getScreenId()
	         
	            // Add other properties as needed
	        ))
	        .collect(Collectors.toList());
 
	    logger.info("-------List Of Shows With TheatreId " + theatreId + " Fetched Successfully---------");
	    return ResponseEntity.ok(viewShowByTidDTO);
	}
	@GetMapping("/shows/date/{date}")
	public ResponseEntity<List<ShowDTO>> viewShowByLocalDate(@PathVariable  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

		List<Show> viewShowByLocDate=showService.viewShowsList(date);
		List<ShowDTO> viewShowByLocDateDTO=EntityDtoMapper.convertToDTOList(viewShowByLocDate, ShowDTO.class);
		logger.info("-------List Of Shows With Date " + date + " Fetched Successfully---------");
		return ResponseEntity.ok(viewShowByLocDateDTO);
	}
	
//-----------------------------tickets---------------------------------------------------	
	
	@PostMapping("/tickets/add")
	public ResponseEntity<TicketDTO> addATicket(@Valid @RequestBody TicketDTO ticketDTO,@RequestParam(required = true) Integer bookingId)
			throws AccessForbiddenException, TicketNotFoundException {
		Ticket ticket=EntityDtoMapper.convertToEntity(ticketDTO, Ticket.class);
		Ticket addedTicket = ticketService.addTicket(ticket,bookingId);
		TicketDTO addedTicketDTO=EntityDtoMapper.convertToDTO(addedTicket, TicketDTO.class);
		logger.info("-------Ticked Created Successfully---------");
		return new ResponseEntity<>(addedTicketDTO, HttpStatus.CREATED);
	}
	
	@GetMapping("/tickets/findall")
	public ResponseEntity<List<TicketDTO>> viewTicketList() throws AccessForbiddenException, TicketNotFoundException {
		
		logger.info("-------List of Tickets Found Successfully---------");
		List<Ticket> viewTicket=ticketService.viewTicketList();
		List<TicketDTO> viewTicketDTO=EntityDtoMapper.convertToDTOList(viewTicket, TicketDTO.class);
		return ResponseEntity.ok(viewTicketDTO);
	}

	
	@GetMapping("/tickets/{ticketId}")
	public ResponseEntity<TicketDTO> findATicket(@PathVariable int ticketId) throws TicketNotFoundException, AccessForbiddenException {	
		Ticket t = null;
		TicketDTO tDTO=null;
		try {
			t = ticketService.findTicket(ticketId);
			tDTO=EntityDtoMapper.convertToDTO(t, TicketDTO.class);
			logger.info("-------Ticket with ticketId " + ticketId + " Foound Successfully---------");
		} catch (Exception e) {
			throw new TicketNotFoundException("Invalid Ticket ID");
		}
		return ResponseEntity.ok(tDTO);

	}

}