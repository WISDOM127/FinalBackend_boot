package com.web.service;

import java.util.List;

import com.web.domain.DepFlightDTO;

public interface DepWorldWeatherService {

	//api -> db 데이터 저장
	public void updateData(DepFlightDTO dto);
	
	public void updateAll(List<DepFlightDTO> list);
	
	//테이블 안 모든 데이터 삭제
	public void deleteAllData();
	
	//모든 인청발 항공편 리스트 출력
	public List<DepFlightDTO> flightBoardList();
}
