package com.web.controller;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

@EnableScheduling
@RestController
@RequestMapping("/api")
public class ICNAirportCongestionController {

    @Autowired
    private ICNAirportCongestionService congestionService;

    @Value("${dcongestion.data.api.key}")
    private String apiKey;

    //데이터 요청 메서드 getCongestionData()
    //@GetMapping("/congestionData")
    public List<JSONObject> getCongestionData() throws IOException {
        String apiUrl = "http://apis.data.go.kr/B551177/PassengerNoticeKR/getfPassengerNoticeIKR";
        String queryString = String.format("?serviceKey=%s&selectdate=0&type=json", URLEncoder.encode(apiKey, "UTF-8"));
        URL url = new URL(apiUrl + queryString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        try (BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
            	//response 에 api데이터가 끝날때까지 추가
                response.append(line);
            }
            
            //result에 파싱 처리 완료된 응답 데이터를 담아준다. 
            List<JSONObject> result = parseCongestionData(response.toString());

            //getCongestionData() 는 api 에 요청하고, 파싱 처리까지 완료된 응답 데이터를 반환함.
            return result;
            
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
                System.out.println("금일 출국장 혼잡도" + i +"시 ===========================================");
                System.out.println("adate : " + item.get("adate"));
                System.out.println("atime : " + item.get("atime"));
                System.out.println("t1sumset1 : " + item.get("t1sumset1"));
                System.out.println("t2sumset1 : " + item.get("t2sumset1"));

                result.add(item);
            }
                        
            System.out.println("resultCode : "+ header.get("resultCode"));
            System.out.println("resultMsg : "+ header.get("resultMsg"));            
            
            return result;
            
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }
    
    private ICNAirportPassengerDTO convertToDTO(JSONObject data) {
        ICNAirportPassengerDTO dto = new ICNAirportPassengerDTO();
        
        //시간대 가공 ex)2~3시 데이터 -> 02
        String time = data.get("atime").toString();
        String hourTime = time.substring(0, 2);
        System.out.println(hourTime);
        
    	String t1dep12 = xdot(data.get("t1sum5").toString());
    	String t1dep3 = xdot(data.get("t1sum6").toString());
    	String t1dep4 = xdot(data.get("t1sum7").toString());
    	String t1dep56 = xdot(data.get("t1sum8").toString());
    	String t2dep1 = xdot(data.get("t2sum3").toString());
    	String t2dep2 = xdot(data.get("t2sum4").toString());
        
        //일시
        dto.setAdate(data.get("adate").toString());		//20211130
        dto.setAtime(hourTime);
        //t1출국장
        dto.setT1sum5(t1dep12);
        dto.setT1sum6(t1dep3);
        dto.setT1sum7(t1dep4);
        dto.setT1sum8(t1dep56);
        //t2출국장
        dto.setT2sum3(t2dep1);
        dto.setT2sum4(t2dep2);        
       
//        json에서 받아온 string 타입 그대로 사용할 경우   
//        > dto.setT1sum6(data.get("t1sum6").toString());
        
        return dto;
    }

   
    //@Scheduled(fixedRate = 60 * 1000) //1분마다 실행
    //@GetMapping("/congestionData")
    @Scheduled(fixedRate = 30 * 60 * 1000) // 30분마다 실행
    public String updateCongestionData() {
    	String msg = "데이터 저장완료";
        try {
            List<JSONObject> congestionDataList = getCongestionData();

            for (JSONObject data : congestionDataList) {
                ICNAirportPassengerDTO dto = convertToDTO(data);
                
                congestionService.updateData(dto);
            }
            
            return msg;

        } catch (IOException e) {
            e.printStackTrace();
            msg = "저장오류";
            return msg;
        }
    }
    
    //"0.0" -> 0 int 형태로 파싱메서드
    public int dotToInt(String str) {
    	double doubleValue = Double.parseDouble(str);
    	int result = (int) doubleValue;
    	return result;
    }
    
    //"0.0" -> "0" 변환메서드
    public String xdot(String str) {
    	String result = str.substring(0, str.length()-2);
    	return result;
    }
    
    
    //시간대별 데이터 프론트 전송
    @PostMapping("/congestionData")
    public ICNAirportPassengerDTO congestionTimeData (@RequestBody String atime) {
    	
    	ICNAirportPassengerDTO dto = new ICNAirportPassengerDTO();
    	dto = congestionService.getTimeData(atime);
    	System.out.println(dto);
    	
    	System.out.println(dto.getT1sum6());
    	
    	//프론트에서 요청 온 시간(atime)에 해당하는 대기인원 데이터 반환
    	return dto;
    }
    



}