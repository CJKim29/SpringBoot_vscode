package com.githrd.demo_jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="dept") //Entity명과 Table명이 같으면 생략 가능
public class Dept {

    @Id //primary key 선언
    //@GeneratedValue(strategy = GenerationType.IDENTITY) //Identity-유일, auto-자동증가
    Integer deptno;

    // 컬럼명/제약조건
    @Column(name="dname", nullable = false) //Column 선언 생략 가능(name="dname"과 String dname이 같기 때문)
    String  dname;

    String  loc;
}
