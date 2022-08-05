package kr.green.spring.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import kr.green.spring.service.BoardService;
import kr.green.spring.vo.BoardVO;
import kr.green.spring.vo.MemberVO;

@Controller
//@RequestMapping(value="/board") //클래스 위에 작성하면 클래스 안 메소드의 경로(url)에 /board가 자동으로 붙음
// -> /list 이렇게 적어도 됨 -> 근데 헷깔려서 비추천
public class BoardController {
	
	@Autowired
	BoardService boardService;
	
	@RequestMapping(value="/board/list",method=RequestMethod.GET)
	public ModelAndView boardlistGet(ModelAndView mv){
		//등록된 게시글 가져옴(여러개)
		ArrayList<BoardVO> list = boardService.getBoardList();
		System.out.println(list);
		mv.addObject("list", list);
    mv.setViewName("/board/list");
    return mv;
	}
	
	@RequestMapping(value="/board/insert",method=RequestMethod.GET)
	public ModelAndView boardInsertGet(ModelAndView mv){
    mv.setViewName("/board/insert");
    return mv;
	}
	
	@RequestMapping(value="/board/insert",method=RequestMethod.POST)
	public ModelAndView boardInsertPost(ModelAndView mv, BoardVO board,
			HttpSession session){
		//화면에서 전송한 데이터가 잘 도착했는지 확인
		System.out.println(board);
		//로그인한 회원 정보 확인
		MemberVO user = (MemberVO)session.getAttribute("user");
		//System.out.println(user);
		boardService.insertBoard(board, user);
    mv.setViewName("redirect:/board/list");
    return mv;
	}
}
