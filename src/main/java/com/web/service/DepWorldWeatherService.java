package com.web.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.web.domain.DepFlightDTO;

public interface DepWorldWeatherService {

	// api -> db 데이터 저장
	public void updateData(DepFlightDTO dto);

	// 테이블 안 모든 데이터 삭제
	public void deleteAllData();

	// 모든 인청발 항공편 리스트 출력
	//public List<DepFlightDTO> flightBoardList();


	//페이징
	public Page<DepFlightDTO> PageList(Pageable pageable);
	
	// 검색 - 편명
	public Page<DepFlightDTO> findByFlightId(String searchKeyword, Pageable pageable);

	// 검색 - 현황
	public Page<DepFlightDTO> findByRemarkContaining(String searchKeyword, Pageable pageable);
	
	// 검색 - 현황+편명
	public Page<DepFlightDTO> findByRemarkAndFlightId(String searchKeyword, Pageable pageable, String searchKeyword2);
	
}
