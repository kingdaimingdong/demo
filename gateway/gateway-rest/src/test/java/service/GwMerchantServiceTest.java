package service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mina.npay.gateway.PayGwApplication;
import com.mina.npay.gateway.common.utils.GsonUtils;
import com.mina.npay.gateway.entity.GwMerchant;
import com.mina.npay.gateway.service.IGwMerchantService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayGwApplication.class)
public class GwMerchantServiceTest {


    @Autowired
    private IGwMerchantService iGwMerchantService;

    @Test
    public void getTest(){
        GwMerchant gwMerchant = iGwMerchantService.getById("1");
        System.out.println("res ===="+gwMerchant.toString());
    }

    @Test
    public void saveTest(){
        GwMerchant merchant = new GwMerchant();

        merchant.setId("234567");
        merchant.setTkey("test");
        merchant.setName("dada");
        merchant.setStatus(0);
        merchant.setUplineTs(LocalDateTime.now());

        iGwMerchantService.save(merchant);

    }

    @Test
    public void updateTest(){

        GwMerchant merchant = iGwMerchantService.getById("1");
        System.out.println("merchant version:"+merchant.getVs());

        Page<GwMerchant> page = new Page<>();
        IPage<GwMerchant> res = iGwMerchantService.page(page);

        System.out.println(GsonUtils.toJson(res));
    }



}
