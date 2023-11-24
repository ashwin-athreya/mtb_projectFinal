package com.cg.mts.dto;

import java.util.List;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TheatreDTO {
    private int theatreId;
    @Size(max = 50, message = "Theatre name must be at most 50 characters")
    private String theatreName;
    @Size(max = 20, message = "Theatre city must be at most 20 characters")
    private String theatreCity;
    @Size(max = 30, message = "Manager name must be at most 30 characters")
    private String managerName;
    @Pattern(regexp = "\\d{10}", message = "Manager contact must be a 10-digit number")
    private String managerContact;
    @Size(max = 3, message = "A theatre can have at most 3 screens")
    private List<ScreenDTO> screens;
    private List<ShowDTO> shows;
	public TheatreDTO(int theatreId,
			@Size(max = 50, message = "Theatre name must be at most 50 characters") String theatreName,
			@Size(max = 20, message = "Theatre city must be at most 20 characters") String theatreCity,
			@Size(max = 30, message = "Manager name must be at most 30 characters") String managerName,
			@Pattern(regexp = "\\d{10}", message = "Manager contact must be a 10-digit number") String managerContact) {
		super();
		this.theatreId = theatreId;
		this.theatreName = theatreName;
		this.theatreCity = theatreCity;
		this.managerName = managerName;
		this.managerContact = managerContact;
	}
    
    
    
}