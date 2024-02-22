package com.web.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.web.domain.Board;
import com.web.persistence.BoardRepository;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private BoardRepository boardRepository;
	
	@Override
	public Board BoardCreate(Board boardDTO) {
		// TODO Auto-generated method stub
		return boardRepository.save(boardDTO);
	}
	
	//게시판 전체 리스트 출력
	@Override
	public Page<Board> BoardList(Pageable pageable) {
		// TODO Auto-generated method stub
		Page<Board> paging = boardRepository.findAll(pageable); 
		return paging;
	}
	@Override
	public Board BoardDetail(Long boardSeq) {
		// TODO Auto-generated method stub
		Optional<Board> optional = boardRepository.findById(boardSeq);
		// findById 로 찾으면 타입이 optional 이된다  isPresent() -> boolean 타입으로 거짓이면 null
		if(optional.isPresent()) {
			Board boardDTO = optional.get();
			boardDTO.setBoardViews(boardDTO.getBoardViews()+1); // 조회수 증가
			boardRepository.save(boardDTO);
			System.out.println(boardDTO);
			return boardDTO;
		}
		return null;
	} 
	@Override
	public void BoardDelete(Long boardSeq) {
		// TODO Auto-generated method stub
	boardRepository.deleteById(boardSeq);
	}
	@Override
	public Board Edit(Board boardDTO) {
		// TODO Auto-generated method stub
		return boardRepository.save(boardDTO);
	}
	
	@Override
	public String FindBoardPw(Long boardSeq) {
		// TODO Auto-generated method stub
		Optional<Board> optionalBoard = boardRepository.findById(boardSeq);
		
		// boardSeq에 해당하는 행이 존재하는지 확인
	    if (optionalBoard.isPresent()) {
	        Board board = optionalBoard.get();
	        return board.getBoardPw();
	    } else {
	        return "불일치";
	    }
		
	}
	
	
}
