package dateTime;

import cn.hutool.core.date.DateUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.junit.Test;

import static java.time.ZoneOffset.UTC;

/**
 * Created by unique-lv on 2016/3/20.
 */
public class Example {
    private static Logger log = LogManager.getLogger(Example.class);

    public static final String common = "yyyy-MM-dd HH:mm:ss";
    public static final String rankDateFormat = "yyMMdd";

    @Test
    public void useHutools(){
        //当前格林威治时间
        // 获取格林威治标准时区
        TimeZone timeZone1 = TimeZone.getTimeZone("GMT");
        //设置程序的默认时区是 格林威治标准时区
        TimeZone.setDefault(timeZone1);
        log.info("当前utc时间是" + DateUtil.now());
    }


    /**
     * 毫秒数和日期字符串互转
     */
    @Test
    public void formatDate(){
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat dateFm = new SimpleDateFormat(pattern);
        log.info("当前时间毫秒数" + new Date().getTime());

        //毫秒数转化为日期字符串
        Long time = new Long("1454511703672");
        log.trace(dateFm.format(time));

        //日期字符串转化为毫秒数
        try {
            Date date = dateFm.parse("2016-10-27 14:37:47");
            log.info("日期转化的毫秒数=" + date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }



    @Test
    public void timeZone(){
        Date e = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(e);
        c.add(Calendar.HOUR,2);

        log.info(c.getTimeZone());
        log.info((float) TimeZone.getDefault().getRawOffset());
        log.info((int) (((float) TimeZone.getDefault().getRawOffset()) / 1000f / 3600f * 60f));
    }


    /**
     * 时间和日期的调整
     */
    @Test
    public void adjustDateTime(){
        Date e = new Date();
        float diff = -1.5f;
        SimpleDateFormat f = new SimpleDateFormat(common);
        log.info(f.format(e));
        Calendar c = Calendar.getInstance();
        c.setTime(e);
        //按秒加减
        c.add(Calendar.SECOND,(int)(diff*3600));
        log.info(f.format(c.getTime()));

        //按分加减
        c.add(Calendar.MINUTE,-10);
        log.info(f.format(c.getTime()));

        //按小时加减
        c.add(Calendar.HOUR,1);
        log.info(f.format(c.getTime()));
        //按日加减
        c.add(Calendar.DATE,-1);
        log.info(f.format(c.getTime()));
        //按星期加减
        c.add(Calendar.WEEK_OF_YEAR,1);
        log.info(f.format(c.getTime()));
        //按月加减
        c.add(Calendar.MONTH,1);
        log.info(f.format(c.getTime()));
        //按年加减
        c.add(Calendar.YEAR,1);
        log.info(f.format(c.getTime()));
    }

    /**
     * 获取指定日期本周最后一天的格式化字符串
     * @return
     */
    @Test
    public void getLastDayOfSpecifiedWeek() throws ParseException {
        String queryDate="160301";
        String format="yyMMdd";
        SimpleDateFormat f = new SimpleDateFormat(format);

        Calendar c = Calendar.getInstance();
        java.util.Date d = f.parse(queryDate);
        c.setTime(d);
        //这种输出的是上个星期周日的日期，因为老外那边把周日当成第一天
        c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        //增加一个星期，才是我们中国人理解的本周日的日期
        c.add(Calendar.WEEK_OF_YEAR, 1);
        log.info(f.format(c.getTime()));
        assert f.format(c.getTime()).equals("160306");
//        return f.format(c.getTime());
    }

    /**
     * 调整日期
     */
    @Test
    public void adjustDate(){
        Calendar c = Calendar.getInstance();//获得一个日历的实例
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = null;
        try{
            date = sdf.parse("2016-03-04");//初始日期
        }catch(Exception e){

        }
        c.setTime(date);//设置日历
        c.add(Calendar.DAY_OF_MONTH, -4);
        String strDate = sdf.format(c.getTime());
        log.info(strDate);
    }

    /**
     * 获取当前秒数
     */
    @Test
    public void getSeconds(){
        System.out.println(new Date().getTime());
        System.out.println(System.currentTimeMillis());
        System.out.println(System.currentTimeMillis()/1000);
        System.out.println(System.currentTimeMillis()%1000);
    }



    /**
     * 获取本周最后一天的格式化字符串
     * @return
     */
    @Test
    public void getLastDayOfThisWeek() throws ParseException {
        String date = "160606";
        SimpleDateFormat f = new SimpleDateFormat(rankDateFormat);
        Date d = f.parse(date);
        Calendar c = Calendar.getInstance();
        log.info(f.format(c.getTime()));
        c.setTime(d);
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        c.add(Calendar.DATE, -day_of_week + 7);
        log.info(f.format(c.getTime()));
    }

    /**
     * 获取本周第一天的格式化字符串
     * @return
     */
    @Test
    public void getFirstDayOfThisWeek() throws ParseException {
        String date = "160605";
        SimpleDateFormat f = new SimpleDateFormat(rankDateFormat);
        Date d = f.parse(date);
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        c.add(Calendar.DATE, -day_of_week + 1);
        log.info(f.format(c.getTime()));
    }

}
