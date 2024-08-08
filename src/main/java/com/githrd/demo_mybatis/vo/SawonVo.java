package com.githrd.demo_mybatis.vo;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Alias("sawon")
public class SawonVo {
    
    int     sabun;
    String  saname;
    String  sasex;
    int     deptno;
    String  sajob;
    String  sahire;
    int     samgr;
    int     sapay;
}