package str;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

/**
 * StringBuffer测试类
 */
public class StringBufferTestCase {
    public static Logger log = LogManager.getLogger(StringBufferTestCase.class);

    @Test
    public void testReplace(){
        String text = "select * from t_student  where 1=1 ";
        StringBuffer buffer = new StringBuffer(text);
        buffer.replace(buffer.indexOf("where 1=1"),
                buffer.indexOf("where 1=1") + 10,"替换的文本");
        log.info(buffer.toString());
    }
}
