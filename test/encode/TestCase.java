package encode;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**编码相关
 * Created by lvm on 2016/6/23.
 */
public class TestCase {

    public static Logger log = LogManager.getLogger(TestCase.class);

    /**
     * url编码字符串
     * @throws UnsupportedEncodingException
     */
    @Test
    public void urlEncode() throws UnsupportedEncodingException {
        String url = URLEncoder.encode("http://ezonsport.com/test?pageName=orderTest", "UTF-8");
        log.info(url);
        String msg = URLEncoder.encode("【宜准跑步】验证码：5972，您正通过手机注册宜准跑步账号。30分钟内有效，请勿泄露。", "UTF-8");
        log.info(msg);
    }
}
