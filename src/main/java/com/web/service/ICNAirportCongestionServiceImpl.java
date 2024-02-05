package com.web.service;

import com.web.controller.ICNAirportCongestionController;
import com.web.domain.ICNAirportPassengerDTO;
import com.web.persistence.AirportCRUDRepository;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ICNAirportCongestionServiceImpl implements ICNAirportCongestionService {

	@Autowired
	private AirportCRUDRepository airportCRUDRepo;

	// 오늘의 모든 출국장 대기인원 정보 -> db 데이터 업데이트
	@Override
	public void updateData(ICNAirportPassengerDTO dto) {
		airportCRUDRepo.save(dto);
	};

	
	// 페이징
//	@Override
//	public Page<ICNAirportPassengerDTO> PageList(Pageable pageable) {
//		// TODO Auto-generated method stub
//		return ICNRepo.findAll(pageable);
//	}

}