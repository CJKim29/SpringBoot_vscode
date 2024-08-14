package com.girhrd.demo_photo.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.girhrd.demo_photo.dao.PhotoMapper;
import com.girhrd.demo_photo.util.MyCommon;
import com.girhrd.demo_photo.util.Paging;
import com.girhrd.demo_photo.vo.MemberVo;
import com.girhrd.demo_photo.vo.PhotoVo;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/photo/")
public class PhotoController {
	
	@Autowired
	PhotoMapper photo_mapper;
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	HttpSession session;
	
	@Autowired
	ServletContext application;
	
	// /photo/list.do
	// /photo/list.do?page=1
	@RequestMapping("list.do")
	public String list(@RequestParam(name="page", defaultValue = "1") int nowPage,
						Model model) {
		
		//가져올 게시물의 범위 계산(start/end)
		int start = (nowPage-1) * MyCommon.Photo.BLOCK_LIST + 1;
		//int end   = (nowPage-1) * MyCommon.Photo.BLOCK_LIST + 8;
		int end   = start + MyCommon.Photo.BLOCK_LIST - 1;
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("start", start);
		map.put("end", end);
		
		List<PhotoVo> list = photo_mapper.selectPageList(map);
		
		//전체 게시물 수
		int rowTotal = photo_mapper.selectRowTotal();
		
		//pageMenu 만들기
		String pageMenu = Paging.getPaging("list.do", 						// pageURL
											nowPage,						// 현재페이지
											rowTotal,						// 전체게시물수
											MyCommon.Photo.BLOCK_LIST,		// 한 화면에 보여질 게시물 수
											MyCommon.Photo.BLOCK_PAGE		// 한 화면에 보여질 페이지 수
											);	
		
		//결과적으로 request binding
		model.addAttribute("list", list);
		model.addAttribute("pageMenu", pageMenu);
		
		return "/photo/photo_list";
	}
	
	//사진등록폼 띄우기
	@RequestMapping("insert_form.do")
	public String insert_form() {
		
		return "photo/photo_insert_form";
	}
	
	// photo/insert.do?p_title=제목&p_content=내용&photo=a.jpg
	//사진등록
	//요청parameter이름과 받는 변수명이 동일하면 @RequestParam(name="")의 name속성은 생략가능
	@RequestMapping("insert.do")
	public String insert(PhotoVo vo, @RequestParam MultipartFile photo, RedirectAttributes ra) throws Exception, IOException {
		
		
		MemberVo user = (MemberVo) session.getAttribute("user"); 
		
		// session timeout
		if(user==null) {
			
			//response.sendRedirect("../member/login_form.do?reason=session_timeout");
			
			ra.addAttribute("reason", "session_timeout");
			
			return "redirect:../member/login_form.do";
		}
		
		//파일업로드
		String absPath = application.getRealPath("/resources/images/");
		String p_filename ="no_file";
		if(!photo.isEmpty() ) {
			
			//업로드 파일이름 얻어오기
			p_filename = photo.getOriginalFilename();
			
			File f = new File(absPath, p_filename);
			
			if(f.exists()) {
				// 저장경로에 동일한 파일이 존재하면 => 다른이름으로 파일명 변경
				// 변경파일명 = 시간_원래파일명
				long tm = System.currentTimeMillis();
				p_filename = String.format("%d %d",  tm, p_filename);
				
				f = new File(absPath, p_filename);
			}
			
			//임시파일 -> 내가 지정한 위치로 복사
			photo.transferTo(f);
		}
		//업로드된 파일이름
		vo.setP_filename(p_filename);		
		
		//IP
		String p_ip = request.getRemoteAddr();
		vo.setP_ip(p_ip);
		
		String p_content = vo.getP_content().replaceAll("\n","<br>");
		vo.setP_content(p_content);
		
		//로그인 유저 정보 넣기
		vo.setMem_idx(user.getMem_idx());
		vo.setMem_name(user.getMem_name());
		
		//DB insert
		int res = photo_mapper.insert(vo);
		
		
		return "redirect:list.do";
	}
	
	// /photo/photo_one.do?p_idx=5
	@RequestMapping(value="photo_one.do")
	@ResponseBody	//현재 반환 값을 응답데이터로 이용해라
	public PhotoVo photo_one(int p_idx) {
		
		PhotoVo vo = photo_mapper.selectOne(p_idx);
		
		return vo;
	}
	
	@RequestMapping("delete.do")
	public String delete(int p_idx) {
		
		PhotoVo vo = photo_mapper.selectOne(p_idx);
		// /images/의 절대경로
		String absPath = request.getServletContext().getRealPath("/resources/images/");
		
		//						절대경로  (삭제)파일명
		File delFile = new File(absPath, vo.getP_filename());
		
		delFile.delete();
		
		//DB delete
		int res = photo_mapper.delete(p_idx);
		
		return "redirect:list.do";
	}
	
	
	//사진수정폼 띄우기
	@RequestMapping("modify_form.do")
	public String modify_form(int p_idx) {
		
		PhotoVo vo = photo_mapper.selectOne(p_idx);
		
		//<br> - > "\n"
		String p_content = vo.getP_content().replaceAll("<br>", "\n");
		vo.setP_content(p_content);
		
		//3.request binding
		request.setAttribute("vo", vo);
		
		return "photo/photo_modify_form";
	}
	
	@RequestMapping("modify.do")
	public String modify(int p_idx, String p_title, String p_content) {
		
		String p_ip		 = request.getRemoteAddr();
		
		PhotoVo vo = new PhotoVo(p_idx, p_title, p_content, p_ip);
		
		int res = photo_mapper.update(vo);
		
		return "redirect:list.do";
	}
	
	@RequestMapping(value="photo_upload.do")
	@ResponseBody
	public Map<String,String> photo_upload(@RequestParam MultipartFile photo, int p_idx) throws Exception, IOException {
		
		//파일업로드
		String absPath = application.getRealPath("/resources/images/");
		String p_filename ="";
		if(!photo.isEmpty() ) {
			
			//업로드 파일이름 얻어오기
			p_filename = photo.getOriginalFilename();
			
			File f = new File(absPath, p_filename);
			
			if(f.exists()) {
				//저장경로에 동일한 화일이 존재하면=>다른이름을 화일명 변경
				// 변경화일명 = 시간_원래화일명
				long tm = System.currentTimeMillis();
				p_filename = String.format("%d_%s", tm , p_filename);
				
				f = new File(absPath, p_filename);
			}
			//임시화일=>내가 지정한 위치로 복사
			photo.transferTo(f);
		}
		
		//p_idx에 저장된 이전 파일 삭제
		PhotoVo vo = photo_mapper.selectOne(p_idx);
		File delFile = new File(absPath, vo.getP_filename());
		delFile.delete();
		
		
		//update된 파일 이름 수정
		vo.setP_filename(p_filename);
		int res = photo_mapper.updateFilename(vo);

		//{"p_filename":"%s"}
		//String json = String.format("{\"p_filename\":\"%s\"}", p_filename);
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("p_filename", p_filename);

		return map;
	}
	
	
	
	
	
	
	
	
}