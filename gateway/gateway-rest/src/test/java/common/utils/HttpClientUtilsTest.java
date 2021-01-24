package common.utils;

import com.mina.npay.gateway.PayGwApplication;
import com.mina.npay.gateway.common.utils.HttpClientUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * httpclient测试工具类
 * @author daimingdong
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayGwApplication.class)
public class HttpClientUtilsTest {

    @Test
    public void getUrlTest(){

        try {
            String res  = HttpClientUtils.doRequest("http://www.baidu.com");
            System.out.println(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void postUrlTest(){

        Map<String,String> map = new HashMap<>();
        map.put("sign","123456");
        try {
            String res  = HttpClientUtils.doRequest("http://192.168.0.110:3306",map,null,null);
            System.out.println(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
