package com.web.service;

import com.web.domain.ICNAirportPassengerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ICNAirportCongestionService {
	
	//api -> db 데이터 저장
	public void updateData(ICNAirportPassengerDTO dto);

	//시간대별 대기인원 정보 조회(attime 으로 조회) -> dto 반환
	public ICNAirportPassengerDTO getTimeData(String atime);
	 	
}