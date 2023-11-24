package com.cg.mts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScreenResponseDTO {
	
    private int screenId;
    private String screenName;
    private int rows;
    private int columns;
 
    private int theatreId;
    private String theatreName;
    private String theatreCity;
    
	public ScreenResponseDTO(String screenName, int rows, int columns, int theatreId, String theatreName,
			String theatreCity) {
		super();
		this.screenName = screenName;
		this.rows = rows;
		this.columns = columns;
		this.theatreId = theatreId;
		this.theatreName = theatreName;
		this.theatreCity = theatreCity;
	}
}