//package com.web.persistence;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpMethod;
//import org.springframework.stereotype.Repository;
//import org.springframework.web.reactive.function.client.WebClient;
//
//import com.web.domain.ICNAirportPassengerDTO;
//
//@Repository
//public class ICNAirportCongestionRepository {
//			
//	@Value("${congestion.data.api.key}")
//	private String apiKey;  // application.properties 에 키설정
//	
//    private final String API_BASE_URL = "http://apis.data.go.kr/B551177/PassengerNoticeKR/getfPassengerNoticeIKR?serviceKey=" +apiKey ;
//
//	//오늘의 시간대별 모든 데이터 요청 -> 리스트 받기	
//    public List<ICNAirportPassengerDTO> getTodayPassenger() {
//        // API 호출에 필요한 파라미터 설정 : 요청변수 selectDate "0"일 때 오늘 "1"일 때 내일 /type=json
//        String urlParam = String.format("?serviceKey=%s&selectdate=0&type=json", apiKey);
//
//        // 요청 최종 URL
//        String apiUrl = API_BASE_URL + urlParam;
//
//    	WebClient webClient = WebClient.create();
//
//    	// API 호출 및 응답 매핑
//        List<ICNAirportPassengerDTO> result = webClient
//                .method(HttpMethod.GET)
//                .uri(apiUrl)
//                .retrieve()
//                .bodyToFlux(ICNAirportPassengerDTO.class)
//                .collectList()
//                .block(); // 응답 받을 때 까지 block 처리
//
//        return result;
//        }
//    }
       

	
