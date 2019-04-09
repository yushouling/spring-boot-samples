package com.ysl.dao.mapper;

import com.ysl.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

// 由于启动类用了@MapperScan注解，此处不需要注解
public interface UserMapper {
    @Select("select * from sys_user where user_id=#{userId}")
    public User getUser(@Param("userId") String userId);

    @Select("select * from sys_user")
    public List<User> getUserList();

    // 方法名映射到UserMapper.xml的id为getUserName
    // @Param中的值与XML中的参数名#{}一致
    public String getUserName(@Param("userId2") String userId);
}
