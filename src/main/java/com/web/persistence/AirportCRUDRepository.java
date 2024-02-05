package com.web.persistence;

import org.springframework.data.repository.CrudRepository;

import com.web.domain.ICNAirportPassengerDTO;

public interface AirportCRUDRepository extends CrudRepository<ICNAirportPassengerDTO, String> {
	
	

}
