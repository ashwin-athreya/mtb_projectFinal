package com.cg.mts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.mts.entity.Theatre;

public interface TheatreRepository extends JpaRepository<Theatre, Integer> {

	public List<Theatre> getTheatreByTheatreCity(String theatreCity);
}
