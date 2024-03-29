package com.web.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.domain.DepFlightDTO;
import com.web.domain.ICNAirportPassengerDTO;
import com.web.service.DepWorldWeatherService;

@EnableScheduling
@RestController
@RequestMapping("/airRoute")
public class WorldWeatherController {

	@Autowired
	private DepWorldWeatherService weatherService;

	@Value("${dweather.data.api.key}")
	private String apiKey;

	public String nowTime() {
		// String onehourago = "";

		LocalDateTime date = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmm");
		String now = date.format(formatter);
		System.out.println(now);

		return now;
	}

	// 데이터 요청 메서드
	// @GetMapping("/congestionData")
	public List<JSONObject> getDepWeatherData() throws IOException {
		String now = nowTime();
		String apiUrl = "http://apis.data.go.kr/B551177/StatusOfPassengerWorldWeatherInfo/getPassengerDeparturesWorldWeather";
//		전체데이터 queryString 
		String queryString = String.format(
				"?serviceKey=%s&numOfRows=1000&pageNo=1&from_time=0000&to_time=2400&lang=K&type=json",
				URLEncoder.encode(apiKey, "UTF-8"));
		// 시작시간 따로 지정
//		String queryString = String.format("?serviceKey=%s&numOfRows=1000&pageNo=1&to_time=2400&lang=K&type=json",
//				URLEncoder.encode(apiKey, "UTF-8"));
//		String fromTime = String.format("&from_time=%s", URLEncoder.encode(now, "UTF-8"));
		URL url = new URL(apiUrl + queryString);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		try (BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
			StringBuilder response = new StringBuilder();
			String line;
			while ((line = rd.readLine()) != null) {
				// response 에 api데이터가 끝날때까지 추가
				response.append(line);
			}

			// result에 파싱 처리 완료된 응답 데이터를 담아준다.
			List<JSONObject> result = parseCongestionData(response.toString());

			// getCongestionData() 는 api 에 요청하고, 파싱 처리까지 완료된 응답 데이터를 반환함.
			return result;

		} finally {
			conn.disconnect();
		}
	}

	// 데이터 요청 메서드2 편명검색
	@PostMapping("/searchFlight")
	public List<JSONObject> getFlightSearch(@RequestBody String flightSearch) throws IOException {
		String now = nowTime();

		String apiUrl = "http://apis.data.go.kr/B551177/StatusOfPassengerWorldWeatherInfo/getPassengerDeparturesWorldWeather";
		// String queryString =
		// String.format("?serviceKey=%s&numOfRows=10&pageNo=1&from_time=0000&to_time=2400&lang=K&type=json",
		// URLEncoder.encode(apiKey, "UTF-8"));
		String queryString = String.format("?serviceKey=%s&numOfRows=5&pageNo=1&to_time=2400&lang=K&type=json",
				URLEncoder.encode(apiKey, "UTF-8"));
		String fromTime = String.format("&from_time=%s", URLEncoder.encode(now, "UTF-8"));
		String flightId = String.format("&flight_id=%s", URLEncoder.encode(flightSearch, "UTF-8"));
		URL url = new URL(apiUrl + queryString + fromTime + flightId);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		// DepFlightDTO dto = new DepFlightDTO();

		try (BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
			StringBuilder response = new StringBuilder();
			String line;
			while ((line = rd.readLine()) != null) {
				// response 에 api데이터가 끝날때까지 추가
				response.append(line);
			}
			// result에 파싱 처리 완료된 응답 데이터를 담아준다.
			List<JSONObject> flightData = parseCongestionData(response.toString());

//                for (JSONObject data : flightData) {
//              	  dto = convertToDTO(data); dto타입으로변환
//              	  }
			return flightData;

		} finally {
			conn.disconnect();
		}
	}

