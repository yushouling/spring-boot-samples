import com.ysl.redis.Application;
import com.ysl.redis.service.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class RedisTest {

    @Autowired
    RedisService redisService;

    @Test
    public void testString() {
        String str01 = "test:str01";
        boolean setBoolean = redisService.set(str01, "test");
        System.out.println(setBoolean);

        String str01Result = redisService.get(str01);
        System.out.println(str01Result);
    }

    @Test
    public void testList() {
        List<String> list = new ArrayList<>();
        list.add("a1");
        list.add("a2");

        String key = "test:list01";
        boolean setBoolean = redisService.setList(key, list);
        System.out.println(setBoolean);

        List<String> result = redisService.getList(key, String.class);
        System.out.println(result);

    }
}
