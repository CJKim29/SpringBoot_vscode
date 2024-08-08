package com.githrd.demo_mybatis.vo;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data = @Getter @Setter
@Data
@NoArgsConstructor  //Default Constructor(기본 생성자)
@AllArgsConstructor //All Property넣어준 Overload된 생성자
@Alias("dept")      //alias 설정
public class DeptVo {

    int     deptno;
    String  dname;
    String  loc;
}