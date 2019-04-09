package com.example.interceptor.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    /**
     * 测试被拦截路径
     * {@link com.example.interceptor.test.MyWebMvcConfigurer}
     *
     * @return
     */
    @GetMapping("/index")
    public String index() {
        return "此路径已被拦截";
    }

    @GetMapping("/hello")
    public String hello(String name) {
        return "Hello: " + name;
    }

    @GetMapping("/get/{id}")
    public String get(@PathVariable("id") String id) {
        return "get " + id;
    }
}
