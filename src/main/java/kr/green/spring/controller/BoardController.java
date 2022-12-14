package kr.green.spring.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import kr.green.spring.pagination.Criteria;
import kr.green.spring.pagination.PageMaker;
import kr.green.spring.service.BoardService;
import kr.green.spring.vo.BoardVO;
import kr.green.spring.vo.CommentVO;
import kr.green.spring.vo.FileVO;
import kr.green.spring.vo.LikesVO;
import kr.green.spring.vo.MemberVO;

@Controller
//@RequestMapping(value="/board") //클래스 위에 작성하면 클래스 안 메소드의 경로(url)에 /board가 자동으로 붙음
// -> /list 이렇게 적어도 됨 -> 근데 헷깔려서 비추천
public class BoardController {
	
	@Autowired
	BoardService boardService;
	
	@RequestMapping(value="/board/list",method=RequestMethod.GET)
	public ModelAndView boardlistGet(ModelAndView mv, Criteria cri){
		cri.setPerPageNum(10);
		int totalCount = boardService.getTotalCount(cri);
		//등록된 게시글 가져옴(여러개)
		ArrayList<BoardVO> list = boardService.getBoardList(cri);
		PageMaker pm = new PageMaker(cri, 5, totalCount);
		mv.addObject("pm", pm);
		mv.addObject("list", list);
    mv.setViewName("/board/list");
    return mv;
	}
	
	//게시글 등록
	@RequestMapping(value="/board/insert",method=RequestMethod.GET)
	public ModelAndView boardInsertGet(ModelAndView mv){
    mv.setViewName("/board/insert");
    return mv;
	}
	
	@RequestMapping(value="/board/insert",method=RequestMethod.POST)
	public ModelAndView boardInsertPost(ModelAndView mv, BoardVO board,
			HttpSession session, MultipartFile [] files){
		//로그인한 회원 정보 확인
		MemberVO user = (MemberVO)session.getAttribute("user");
		boardService.insertBoard(board, user, files);
    mv.setViewName("redirect:/board/list");
    return mv;
	}
	
	//선택한 게시글
	@RequestMapping(value="/board/select/{bd_num}",method=RequestMethod.GET)
	public ModelAndView boardSelectGet(ModelAndView mv,
			@PathVariable("bd_num")Integer bd_num, HttpSession session){
		//게시글 번호에 맞는 게시글 조회수 증가
		boardService.updateView(bd_num);
		//게시글 번호에 맞는 게시글 정보 가져오기
		BoardVO board = boardService.getBoard(bd_num);
		//해당 게시글에 대한 사용자의 추천/비추천 정보 -> 게시글 번호, 아이디
		MemberVO user = (MemberVO)session.getAttribute("user");
		LikesVO likes = boardService.getLikes(board, user);
		//첨부파일 가져옴
		ArrayList<FileVO> fileList = boardService.getFileList(bd_num);
		//가져온 게시글을 화면에 전달
		mv.addObject("board", board);
		mv.addObject("likes", likes);
		mv.addObject("fileList", fileList);
    mv.setViewName("/board/select");
    return mv;
	}
	
	//게시글 수정
	@RequestMapping(value="/board/update/{bd_num}",method=RequestMethod.GET)
	public ModelAndView boardUpdateGet(ModelAndView mv,
			@PathVariable("bd_num")Integer bd_num){
		//게시글 번호에 맞는 게시글 정보 가져오기
		BoardVO board = boardService.getBoard(bd_num);
		//첨부파일 가져옴
		ArrayList<FileVO> fileList = boardService.getFileList(bd_num);
		//가져온 게시글을 화면에 전달
		mv.addObject("fileList", fileList);
		mv.addObject("board", board);
    mv.setViewName("/board/update");
    return mv;
	}
	
