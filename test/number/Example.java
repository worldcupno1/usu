package number;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by lvm on 2016/3/9.
 */
public class Example {
    public static Logger log = LogManager.getLogger(Example.class);

    //取指定范围的int值,下限（即第二个参数）是不包含的
    @Test
    public void nextInt(){
        Random random = new Random();
        random.nextInt(10);
        int k = 0;

        for (int i = 0; i < 100; i++) {
//            log.info(RandomUtils.nextInt(1,9));
            k = org.apache.commons.lang.math.RandomUtils.nextInt(random,5);
            log.info(k);
        }
    }

    //取指定范围的long值,下限（即第二个参数）是不包含的
    @Test
    public void nextLong(){
        log.info(RandomUtils.nextLong(10000l,10000000l));
    }

    //判断一个数字字符串是否小数
    @Test
    public void isNumeric(){
        String n = "38.891025";
        //这个无法判断小数和负数
        log.info(StringUtils.isNumeric(n));
        //这个可以判断小数
        log.info(NumberUtils.isCreatable(n));
    }

    @Test
    public void testDig(){
        float n = 97.27f;
        double z = Math.floor(n*10000/263500);
        int num = (int)z;
        log.info(z);
        log.info(num);

        int percent = Math.round(n*10000%263500/263500 *100);
        log.info(percent);
    }

    @Test
    public void testDig2(){
        float n = 365f;

        int percent = Math.round(n/1000 *100);
        log.info(percent);
    }

    @Test
    public void testDig3(){
        Integer i = 480;
        log.info(i.byteValue());
        log.info(i.shortValue());
    }

    @Test
    public void ifnull(){
        Integer i = null;
        /*if (i >0){
            log.info("error");
        }*/
        if (i == null || i > 0){
            log.info("go on");
        }
    }

    /**
     * 关于BigDecimal 的累加
     */
    @Test
    public void sum(){
        BigDecimal sum = new BigDecimal(0);
        ArrayList<BigDecimal> list = new ArrayList<>();
        list.add(new BigDecimal(2));
        list.add(new BigDecimal(3));
        list.add(new BigDecimal(3.974));
        list.add(null);
        HashMap<String,Object> m = new HashMap<>();
        m.put("field",list);
        for (int i = 0; i < list.size(); i++) {
            if (null != list.get(i)){
                sum = sum.add(list.get(i));
                log.info("yes,add" + sum.add(list.get(i)));
            }

        }
        log.info("sum=" + sum);
    }

    /**
     * 关于BigDecimal 和 0
     */
    @Test
    public void aboutZero(){
        BigDecimal a = new BigDecimal(0.1);
        log.info(0.1 == a.doubleValue());
        log.info(a.equals(new BigDecimal(0.1)));

        BigDecimal b = new BigDecimal(0.0);
        log.info(0.0 == b.doubleValue());
        log.info(b.equals(new BigDecimal(0.0)));
        /*
            这个错误一般出现在BigDecimal的除法运算中，如：
            像这样不做小数位数处理的话就有可能会报错
            BigDecimal result= num1.divide(num2)
        正确的：
            //设置了保留几位，这里是两位✔
            BigDecimal result= num1.divide(num2,2)；
         */


    }

}
