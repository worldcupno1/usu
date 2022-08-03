package number;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

/**
 * 字节相关测试
 */

public class ByteTestExample {
    public static Logger log = LogManager.getLogger(ByteTestExample.class);

    @Test
    public void basic(){
        byte a = 0x01;
        log.info((1 == a) + "" );
        log.info(Integer.parseInt(String.valueOf((byte)0x11)));
        log.info(String.valueOf((byte)0x11));
    }
}
