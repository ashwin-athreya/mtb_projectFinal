package com.cg.mts.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScreenDTO {
	
    private int screenId;
    @NotBlank(message = "Screen name is required")
    private String screenName;
    @Positive(message = "Number of rows must be a positive integer")
    private int rows;
    @Positive(message = "Number of columns must be a positive integer")
    private int columns;
 
   
}