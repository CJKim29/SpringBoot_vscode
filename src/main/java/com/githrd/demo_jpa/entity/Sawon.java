package com.githrd.demo_jpa.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="sawon") //class이름이 sawon이므로 생략가능
public class Sawon {

    @Id
    Integer sabun;

    String saname;
    String sasex;
    String sajob;
    String sahire;
    
    @Column(nullable = true) // null 허용
    Integer samgr;
    
    Integer sapay;

    // foreign키로 설정되어있으니 참조만 하게끔 설정
    @Column(insertable=false, updatable=false)
    Integer deptno;

    @OneToOne
    @JoinColumn(name = "deptno", referencedColumnName = "deptno")
    Dept dept;

    @OneToMany
    @JoinColumn(name="godam", referencedColumnName = "sabun")
    List<Gogek> goList;
}