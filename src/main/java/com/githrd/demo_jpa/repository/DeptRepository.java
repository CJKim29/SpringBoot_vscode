package com.githrd.demo_jpa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.githrd.demo_jpa.entity.Dept;

@Repository
public interface DeptRepository extends JpaRepository<Dept, Integer> {

    //전체조회
    List<Dept> findAll();

    //deptno 부서별 조회
    Optional<Dept>        findByDeptno(Integer deptno);

}