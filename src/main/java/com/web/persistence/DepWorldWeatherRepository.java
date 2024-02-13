package com.web.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.domain.DepWorldWeatherDTO;

public interface DepWorldWeatherRepository extends JpaRepository<DepWorldWeatherDTO, String>{

}
