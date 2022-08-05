package kr.green.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
//@RequestMapping(value="/board") //클래스 위에 작성하면 클래스 안 메소드의 경로(url)에 /board가 자동으로 붙음
// -> /list 이렇게 적어도 됨 -> 근데 헷깔려서 비추천
public class BoardController {
	
	@RequestMapping(value="/board/list",method=RequestMethod.GET)
	public ModelAndView boardlistGet(ModelAndView mv){
    mv.setViewName("/board/list");
    return mv;
	}
	
	@RequestMapping(value="/board/insert",method=RequestMethod.GET)
	public ModelAndView boardInsertGet(ModelAndView mv){
    mv.setViewName("/board/insert");
    return mv;
	}
	
	@RequestMapping(value="/board/insert",method=RequestMethod.POST)
	public ModelAndView boardInsertPost(ModelAndView mv){
		//화면에서 전송한 데이터가 잘 도착했는지 확인
		//로그인한 회원 정보 확인
    mv.setViewName("redirect:/board/list");
    return mv;
	}
}
