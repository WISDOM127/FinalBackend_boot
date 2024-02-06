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
    
    private ICNAirportPassengerDTO convertToDTO(JSONObject data) {
        ICNAirportPassengerDTO dto = new ICNAirportPassengerDTO();
        dto.setAdate(data.get("adate").toString());
        dto.setAtime(data.get("atime").toString());
        dto.setT1sum5(data.get("t1sum5").toString());
        dto.setT1sum6(data.get("t1sum6").toString());
        dto.setT1sum7(data.get("t1sum7").toString());
        dto.setT1sum8(data.get("t1sum8").toString());
       
//        dto.setT1sum8(Integer.parseInt(data.get("t1sum8").toString()));     
//        dto.setT1sum6(data.get("t1sum6").toString());
        
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



}