package com.web.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table
public class DepWorldWeatherDTO extends BaseEntity  {
	
	@Id
	private String flightId;	//편명	
	
	private String airline; //항공사
	
	private String scheduleDateTime; //도착예정시간HHMM
	
	private String estimatedDateTime; //도착변경시간HHMM
	
	private String airport; //도착공항(목적지)
	private String airportCode; //도착공항코드	
	
	private String remark; //현황 (도착,결항,지연,회항,착륙)
	
	//날씨
	private String wimage; //날씨 이미지 url 경로
	private String himidity; //습도
	private String wind; 	//풍속
	private String temp; 	//기온
	private String senstemp; //체감기온

	
	//private String pageNo; 
	private String totalCount; 
	
	
	
	
	
}
