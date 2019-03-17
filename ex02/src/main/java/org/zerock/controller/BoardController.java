package org.zerock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.BoardVO;
import org.zerock.service.BoardService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/board/*")
@AllArgsConstructor
public class BoardController {
	
	private BoardService service;
	// /board/list 에의한 전체 테이블 목록 보여주기 GET 방식으로 요청이 들어온다.
	@GetMapping("/list")
	public void list(Model model)
	{
		log.info("list");
		model.addAttribute("list",service.getList());
	}
	// /board/register GET 방식일 때 등록 폼을 사용자에게 보여준다.
	@GetMapping("/register")
	public void register() {
		
	}
	
	// /board/register POST 방식이면 넘어온 파라미터를 통해 게시글을 등록한다.
	@PostMapping("/register")
	public String register(BoardVO board,RedirectAttributes rttr)
	{
		log.info("register: "+board);
		service.register(board);
		rttr.addFlashAttribute("result",board.getBno());
		return "redirect:/board/list";
	}
	
	// /board/get 으로 GET방식으로 조회할 게시글 번호를 bno로 넘겨서 해당 게시글 조회하기
	@GetMapping("/get")
	public void get(@RequestParam("bno") Long bno,Model model) {
		log.info("/get");
		model.addAttribute("board",service.get(bno));
	}
	
	@PostMapping("/modify")
	public String modify(BoardVO board, RedirectAttributes rttr) {
		log.info("modify:"+board);
		if(service.modify(board)) {
			rttr.addFlashAttribute("result","success");
		}
		return "redirect:/board/list";
	}
	
	@PostMapping("/remove")
	public String remove(@RequestParam("bno") Long bno,RedirectAttributes rttr) {
		log.info("remove...."+bno);
		if(service.remove(bno)) {
			rttr.addFlashAttribute("result","success");
		}
		return "redirect:/board/list";
	}
}
