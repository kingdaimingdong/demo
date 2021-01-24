package common.utils;

import com.mina.npay.gateway.PayGwApplication;
import com.mina.npay.gateway.common.utils.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * 日期工具类测试
 * @author daimingdong
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayGwApplication.class)
public class DateUtilsTest {


    @Test
    public void betweenTest(){
        LocalDateTime dt = LocalDateTime.now().plusDays(5);
        long res = DateUtils.between(dt,LocalDateTime.now(), TimeUnit.DAYS);
        System.out.println(res);
    }

    @Test
    public void nowTest(){

        System.out.println(LocalDateTime.now());
    }
}
