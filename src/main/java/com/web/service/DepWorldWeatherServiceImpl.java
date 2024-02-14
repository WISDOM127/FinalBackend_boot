package com.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.web.domain.DepFlightDTO;
import com.web.persistence.DepFlightRepository;

@Service
public class DepWorldWeatherServiceImpl implements DepWorldWeatherService {

	@Autowired
	DepFlightRepository dRepo;
	
	@Override
	public void updateData(DepFlightDTO dto) {
		// TODO Auto-generated method stub
		dRepo.save(dto);
		
	}
	
	@Override
	public void updateAll(List<DepFlightDTO> list) {
		// TODO Auto-generated method stub
		dRepo.saveAll(list);
		
	}
	
	@Override
	public void deleteAllData() {
		// TODO Auto-generated method stub
		dRepo.deleteAllInBatch();
	}
	
	//모든 인청발 항공편 리스트 출력
	@Override
	public List<DepFlightDTO> flightBoardList() {
		// TODO Auto-generated method stub
		return dRepo.findAll(Sort.by(Sort.Direction.ASC, "scheduleDateTime"));
	}
	
}
