package com.web.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.web.domain.Board;

public interface BoardService {
	
	//게시판 전체 리스트 
	public Page<Board> BoardList(Pageable pageable);
	
	//글생성
	public Board BoardCreate(Board boardDTO);
	//상세보기 
	public Board BoardDetail(Long boardSeq);
	//글삭제 
	public void BoardDelete(Long boardSeq);
	//글수정
	public Board Edit(Board boardDTO);
	//글비번찾기
	public String FindBoardPw(Long boardSeq);

//	//덧글입력
	
}
