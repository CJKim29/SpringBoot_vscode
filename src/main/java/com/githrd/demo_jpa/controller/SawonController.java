package com.githrd.demo_jpa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.githrd.demo_jpa.entity.Sawon;
import com.githrd.demo_jpa.repository.SawonRepository;

// @RestController = @controller + @ResponseBody
@RestController
public class SawonController {

    @Autowired
    SawonRepository sawonRepository;

    //전체조회
    @GetMapping("/sawons")
    public List<Sawon> selecList(){
        
        return sawonRepository.findAll();
    }

    //전체조회 + Query
    @GetMapping("/sawons-dept")
    public List<Sawon> selecListWithDept(){
        
        return sawonRepository.findAllWithDept();
    }

    //부서별조회
    @GetMapping("/sawon/{deptno}")
    public List<Sawon> selecListFromDeptno(@PathVariable int deptno){
        
        return sawonRepository.findAllByDeptno(deptno);
    }

    //Sort처리
    @GetMapping("/sawons-sortbysapay")
    public List<Sawon> selecListSortBySapayDesc(){
        
        return sawonRepository.findAll(Sort.by("sapay").descending());
    }

    //Page처리 : Oracle11 later이던지 MySQL설치해서 나중에 확인해보자
    @GetMapping("/sawons/{page}/{count}")
    public List<Sawon> selecListPaging(@PathVariable int page, @PathVariable int count){
        
        //page는 0부터 시작
        Pageable pageAble = PageRequest.of(page, count);

        Page<Sawon> pageSawon =  sawonRepository.findAll(pageAble);
        // PAge<Sawon> -> List<Sawon>변환 : getContent()

        return pageSawon.getContent();
    }
}