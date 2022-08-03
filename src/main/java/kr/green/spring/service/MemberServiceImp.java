package kr.green.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.green.spring.dao.MemberDAO;
import kr.green.spring.vo.MemberVO;

//service 어노테이션 : 인터페이스 객체 만들게 해줌
// -> homecontroller.java 파일의 MemberService memberService;
@Service
public class MemberServiceImp implements MemberService{

	@Autowired
	MemberDAO memberDao;

	@Override
	public String getEmail(String me_id) {
		if(me_id == null)
			return null;
		//null이 아니면 dao한테 id줄테니 이메일 정보 달라고 요청
		return memberDao.selectEmail(me_id);
	}

	@Override
	public MemberVO getMember(String me_id) {
		if(me_id == null)
			return null;
		return memberDao.selectMember(me_id);
	}

	@Override
	public MemberVO getMember(String me_id, String me_pw) {
		if(me_id == null || me_pw == null)
			return null;
		return memberDao.selectMember2(me_id, me_pw);
	}
	//선생님 풀이 : selectMember 재사용
	@Override
	public MemberVO getMember(MemberVO member) {
		if(member == null || member.getMe_id() == null || member.getMe_pw() == null)
			return null;
		
		MemberVO dbMember = memberDao.selectMember(member.getMe_id());
		if(dbMember == null)
			return null;
		
		if(dbMember.getMe_pw().equals(member.getMe_pw()))
			return dbMember;
		return null;
	}
	
	@Override
	public MemberVO getMember2(MemberVO member) {
		if(member == null || member.getMe_id() == null || member.getMe_pw() == null)
			return null;
		
		return memberDao.selectMember3(member);
	}


}
