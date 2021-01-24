package common.utils;

import com.mina.npay.gateway.common.utils.GsonUtils;
import org.junit.Test;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ConvertTest {

    class A {
        private String name = "son";

    }

    class B extends A{

        private String age = "90";
    }

    @Test
    public void convertTest(){

        A a = new B();

        A c = a;

        B b = (B)c;

        boolean res = (a == b);

        System.out.println("a address:"+a);
        System.out.println("b address:"+b);
        System.out.println("a equals b"+res);
        System.out.println(GsonUtils.toJson(c));
    }

    @Test
    public void getIp(){
            try {
                InetAddress ip4 = Inet4Address.getLocalHost();
                System.out.println(ip4.getHostAddress());
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
    }
}
