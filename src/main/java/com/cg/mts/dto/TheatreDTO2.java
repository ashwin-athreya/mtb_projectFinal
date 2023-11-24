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
public class TheatreDTO2 {
    private int theatreId;
    private String theatreName;
    private String theatreCity;
    private String managerName;
    private String managerContact;
    
}