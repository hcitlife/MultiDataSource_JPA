package com.hc.test2.dao;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class DeptDao2Test {

    @Resource
    private DeptDao2 deptDao2;

    @Test
    public void findAll(){
        deptDao2.findAll().forEach(System.out::println);
    }
}