//package com.web.domain;
//
//
//import java.util.Date;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.Table;
//import jakarta.persistence.TableGenerator;
//import lombok.Data;
//import lombok.ToString;
//
//
//@Data
//@Entity
//@Table(name = "BOARD_COMMENTS")
//@TableGenerator(name="COMMENT_SEQ_GENERATOR",
//				table="ALL_SEQUENCE",
//				pkColumnValue="COMMENT_SEQ",
//				initialValue=0,
//				allocationSize = 1)
//public class BoardComments {
//	
//		@Id
//		@GeneratedValue(strategy = GenerationType.TABLE, generator = "COMMENT_SEQ")
//		private Long commentSeq;  //메모번호
//		private String commentContent; //내용
//		
//	    private Long boardNum; //글번호
//
//
//}
