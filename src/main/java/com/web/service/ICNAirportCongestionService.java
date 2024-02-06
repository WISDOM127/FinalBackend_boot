package com.web.service;

import com.web.domain.ICNAirportPassengerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ICNAirportCongestionService {
	
	//api -> db 데이터 저장
	void updateData(ICNAirportPassengerDTO dto);

	//오늘의 출국장 대기인원 정보
	//List<ICNAirportPassengerDTO> getTodayPassenger();
    	
}