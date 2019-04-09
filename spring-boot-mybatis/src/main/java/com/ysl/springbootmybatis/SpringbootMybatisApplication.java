package com.ysl.springbootmybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "com.ysl.dao.mapper")
public class SpringbootMybatisApplication {

    /*
     * 查看测试类：{@link SpringbootMybatisApplicationTests.java}
     */
    public static void main(String[] args) {
        SpringApplication.run(SpringbootMybatisApplication.class);
    }
}
