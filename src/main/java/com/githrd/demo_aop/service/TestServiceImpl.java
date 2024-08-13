package com.githrd.demo_aop.service;

import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {

    @Override
    public String Hello() {

        try {
            Thread.sleep(1234);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "Hello EveryOne!!";
    }

    @Override
    public String hi() {

        for (int i = 0; i < 1000000; i++) {
            
        }
        return "Hi~ How are you?";
    }

}