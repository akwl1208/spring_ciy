package kr.green.spring.vo;

import java.util.Date;

import lombok.Data;

@Data
public class MemberVO {
	//login.jsp 파일에서 input 태그의 name들과 변수명이 일치해야 한다
	//변수명은 데이터 변수명(sql)과 같게 해주는게 좋다
	private String me_id;
	private String me_pw;
	private char me_gender;
	private Date me_birthday;
	private String me_email;
	private int me_authority;
}
