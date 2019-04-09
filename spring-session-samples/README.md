# spring-session
## 分布式session，使用Spring boot 2.x + redis 2.9

## 使用步骤：
### 1.修改application.properties的redis配置，注意这里不能使用集群模式
### 2.使用默认端口启动程序
### 3.修改server.port端口，再启动一个新的程序
### 4.访问localhost:8080/session/set?name=hello
### 5.两个端口都访问localhost:8080/session/get获取session


