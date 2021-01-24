package common.utils;

import com.mina.npay.gateway.PayGwApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayGwApplication.class)
public class TimeTest {

    @Test
    public void testTime() {
        LocalDateTime time = LocalDateTime.now();

        System.out.println(time.toString()); //字符串表示
        System.out.println(time.toLocalTime()); //获取时间(LocalTime)
        System.out.println(time.toLocalDate()); //获取日期(LocalDate)
        System.out.println(time.getDayOfMonth()); //获取当前时间月份的第几天
        System.out.println(time.getDayOfWeek());  //获取当前周的第几天
        System.out.println(time.getDayOfYear());  //获取当前时间在该年属于第几天
        System.out.println(time.getHour());
        System.out.println(time.getMinute());
        System.out.println(time.getMonthValue());
        System.out.println(time.getMonth());
        System.out.println("-----------------------------------");
        //格式化输出
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY/MM/dd HH:mm:ss");
        System.out.println(time.format(formatter));
        //构造时间
        LocalDateTime startTime = LocalDateTime.of(2018, 1, 1, 20, 31, 20);
        LocalDateTime endTime = LocalDateTime.of(2018, 1, 3, 20, 31, 20);
        //比较时间
        System.out.println(time.isAfter(startTime));
        System.out.println(time.isBefore(endTime));

        //时间运算，相加相减
        System.out.println(time.plusYears(2)); //加2年
        System.out.println(time.plusDays(2)); //加两天
        System.out.println(time.minusYears(2)); //减两年
        System.out.println(time.minusDays(2)); //减两天

        //获取毫秒数(使用Instant)
        System.out.println(time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        //获取秒数(使用Instant)
        System.out.println(time.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond());



        TemporalAdjusters temproalAdjusters = null;
        LocalDate date=LocalDate.of(2017, 10, 16);
        date=date.with(temproalAdjusters.dayOfWeekInMonth(3, DayOfWeek.MONDAY));
        System.out.println("与date月份相同的第3个星期一:"+date);
        date=date.with(temproalAdjusters.firstDayOfMonth());
        System.out.println("与date月份相同的第一天:"+date);
        date=date.with(temproalAdjusters.firstDayOfNextMonth());
        System.out.println("date月份下一个月的第一天:"+date);
        date=date.with(temproalAdjusters.firstDayOfNextYear());
        System.out.println("date值,明年的元旦:"+date);
        date=date.with(temproalAdjusters.firstDayOfYear());
        System.out.println("date值,当年的元旦:"+date);
        date=date.with(temproalAdjusters.firstInMonth(DayOfWeek.MONDAY));
        System.out.println("与date月份相同的第一个星期一:"+date);
        date=date.with(temproalAdjusters.lastDayOfMonth());
        System.out.println("与date值相同月份的最后一天:"+date);
        date=date.with(temproalAdjusters.lastDayOfYear());
        System.out.println("与date值相同年份的最后一天:"+date);
        date=date.with(temproalAdjusters.lastInMonth(DayOfWeek.TUESDAY));
        System.out.println("与date值同一个月中，最后一个符合星期二的值:"+date);
        date=date.with(temproalAdjusters.next(DayOfWeek.MONDAY));
        System.out.println("date日期以后的第一个匹配星期一"+date);
        date=date.with(temproalAdjusters.previous(DayOfWeek.MONDAY));
        System.out.println("date日期以前的第一个匹配星期一:"+date);
        date=date.with(temproalAdjusters.nextOrSame(DayOfWeek.MONDAY));
        System.out.println("date日期以后的第一个匹配星期一,如何date符合直接返回:"+date);
        date=date.with(temproalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        System.out.println("date日期以前的第一个匹配星期一,如何date符合直接返回:"+date);

    }
}
