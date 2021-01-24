package common.utils;

import com.mina.npay.gateway.PayGwApplication;
import com.mina.npay.gateway.common.utils.MessageUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Locale;

/**
 * 国际化测试类
 * @author daimingdong
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayGwApplication.class)
public class MessageUtilsTest {

    @Autowired
    private MessageUtils messageUtils;

    @Autowired
    private MessageSource messageSource;


    @Test
    public void messagesTest(){

        //String res = messageUtils.get("1001");
        String res = MessageUtils.get("user.username");

        System.out.println("========:"+ res);
    }

    @Test
    public void getKeyTest(){
       String res =  messageSource.getMessage("user.username",null,Locale.US);
        System.out.println("========:"+ res);
    }
}
