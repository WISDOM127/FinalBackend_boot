package com.web.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.web.domain.ICNAirportPassengerDTO;

public interface AirportDepRepository extends JpaRepository<ICNAirportPassengerDTO, String> {
	
	public ICNAirportPassengerDTO findByAtime(String atime);
	

}
