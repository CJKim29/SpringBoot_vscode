package com.githrd.demo_mybatis.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.githrd.demo_mybatis.vo.SawonVo;

@Mapper
public interface SawonDao {

    // XML의 Mapper정보를 이용하려면
    // 1.DeptDao == DeptDao.xml
    // 2.namespace="Mapper정보"
    //   namespace="com.githrd.demo_mybatis.dao.DeptDao"
    // 3.mapper id = "메소드명"
    //          id = "selectList"

    @Select("select * from sawon")
    List<SawonVo> selectList();

    @Select("select * from sawon where sajob = #{ sajob }")
    List<SawonVo> selectListSajob(String sajob);

    @Select("select * from sawon where sabun = #{ sabun }")
    List<SawonVo> selectListSabun(int sabun);


    List<SawonVo> selectList1(Map<String,Object> map);

}