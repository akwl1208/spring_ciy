package kr.green.spring.service;

import kr.green.spring.vo.MemberVO;

public interface MemberService {

	String getEmail(String me_id);

	MemberVO getMember(String me_id);

	MemberVO getMember(String me_id, String me_pw);

	MemberVO getMember(MemberVO member);

	MemberVO getMember2(MemberVO member);

}
