package com.githrd.demo_mvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.githrd.demo_mvc.dao.TestDao;

@Service("test_service")
public class TestServiceImpl implements TestService {

    @Autowired
    @Qualifier("test_dao")  //만들어진 bean 중에서 injection 받을 것을 지정
    TestDao test_dao;

    @Autowired
    @Qualifier("test_dao2")  //만들어진 bean 중에서 injection 받을 것을 지정
    TestDao test_dao2;

    // 생성자 자동 생성 : ctor 입력 후 tab 2회
    public TestServiceImpl() {
        super();
        System.out.println("--TestServiceImpl()--");
    }

    // Code Generator : Ctrl + .
    @Override
    public void test() {
    }

    @Override
    public String hello() {
        
        StringBuffer sb = new StringBuffer();
        sb.append("msg1=>");
        sb.append(test_dao.hello());
        sb.append("<br>");
        sb.append("msg2=>");
        sb.append(test_dao2.hello());

        return sb.toString();
    }
}