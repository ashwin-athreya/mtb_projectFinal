package com.cg.mts.dto;

import java.time.LocalDate;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponseDTO {
   
	private int transactionId;
	private String customerName;
	private int seatsCount;
	private double totalCostPaid;
	private String movieName;
	private String theatreName;
	private String theatreAddress;
	private String showDate;
	private String showStartTime;
	private String showEndTime;
	private String transactionStatus;
	private String paymentMode;
    

}
