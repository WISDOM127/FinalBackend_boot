package com.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.domain.Board;
import com.web.service.BoardService;

@RestController
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@PostMapping("/BoardCreate") 
	public Board BoardCreate(@RequestBody Board boardDTO) {
		return boardService.BoardCreate(boardDTO);
	}
	
	//게시판 전체 리스트 출력
	@GetMapping("/BoardList")
	public Page<Board> BoardList(@PageableDefault(size = 10, sort = "boardSeq", direction = Direction.DESC) Pageable pageable) {
		Page<Board> list = boardService.BoardList(pageable);
//		
//		Map<String,Object> map = new HashMap<>();
//		map.put("list", list);
		return list;
	}
	
	// 게시판 상세정보 
	@GetMapping("/BoardDetail/{boardSeq}")
	public Board BoardDetail(@PathVariable Long boardSeq){
		return boardService.BoardDetail(boardSeq);
	}
	
	// 글 비번 찾기getBoardPw
	@GetMapping("/getBoardPw/{boardSeq}")
	public String getBoardPw(@PathVariable Long boardSeq){
		System.out.println("비번찾기"+boardService.FindBoardPw(boardSeq));
		return boardService.FindBoardPw(boardSeq);
	}
	
	// 삭제
	@DeleteMapping("/BoardDelete/{boardSeq}")
	public void BoardDelete(@PathVariable Long boardSeq) {
		System.out.println("삭제"+boardSeq);
		 boardService.BoardDelete(boardSeq);
	}
	
	// 수정
	@PostMapping("/Edit/{boardSeq}")
	public Board Edit(@PathVariable Long boardSeq,@RequestBody Board boardDTO) {
		System.out.println("번호 ="+boardSeq);
		System.out.println("객체 ="+boardDTO);
		return boardService.Edit(boardDTO);
	}
	
	


}
