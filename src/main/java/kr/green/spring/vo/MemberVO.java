package kr.green.spring.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class MemberVO {
	//login.jsp 파일에서 input 태그의 name들과 변수명이 일치해야 한다
	//변수명은 데이터 변수명(sql)과 같게 해주는게 좋다
	private String me_id;
	private String me_pw;
	private char me_gender;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date me_birth;
	private String me_email;
	private int me_authority;
}
