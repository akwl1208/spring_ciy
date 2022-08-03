package kr.green.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.green.spring.dao.MemberDAO;

//service 어노테이션 : 인터페이스 객체 만들게 해줌
// -> homecontroller.java 파일의 MemberService memberService;
@Service
public class MemberServiceImp implements MemberService{

	@Autowired
	MemberDAO memberDao;

	@Override
	public String getEmail(String id) {
		if(id == null)
			return null;
		//null이 아니면 dao한테 id줄테니 이메일 정보 달라고 요청
		return memberDao.selectEmail(id);
	}
}
