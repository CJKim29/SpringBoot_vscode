package com.githrd.demo_jpa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.githrd.demo_jpa.entity.Dept;

@Repository
public interface DeptRepository extends JpaRepository<Dept, Integer> {

    //전체조회
    //@Query("select d from Dept d order by d.deptno desc")
    @Query(value = "select * from dept order by deptno desc", nativeQuery = true)
    List<Dept> findAll();

    //deptno 부서별 조회 - optional -> 제대로 연결 됐나 안 됐나 알아보기 위해
    Optional<Dept>        findByDeptno(Integer deptno);

    @Query(value = "select * from dept  where loc =:loc", nativeQuery=true)
    List<Dept> findByLoc(String loc);

}