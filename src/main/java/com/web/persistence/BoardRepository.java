package com.web.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.domain.Board;
import com.web.domain.ICNAirportPassengerDTO;

public interface BoardRepository extends JpaRepository<Board, Long> {
	
	public Board findByBoardSeq(Long seq);

}
