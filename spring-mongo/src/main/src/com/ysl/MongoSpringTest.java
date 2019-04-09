package com.ysl;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import java.util.List;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/*spring整合mongodb*/
public class MongoSpringTest {
    private static MongoTemplate mongoTemplate;
    
    static {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("mongodb.xml");
        mongoTemplate = (MongoTemplate) context.getBean("mongoTemplate");
    }

    /**
     * 查询用户信息
     */
    @Test
    public void testQuery() {
        // 查询主要用到Query和Criteria两个对象
        Query query = new Query();
        Criteria criteria = where("age").gt(8);// 大于

        query.addCriteria(criteria);
		//List<Student> findAll = mongoTemplate.findAll(Student.class);
		List<Student> list = mongoTemplate.find(query, Student.class);
		for(Student student : list) {
			System.out.println(student.toString());
		}
		
		// 插入数据
        //mongoTemplate.insert(object);
    }

}
