package config.interceptor;

import com.mina.npay.gateway.PayGwApplication;
import com.mina.npay.gateway.common.utils.DateUtils;
import com.mina.npay.gateway.config.interceptor.SignUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.Date;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 签名工具测试类
 * @author daimingdong
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayGwApplication.class)
public class SignUtilTest {

    @Test
    public void signAuthTest(){
        SortedMap<String, String> params = new TreeMap<String,String>();
        params.put("age","88");
        String sign = SignUtil.getParamsSign(params);
        System.out.println("sign:"+sign);
        params.put("sign",sign);
        boolean res =  SignUtil.verifySign(params);
        System.out.println("res is :" + res);
    }

    @Test
    public void getReqTsTest(){
        Instant instant = DateUtils.dateToInstantConverter(new Date());
        long reqTs = instant.getEpochSecond();
        System.out.println("reqTs="+String.valueOf(reqTs));
        Instant.ofEpochSecond(reqTs).toString();
    }

    @Test
    public void generateReqSign(){
        SortedMap<String, String> req = new TreeMap<String,String>();

        req.put("mid","1");
        req.put("reqTs","1584414913");
        req.put("uid","1");
        req.put("serial","123456789");
        req.put("amount","100");
        req.put("currency","dilamu");
        req.put("reserved","paytomato");
        req.put("servNotice","www.baidu.com");

        String sign = SignUtil.getParamsSign(req,"test");

        System.out.println("sign="+sign);

        req.put("sign",sign);
        boolean res =  SignUtil.verifySign(req,"test");
        System.out.println("res is :" + res);

    }
}
