package kr.green.spring.dao;

import org.apache.ibatis.annotations.Param;

import kr.green.spring.vo.MemberVO;

public interface MemberDAO {

	String selectEmail(@Param("me_id")String me_id);
	
	MemberVO selectMember(@Param("me_id")String me_id);
	//dao는 오버로딩 안됨
	MemberVO selectMember2(@Param("me_id")String me_id, @Param("me_pw")String me_pw);

}
