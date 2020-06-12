package com.hc.test1.dao;

import com.hc.bean.Dept;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class DeptDaoTest {

    @Resource
    private DeptDao1 deptDao1;

    @Test
    public void fun(){
        List<Dept> depts = deptDao1.findAll();
        depts.forEach(System.out::println);
    }
}