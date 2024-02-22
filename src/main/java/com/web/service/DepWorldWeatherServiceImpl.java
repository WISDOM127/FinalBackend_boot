package com.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	public void deleteAllData() {
		// TODO Auto-generated method stub
		dRepo.deleteAllInBatch();
	}
	
	//모든 인청발 항공편 리스트 출력
//	@Override
//	public List<DepFlightDTO> flightBoardList() {
//		// 예정시간순으로 오름차순 정렬 후 출력 
//		return dRepo.findAll(Sort.by(Sort.Direction.ASC, "scheduleDateTime"));
//	}
	
//	
	//페이징 : 인청발 항공편 리스트 
	@Override
	public Page<DepFlightDTO> PageList(Pageable pageable) {
		// 예정시간순으로 오름차순 정렬 후 출력 
		return dRepo.findAll(pageable);
	}
	
	//검색 -----------------------------------------------------------------
	@Override
	public Page<DepFlightDTO> findByFlightId(String searchKeyword, Pageable pageable) {
		// TODO Auto-generated method stub
		return dRepo.findByFlightIdContaining(searchKeyword, pageable);
	}
	
	@Override
	public Page<DepFlightDTO> findByRemarkContaining(String searchKeyword, Pageable pageable) {
		// TODO Auto-generated method stub
		return dRepo.findByRemarkContaining(searchKeyword, pageable);
	}
	
	@Override
	public Page<DepFlightDTO> findByRemarkAndFlightId(String searchKeyword, Pageable pageable, String searchKeyword2) {
		// TODO Auto-generated method stub
		return dRepo.findByRemarkContainingAndFlightIdContaining(searchKeyword, pageable, searchKeyword2);
	}
}
