package com.web.controller;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.domain.ICNAirportPassengerDTO;
import com.web.service.ICNAirportCongestionService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ICNAirportCongestionController {

    @Autowired
    private ICNAirportCongestionService congestionService;

    @Value("${congestion.data.api.key}")
    private String apiKey;

    @GetMapping("/congestionData")
    public List<JSONObject> getCongestionData() throws IOException {
        String apiUrl = "http://apis.data.go.kr/B551177/PassengerNoticeKR/getfPassengerNoticeIKR";
        String queryString = String.format("?serviceKey=%s&selectdate=0&type=json", URLEncoder.encode(apiKey, "UTF-8"));
        URL url = new URL(apiUrl + queryString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        try (BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
            }
            
            List<JSONObject> result = parseCongestionData(response.toString());

            return result;
            
        } finally {
            conn.disconnect();
        }
    }

    private List<JSONObject> parseCongestionData(String responseData) {
        JSONParser jsonParser = new JSONParser();
        List<JSONObject> result = new ArrayList<>();

        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(responseData);
            JSONObject responseBody = (JSONObject) jsonObject.get("response");
            JSONObject body = (JSONObject) responseBody.get("body");
            JSONArray items = (JSONArray) body.get("items");

            for (int i = 0; i < items.size(); i++) {
                JSONObject item = (JSONObject) items.get(i);
                System.out.println("금일 혼잡도데이터" + i + " ===========================================");
                System.out.println("adate : " + item.get("adate"));
                System.out.println("atime : " + item.get("atime"));
                System.out.println("t1sum5 : " + item.get("t1sum5"));
                System.out.println("t1sum6 : " + item.get("t1sum6"));
                System.out.println("");

                result.add(item);
            }

            return result;
            
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    //@Scheduled(fixedRate = 30 * 60 * 1000) // 30분마다 실행
    //@Scheduled(fixedRate = 60 * 1000)
    public void updateCongestionData() {
        try {
            List<JSONObject> congestionDataList = getCongestionData();

            for (JSONObject data : congestionDataList) {
                ICNAirportPassengerDTO dto = convertToDTO(data);
                congestionService.updateData(dto);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ICNAirportPassengerDTO convertToDTO(JSONObject data) {
        ICNAirportPassengerDTO dto = new ICNAirportPassengerDTO();
        dto.setAdate(data.get("adate").toString());
        dto.setAtime(data.get("atime").toString());
        dto.setT1sum5(data.get("t1sum5").toString());
        dto.setT1sum6(data.get("t1sum6").toString());
        return dto;
    }


//
//@RequestMapping("/api")
//@RestController
//public class ICNAirportCongestionController {
//	
//	@Autowired
//	private ICNAirportCongestionService congestionService;
//
//	@Value("${congestion.data.api.key}")
//	private String apiKey;
//
//	@GetMapping("/api/congestionData")
//	public List<JSONObject> congestionData() throws IOException {
//		StringBuilder urlBuilder = new StringBuilder(
//				"http://apis.data.go.kr/B551177/PassengerNoticeKR/getfPassengerNoticeIKR"); /* URL */
//		urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + apiKey); /* Service Key */
//		urlBuilder.append(
//				"&" + URLEncoder.encode("selectdate", "UTF-8") + "=0"); /* 오늘일자(D) ='0', 내일일자(D+1) ='1', default = 0 */
//		urlBuilder.append("&" + URLEncoder.encode("type", "UTF-8") + "=json"); /* 응답유형 [xml, json] default=xml */
//
//		URL url = new URL(urlBuilder.toString());
//		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//
//		try (BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
//			StringBuilder sb = new StringBuilder();
//			String line;
//			while ((line = rd.readLine()) != null) {
//				sb.append(line);
//			}
//			// System.out.println(sb.toString()); // 요청한 공공데이터 응답값 --> sb 객체에 담김
//
//			// JSONParser를 통해 파싱한 응답 데이터를 넣어 JSON Object 로 만들어 준다.
//			JSONParser jsonParser = new JSONParser();
//			JSONObject jsonObject;
//
//			try {
//				jsonObject = (JSONObject) jsonParser.parse(sb.toString());
//
//				// 배열 추출 : response -> body -> items
//				JSONObject responseBody = (JSONObject) jsonObject.get("response");
//				JSONObject body = (JSONObject) responseBody.get("body");
//				JSONArray array = (JSONArray) body.get("items");
//
//				List<JSONObject> result = new ArrayList<>();
//
//				for (int i = 0; i < array.size(); i++) {
//					JSONObject object = (JSONObject) array.get(i);
//					System.out.println("금일 혼잡도데이터" + i + " ===========================================");
//					System.out.println("adate : " + object.get("adate"));
//					System.out.println("atime : " + object.get("atime"));
//					System.out.println("t1sum5 : " + object.get("t1sum5"));
//					System.out.println("t1sum6 : " + object.get("t1sum6"));
//					System.out.println("");
//					//result 에 key value 형태로 저장된 object 모두 저장
//					result.add(object);
//				}
//
//				return result;
//				//key value 형태로 저장된 object 모두 반환
//
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//		} finally {
//			conn.disconnect();
//		}
//		return null;
//
//	}
//	
//
//	// 30분 주기로 DB 저장
//	@Scheduled(fixedRate = 30 * 60 * 1000) // 30분마다 실행
//	public void updateCongestionData() {
//		try {
//			// 공공데이터 호출 및 저장 로직
//			List<JSONObject> congestionDataList = congestionData();
//
//			// DB에 저장
//			for (JSONObject data : congestionDataList) {
//				ICNAirportPassengerDTO dto = convertToEntity(data);
//				congestionService.updateData(dto);
//			}
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	// JSON 데이터를 엔터티로 변환하는 메서드
//    private ICNAirportPassengerDTO convertToEntity(JSONObject data) {
//        // 엔터티 생성 및 필드 설정 로직 작성
//    	ICNAirportPassengerDTO dto = new ICNAirportPassengerDTO();
//    	dto.setAdate(data.get("adate").toString());
//    	dto.setAtime(data.get("atime").toString());
//    	dto.setT1sum5(data.get("t1sum5").toString());
//    	dto.setT1sum6(data.get("t1sum6").toString());
//    	
//    	//dto.setT1sum5(Double.valueOf(data.get("t1sum5").toString()));
//
//         return dto;
//    }



//    private final ICNAirportCongestionService congestionService;

//    @Autowired
//    public ICNAirportCongestionController(ICNAirportCongestionService congestionService) {
//        this.congestionService = congestionService;
//    }
//
//    @GetMapping("/passenger-notice")
//    public List<ICNAirportPassengerDTO> getTodayPassenger() {
//        return congestionService.getTodayPassenger();
//    }

}