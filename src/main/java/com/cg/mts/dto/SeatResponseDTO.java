package com.cg.mts.dto;

import com.cg.mts.entity.SeatStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatResponseDTO {
	private int seatId;
	private String seatNumber;
	private String type;
	private double price;
	private SeatStatus status;
    
    
	    
}