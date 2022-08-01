package kr.green.spring;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	// url에 ?로 데이터를 전송하는 경우
	// http://localhost:8080/spring?age=20&hobby=운동&id=abc123&pw=abc123
	@RequestMapping(value = "/", method = RequestMethod.GET)
	// age를 int가 아니라 integer로 처리하는 이유 -> null 처리를 위해
	public ModelAndView home(ModelAndView mv, Integer age, String hobby, String id, String pw) {
		// 서버에서 화면으로 데이터전송(ModelAndView 사용)
		// 서버 :mv.addObject("화면에서 사용할 이름", "보낼변수/객체"); 로 화면에 전송
		//클라이언트: ${화면에서 사용할 이름}으로 원하는 위치에서 사용 -> home.jsp
		mv.addObject("name", "홍길동");
		
		/* 화면에서 보낸 변수(age)를 받음
		 * 서버 : 메소드 매개변수의 이름과 화면에서 보낸 변수와 같게 해줌
		 */
		System.out.println("나이는 " + age + "입니다");
		System.out.println("취미는 " + hobby + "입니다");
		
		//아이디와 비밀번호를 입력해서 로그인버튼을 누르면 서버로 이동하는 코드
		System.out.println("아이디는 " + id + "입니다");
		System.out.println("비밀번호는 " + pw + "입니다");
		
		mv.setViewName("home");
		return mv;
	}
	
	//get은 일반적으로 a 태그에서 사용
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public ModelAndView loginGET(ModelAndView mv) {
		//컨트롤러에서 setViewName으로 jsp 파일을 지정하지 않으면 value에 있는 경로를 기준으로 jsp파일을 찾음
		mv.setViewName("/member/login");
		return mv;
	}
	
	// POST는 URL에 경로가 남지 않음
	// 일반적으로 form 태그에서 사용
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public ModelAndView loginPOST(ModelAndView mv, String id, String pw) {
		System.out.println("아이디는 " + id + "입니다");
		System.out.println("비밀번호는 " + pw + "입니다");
		//redirect : get과 같은 경로 사용을 막음
		//spring을 제외한 나머지 작성
		if("asd".equals(id) && "qwe".equals(pw))	
			mv.setViewName("redirect:/"); //로그인을 성공하면 main으로 넘어감
		else
			mv.setViewName("redirect:/login"); //로그인에 실패하면 로그인 화면에 그대로 남아있음
		return mv;
	}
	
	/* url 경로에 전달하려는 값이 있는 경우
	 	http://localhost:8080/spring/member/20/운동 -> 직접 입력
	 	ex) 네이버 기사
	*/
	// value에 입력한 {}안에 이름과 @Pathvariable("이름")이 일치해야 한다
	@RequestMapping(value="/member/{age1}/{hobby2}", method=RequestMethod.GET)
	public ModelAndView ageHobbyGet(ModelAndView mv,
			@PathVariable("age1") int age,
			@PathVariable("hobby2") String hobby) {
		System.out.println("나이는 " + age + "입니다");
		System.out.println("취미는 " + hobby + "입니다");
		mv.setViewName("home");
		return mv;
	}
	
}
