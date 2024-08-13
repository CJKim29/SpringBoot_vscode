package com.githrd.demo_fileupload.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletContext;

@Controller
public class FileUploadController {

    @Autowired
    ServletContext application; //DS에 요청받아오기

    // upload1.do?title=제목&photo=a.jpg
    //@RequestMapping(value = "/upload1.do", method = RequestMethod.POST)
    //                                  @RequestParam(name="photo") <= param name과 변수명이 동일할 경우 생략가능
    @PostMapping("/upload1.do")
    public String upload1(String title, @RequestParam(name="photo") MultipartFile photo, Model model) throws IllegalStateException, IOException{

        //저장할 위치정보 구하기(절대경로)
        String absPath = application.getRealPath("/images/");
        //System.out.println(absPath);

        //파일업로드처리
        String filename = "no_file";
        if(photo.isEmpty()==false){ //업로드 파일이 존재하면

            filename = photo.getOriginalFilename();

            File f = new File(absPath, filename);
            //동일 파일명이 존재하면
            if(f.exists()){
                long tm = System.currentTimeMillis();
                filename = String.format("%d_%s", tm, filename);
                f = new File(absPath, filename);
            }

            photo.transferTo(f);
        }

        model.addAttribute("title", title);
        model.addAttribute("filename", filename);

        return "result1";
    }//end:upload1()

    // upload1.do?title=제목&photo=a.jpg&photo=b.jpg
    @PostMapping("/upload2.do")
    public String upload2(String title, @RequestParam(name="photo") MultipartFile [] photo_array, Model model) throws IllegalStateException, IOException{

        //저장할 위치정보 구하기(절대경로)
        String absPath = application.getRealPath("/images/");

        String filename1 = "no_file";
        String filename2 = "no_file";

        for (int i = 0; i < photo_array.length; i++) {  // i = 0, 1

            MultipartFile photo = photo_array[i];

            if(photo.isEmpty()==false){ //업로드 파일이 존재하면

                String filename = photo.getOriginalFilename();
    
                File f = new File(absPath, filename);
                //동일 파일명이 존재하면
                if(f.exists()){
                    long tm = System.currentTimeMillis();
                    filename = String.format("%d_%s", tm, filename);
                    f = new File(absPath, filename);
                }
                photo.transferTo(f);
                if(i==0)
                    filename1 = filename;
                else if(i==1)
                    filename2 = filename;
            }
        }//end:for()

        model.addAttribute("title", title);
        model.addAttribute("filename1", filename1);
        model.addAttribute("filename2", filename2);

        return "result2";
    }//end:upload2()

    // upload1.do?title=제목&photo=a.jpg&photo=b.jpg
    @PostMapping("/upload3.do")
    public String upload3(String title,
    @RequestParam(name="photo") List<MultipartFile> photo_list, Model model) throws IllegalStateException, IOException{

        //저장할 위치정보 구하기(절대경로)
        String absPath = application.getRealPath("/images/");

        List<String> filename_list = new ArrayList<String>();

        for(MultipartFile photo : photo_list) {
			
			
			if(!photo.isEmpty()) {
				//업로드된 파일명을 구한다
				String filename = photo.getOriginalFilename();
				
				//저장경로및파일
				File f = new File(absPath,filename);
				
				if(f.exists()) {//동일파일이 존재하냐?
					
					// 시간_파일명 이름변경
					long tm = System.currentTimeMillis();
					filename = String.format("%d_%s", tm,filename);
					
					f = new File(absPath,filename);
				}
				
				//spring이 저장해놓은 임시파일을 복사한다
				photo.transferTo(f);
				
				filename_list.add(filename);
			}
			
		}//end:for()

        model.addAttribute("title", title);
		model.addAttribute("filename_list", filename_list);

        return "result3";
    }//end:upload3()
}