	// JSON 데이터 파싱 메서드
	private List<JSONObject> parseCongestionData(String responseData) {
		JSONParser jsonParser = new JSONParser();
		List<JSONObject> result = new ArrayList<>();

		try {
			JSONObject jsonObject = (JSONObject) jsonParser.parse(responseData);
			JSONObject responseBody = (JSONObject) jsonObject.get("response");
			JSONObject header = (JSONObject) responseBody.get("header");
			JSONObject body = (JSONObject) responseBody.get("body");
			JSONArray items = (JSONArray) body.get("items");

			for (int i = 0; i < items.size(); i++) {
				JSONObject item = (JSONObject) items.get(i);
				System.out.println("데이터출력테스트" + i + "시 ===========================================");
				System.out.println("항공사 : " + item.get("airline"));
				System.out.println("편명 : " + item.get("flightId"));
				System.out.println("도착지공항 : " + item.get("airport"));
				System.out.println("예정시간 : " + item.get("scheduleDateTime"));
				System.out.println("변경시간 : " + item.get("estimatedDateTime"));
				System.out.println("현황 : " + item.get("remark"));
				System.out.println("체크인카운터 : " + item.get("chkinrange"));
				System.out.println("탑승구 : " + item.get("gatenumber"));

				result.add(item);
			}

			System.out.println("resultCode : " + header.get("resultCode"));
			System.out.println("resultMsg : " + header.get("resultMsg"));
			System.out.println("데이터항목수 : " + body.get("totalCount"));

			return result;

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return Collections.emptyList();
	}

	// JSONObject-> DTO
	private DepFlightDTO convertToDTO(JSONObject data) {
		DepFlightDTO dto = new DepFlightDTO();

		// String airline = data.get("airline").toString();
		String airline = getStringOrNull(data, "airline");
		String flightId = getStringOrNull(data, "flightId");
		String airport = getStringOrNull(data, "airport");
		String airportCode = getStringOrNull(data, "airportCode");
		String scheduleDateTime = getStringOrNull(data, "scheduleDateTime");
		String estimatedDateTime = getStringOrNull(data, "estimatedDateTime");
		String remark = getStringOrNull(data, "remark");
		String chkinrange = getStringOrNull(data, "chkinrange");
		String gatenumber = getStringOrNull(data, "gatenumber");
		String wimage = getStringOrNull(data, "wimage");
		String himidity = getStringOrNull(data, "himidity");
		String wind = getStringOrNull(data, "wind");
		String temp = getStringOrNull(data, "temp");
		String senstemp = getStringOrNull(data, "senstemp");

		// 일시
		dto.setAirline(airline);
		dto.setFlightId(flightId);
		dto.setAirport(airport);
		dto.setScheduleDateTime(scheduleDateTime);
		dto.setEstimatedDateTime(estimatedDateTime);
		dto.setRemark(remark);
		dto.setChkinrange(chkinrange);
		dto.setGatenumber(gatenumber);
		dto.setWimage(wimage);
		dto.setHimidity(himidity);
		dto.setWind(wind);
		dto.setTemp(temp);
		dto.setSenstemp(senstemp);

//        json에서 받아온 string 타입 그대로 사용할 경우   
//        > dto.setT1sum6(data.get("t1sum6").toString());

		return dto;
	}

	private String getStringOrNull(JSONObject data, String key) {
		Object value = data.get(key);
		return value != null ? value.toString() : null;
	}

	 //@Scheduled(fixedRate = 60 * 60 * 1000) // 1시간마다 실행
	//@Scheduled(cron = "0 5 * * * *") // 매시간 5분 간격으로 실행
	 @Scheduled(cron = "0 0/30 * * * *") // 매시간 30분 간격으로 실행
	// @Scheduled(cron = "0 0 * * * *") // 매시간 정각에 실행
	public String updateCongestionData() {
		String msg = "데이터 저장완료";
		// 리셋후 새 데이터 호출
		try {
			weatherService.deleteAllData();
			List<JSONObject> weatherDataList = getDepWeatherData();

			for (JSONObject data : weatherDataList) {
				DepFlightDTO dto = convertToDTO(data);
				weatherService.updateData(dto);
			}

			return msg;

		} catch (IOException e) {
			e.printStackTrace();
			msg = "저장오류";
			return msg;
		}
	}

	// 데이터 삭제 메서드
	@Scheduled(cron = "0 0 0 * * ?") // 매일 밤 12시 정각 실행
	public String resetAll() {
		String msg = "";
		weatherService.deleteAllData();

		return msg;
	}

//  //모든 항공편 리스트
//  @GetMapping("/getBoardList")
//	public List<DepFlightDTO> BoardList() {
//		List<DepFlightDTO> list = weatherService.flightBoardList();
//		return list;
//	}

//항공편 조회 페이지----------------------------------------------------------------------

	// 항공편 전체 리스트
	@GetMapping("/getBoardList")
	public Page<DepFlightDTO> BoardList(
			@PageableDefault(size = 10, sort = "scheduleDateTime", direction = Direction.ASC) Pageable pageable) {
		System.out.println(pageable);

		Page<DepFlightDTO> PageList;
		PageList = weatherService.PageList(pageable);

		System.out.println(PageList);
		System.out.println(PageList.toList().size()); // 검색된 DTO 개수
		return PageList;
	}

	// 편명검색 시
	@GetMapping("/getKeywordList")
	public Page<DepFlightDTO> KeywordList(
			@PageableDefault(size = 10, sort = "scheduleDateTime", direction = Direction.ASC) Pageable pageable,
			@RequestParam String searchText) {
		System.out.println(pageable);
		System.out.println(searchText);

//		Map<String, Object> params = (Map<String, Object>) requestBody.get("params");
//		String searchText = (String) params.get("searchText");
		String CheckText = "출발";

		Page<DepFlightDTO> PageList;
		Page<DepFlightDTO> searchByFlightId = weatherService.findByFlightId(searchText, pageable);
		Page<DepFlightDTO> checkByRemark = weatherService.findByRemarkContaining(CheckText, pageable);

		PageList = searchByFlightId;

		System.out.println(PageList.toList().size()); // 검색된 DTO 개수

		return PageList;
	}

	// remark = "현황"
	@GetMapping("/getCheckinList")
	public Page<DepFlightDTO> getCheckinList(
			@PageableDefault(size = 10, sort = "scheduleDateTime", direction = Direction.ASC) Pageable pageable,
			@RequestParam String searchText, @RequestParam int remark) {
		System.out.println(searchText);
		System.out.println("anjdi" + remark);
		
		String remarkText = "";
		if (remark == 1) {
			remarkText = "체크인";
		} else if (remark == 2) {
			remarkText = "지연";
		} else {
			remarkText = "결항";
		}

		Page<DepFlightDTO> PageList;
		Page<DepFlightDTO> checkByRemark = weatherService.findByRemarkContaining(remarkText, pageable);
		Page<DepFlightDTO> searchList = weatherService.findByRemarkAndFlightId(remarkText, pageable, searchText);

		if (searchText != null && !searchText.isEmpty()) {
			PageList = searchList;
		} else {
			PageList = checkByRemark;
		}

		System.out.println(PageList.toList().size()); // 검색된 DTO 개수

		return PageList;
	}

	
	
	

}
