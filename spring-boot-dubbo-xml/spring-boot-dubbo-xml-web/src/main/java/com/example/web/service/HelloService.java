package com.example.web.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.api.IHello;
import org.springframework.stereotype.Service;

@Service
public class HelloService {
    @Reference
    private IHello helloProvider;

    public String testDubbo(String name) {
        String sayHello = helloProvider.sayHello(name);
        return "web端请求dubbo返回信息：" + sayHello;
    }
}
