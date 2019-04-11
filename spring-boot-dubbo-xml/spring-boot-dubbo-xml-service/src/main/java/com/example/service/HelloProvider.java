package com.example.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.example.api.IHello;

@Service
public class HelloProvider implements IHello {
    @Override
    public String sayHello(String name) {
        return "Hello: " + name + ", this is from dubbo provider.";
    }
}
