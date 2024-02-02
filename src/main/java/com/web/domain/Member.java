package com.web.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
//@Getter
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
@SequenceGenerator(name="MEMBER_SEQ_GENERATOR", sequenceName="MEMBER_SEQ", allocationSize = 1)
@Entity
@Table(name="Member", uniqueConstraints = {@UniqueConstraint(columnNames = "memberId")})
public class Member extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long memberCode;  //회원에게 고유하게 부여되는 코드

	@Column(unique=true, nullable = false)
	private String memberId; 	//회원 id

	private String password;	//회원 pw
	
	private String memberName; //회원 이름
	private String email; 		//회원 이메일
	private String phoneNum; 	//회원 전화번호
	//private String rrn;			//주민번호 Resident Registration Number
	private String role;
	private String authProvider;  //이후 OAuth 에서 사용할 유저 정보 제공자

}
