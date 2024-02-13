package com.web.service;

import com.web.domain.DepWorldWeatherDTO;

public interface DepWorldWeatherService {

	//api -> db 데이터 저장
	public void updateData(DepWorldWeatherDTO dto);
	
}
