package number;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

/**
 *
 */
public class MathTestExample {
    public static Logger log = LogManager.getLogger(MathTestExample.class);

    /**
     * 关于cos的函数使用
     */
    @Test
    public void testCos(){
        //toRadians 是将角度值转成弧度值
        log.info(Math.cos(Math.toRadians(85.9)));
        log.info(Math.cos(Math.toRadians(86)));
        //toDegrees 是将弧度值转成角度值
        log.info(Math.cos(Math.toDegrees(85.9)));
        log.info(Math.cos(Math.toDegrees(85.8)));
        log.info(Math.cos(Math.toDegrees(86)));
        log.info(Math.cos(Math.toDegrees(86.1)));
        log.info(Math.cos(Math.toDegrees(87)));

    }
}
