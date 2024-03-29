package com.web.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;

@Data
@Entity
@Table
public class ICNAirportPassengerDTO extends BaseEntity {
	
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "InfoNum_SEQ_GENERATOR")
//    private Long infoSeq;
	
	private String adate; //날짜
	
	@Id
	private String atime; //시간대 (1시간단위)
	
	@Column(name = "t1dep12")
	private String t1sum5;  // T1 출국장(1,2) 대기인원수
    
	@Column(name = "t1dep3")
	private String t1sum6;  // T1 출국장(3) 대기인원수
    
	@Column(name = "t1dep4")
	private String t1sum7;  // T1 출국장(4) 대기인원수
    
	@Column(name = "t1dep56")
	private String t1sum8;  // T1 출국장(5,6) 대기인원수
	
	
	@Column(name = "t2dep1")
	private String t2sum3;  // T2 출국장(1) 대기인원수
	
	@Column(name = "t2dep2")
	private String t2sum4;  // T2 출국장(2) 대기인원수
	
	

}
