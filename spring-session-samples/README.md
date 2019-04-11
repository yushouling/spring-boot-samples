spring分布式session
=========================

![Spring Boot 2.0](https://img.shields.io/badge/Spring%20Boot-2.0-brightgreen.svg)
![JDK 1.8](https://img.shields.io/badge/JDK-1.8-brightgreen.svg)
![Redis](https://img.shields.io/badge/Redis-2.9-red.svg)
![Maven](https://img.shields.io/badge/Maven-3.5.0-yellowgreen.svg)
![Test](https://img.shields.io/badge/Test-OK-green.svg)
![Original](https://img.shields.io/badge/Original-yushouling-blue.svg)

# 使用步骤：
- 1.修改application.properties的redis配置，注意这里不能使用集群模式
- 2.使用默认端口启动程序
- 3.修改server.port端口，再启动一个新的程序
- 4.访问 http://localhost:8080/session/set?name=hello
- 5.两个端口都访问 http://localhost:8080/session/get 获取session

