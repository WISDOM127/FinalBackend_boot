package com.web;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.web.domain.ICNAirportPassengerDTO;
import com.web.persistence.CongestionRepository;
import com.web.service.ICNAirportCongestionService;


@SpringBootTest
public class congestiondataTest {
	
	@Autowired
	private CongestionRepository aRepo;
	
	@Autowired
	private ICNAirportCongestionService cs;
	
	
//	@Test
//	public void deleteCustomer() {
//		cs.deleteCustomer("C6736");
//	}
	
	
	
	//startDate:연간VIP서비스시작기간 endDate:연간서비스종료일자
//	private Date startDate = dateTryCatch("2023-02-01");
//	private Date endDate = dateTryCatch("2024-01-31");

	//Date 데이터 변환 메서드 (String -> Date)
//	public Date dateTryCatch(String dateString) {		
//		 try {
//	            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//	            Date stringToDate = dateFormat.parse(dateString);
//	            return stringToDate;
//
//	        } catch (ParseException e) {
//	            e.printStackTrace();
//	            return null;
//	        }
//	}
//

	
	//테이블의 모든 레코드 삭제하기
//	@Test
//	public void deleteVoucher() {
//		try {
//			aRepo.deleteAll();
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.out.println("삭제할 값이 없습니다.");
//		}
//		
//	}
	
//    //시간대별 데이터 프론트 전송
//	@Test
//    public void congestionTimeData () {
//    	String atime = "02";
//    	ICNAirportPassengerDTO dto = new ICNAirportPassengerDTO();
//    	dto = cs.getTimeData(atime);
//    	System.out.println(dto);
//    	
//    
//    }
//	
	
//	@Test
//	public void test() {
//		
//		Voucher hehe = new Voucher();
//		hehe = vRepo.findByVoucherCode("Ge9827b747000007");
//
//		System.out.println(hehe.getVoucherServiceName()+"/"+hehe.getVoucherService());
//	}
	
	@Test
	public void nowTime() {
		String onehourago = "";
		
		 LocalDateTime date = LocalDateTime.now();
		
		 System.out.println(date);
		
		//return onehourago;
	}
	
	

}