	@RequestMapping(value="/board/update/{bd_num}",method=RequestMethod.POST)
	public ModelAndView boardUpdatePost(ModelAndView mv,
			@PathVariable("bd_num")Integer bd_num, HttpSession session, BoardVO board,
			MultipartFile [] files, int [] delFiles){
		//로그인한 회원 정보 확인
		MemberVO user = (MemberVO)session.getAttribute("user");
		//게시글 수정 요청
		board.setBd_num(bd_num);
		boardService.updateBoard(board,user,files,delFiles);
		
    mv.setViewName("redirect:/board/select/"+bd_num);
    return mv;
	}
	
	//게시글 삭제
	@RequestMapping(value="/board/delete/{bd_num}",method=RequestMethod.GET)
	public ModelAndView boardDeleteGet(ModelAndView mv,
			@PathVariable("bd_num")Integer bd_num, HttpSession session){
		//로그인한 회원 정보 확인
		MemberVO user = (MemberVO)session.getAttribute("user");
		boardService.deleteBoard(bd_num, user);
		
    mv.setViewName("redirect:/board/list");
    return mv;
	}
	
	//게시글 추천/비추천
	@RequestMapping(value="/board/likes",method=RequestMethod.POST)
	@ResponseBody
	public String boardLikes(@RequestBody LikesVO likes){
    return boardService.UpdateLikes(likes);
	}
	
	//AJAX를 이용한 게시글
	@RequestMapping(value="/board/list2",method=RequestMethod.GET)
	public ModelAndView boardlist2Get(ModelAndView mv, Criteria cri){
    mv.setViewName("/board/list2");
    return mv;
	}
	
	@RequestMapping(value="/ajax/board/list",method=RequestMethod.POST)
	@ResponseBody
	public Map<Object, Object> ajaxBoardList(@RequestBody Criteria cri){
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		cri.setPerPageNum(2);
		int totalCount = boardService.getTotalCount(cri);
		ArrayList<BoardVO> list = boardService.getBoardList(cri);
		PageMaker pm = new PageMaker(cri, 5, totalCount);
		map.put("list", list);
		map.put("pm", pm);
    return map;
	}
	
	//댓글 입력
	@RequestMapping(value="/ajax/comment/insert",method=RequestMethod.POST)
	@ResponseBody
	public Map<Object, Object> ajaxCommentInsert(@RequestBody CommentVO comment, HttpSession session){
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		MemberVO user = (MemberVO)session.getAttribute("user");
		String res = boardService.insertComment(comment, user);
		map.put("res", res);
		return map;
	}
	//댓글 리스트
	@RequestMapping(value="/ajax/comment/list/{co_bd_num}",method=RequestMethod.POST)
	@ResponseBody
	public Map<Object, Object> ajaxCommentList(@RequestBody Criteria cri
			, @PathVariable("co_bd_num")int co_bd_num){
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		ArrayList<CommentVO> list = boardService.getCommentList(co_bd_num, cri);
		
		int totalCount = boardService.getTotalCountComment(co_bd_num);
		PageMaker pm = new PageMaker(cri, 5, totalCount);
		
		map.put("pm", pm);
		map.put("list",list);
		return map;
	}
	//댓글 삭제
	@RequestMapping(value="/ajax/comment/delete",method=RequestMethod.POST)
	@ResponseBody
	public Map<Object, Object> ajaxCommentDelete(@RequestBody CommentVO comment, HttpSession session){
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		MemberVO user = (MemberVO)session.getAttribute("user");
		boolean res = boardService.deleteComment(comment, user);
		map.put("res", res);
		return map;
	}
	//댓글 수정
	@RequestMapping(value="/ajax/comment/update",method=RequestMethod.POST)
	@ResponseBody
	public Map<Object, Object> ajaxCommentUpdate(@RequestBody CommentVO comment, HttpSession session){
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		MemberVO user = (MemberVO)session.getAttribute("user");
		boolean res = boardService.updateComment(comment, user);
		map.put("res", res);
		return map;
	}
	//게시글 이미지 업로드
	@RequestMapping(value="/board/img/upload",method=RequestMethod.POST)
	@ResponseBody
	public Map<Object, Object> boardImgUpload(@RequestParam("file") MultipartFile file){
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		String url = boardService.uploadImage(file);
		
		map.put("url", url);
		return map;
	}
}
