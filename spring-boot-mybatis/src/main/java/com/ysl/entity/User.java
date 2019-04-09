package com.ysl.entity;

public class User {
    /**
     * 数据库字段为user_id，由于在属性配置了下划线转驼峰，所以就可以完成映射
     */
    private int userId;
    private String name;
    private int deptId;

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", deptId=" + deptId +
                '}';
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }
}
