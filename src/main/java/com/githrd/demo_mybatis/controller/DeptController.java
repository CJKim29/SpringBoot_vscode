package com.githrd.demo_mybatis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.githrd.demo_mybatis.dao.DeptDao;
import com.githrd.demo_mybatis.vo.DeptVo;

@Controller
public class DeptController {

    @Autowired
    DeptDao dept_Dao;

    @RequestMapping("/dept/list.do")
    public String list(Model model){

        List<DeptVo> list = dept_Dao.selectList();
        //System.out.println(list.size());
        model.addAttribute("list", list);

        return "dept/dept_list";
    }
}