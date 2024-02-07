package com.web.service;

import com.web.controller.ICNAirportCongestionController;
import com.web.domain.ICNAirportPassengerDTO;
import com.web.persistence.AirportDepRepository;

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
	private AirportDepRepository depRepo;

	//금일 출국장 대기인원 정보 -> db 저장
	@Override
	public void updateData(ICNAirportPassengerDTO dto) {
		depRepo.save(dto);
	};

	//시간대별 대기인원 정보 조회(attime 으로 조회) 
	@Override
	public ICNAirportPassengerDTO getTimeData(String atime) {
		// TODO Auto-generated method stub
		return depRepo.findByAtime(atime) ;
	}
	
	
	// 페이징
//	@Override
//	public Page<ICNAirportPassengerDTO> PageList(Pageable pageable) {
//		// TODO Auto-generated method stub
//		return ICNRepo.findAll(pageable);
//	}

}