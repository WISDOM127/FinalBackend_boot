package com.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.domain.DepWorldWeatherDTO;
import com.web.persistence.DepWorldWeatherRepository;

@Service
public class DepWorldWeatherServiceImpl implements DepWorldWeatherService {

	@Autowired
	DepWorldWeatherRepository dRepo;
	
	@Override
	public void updateData(DepWorldWeatherDTO dto) {
		// TODO Auto-generated method stub
		dRepo.save(dto);
		
	}
	
}
