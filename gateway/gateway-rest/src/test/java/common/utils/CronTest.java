package common.utils;

import org.junit.Assert;
import org.junit.Test;
import org.quartz.CronExpression;

import java.text.SimpleDateFormat;

/**
 * cron表达式是否满足
 */
public class CronTest {

    /**
     * 测试给定的时间是否在cron表达式之中
     */
    @Test
    public void cron() throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        CronExpression cronExpression = new CronExpression("* 0/1 7-23 * * ?");

        boolean resCron = cronExpression.isSatisfiedBy(simpleDateFormat.parse("2018-04-27 16:00:00"));

        Assert.assertTrue(resCron);
        Assert.assertTrue(resCron == true);

    }
}
