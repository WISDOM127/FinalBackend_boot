package com.web.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.service.DepWorldWeatherService;

@RestController
@RequestMapping("/api")
public class WorldWeatherController {
	
	
	@Autowired
	private DepWorldWeatherService weatherService;
	
	@Value("${dweather.data.api.key}")
    private String apiKey;
	
	//데이터 요청 메서드 
    //@GetMapping("/congestionData")
    public List<JSONObject> getDepWeatherData() throws IOException {
        String apiUrl = "http://apis.data.go.kr/B551177/StatusOfPassengerWorldWeatherInfo/getPassengerDeparturesWorldWeather";
        String queryString = String.format("?serviceKey=%s&from_time=0000&to_time=2400&lang=K&type=json", URLEncoder.encode(apiKey, "UTF-8"));
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
    
 // JSON 데이터 파싱 메서드 ---> 추후 수정 필요
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
                System.out.println("데이터출력테스트" + i +"시 ===========================================");

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


}
