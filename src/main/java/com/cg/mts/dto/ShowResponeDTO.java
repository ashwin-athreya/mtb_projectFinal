package com.cg.mts.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowResponeDTO {
   
	private int ShowId;
	private LocalDateTime showStartTime;
	private LocalDateTime showEndTime;
	private String showName;
	private LocalDate showDate;
	private int theatreId;
	private String theatreName;
	private String theatreCity;
	private int screenId;
}