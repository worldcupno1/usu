package encypt;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

/**关于加密、解密
 * Created by lvm on 2016/4/27.
 */
public class Testcase {
    private static Logger log = LogManager.getLogger(Testcase.class);

    /**
     * md5加密,不可逆
     */
    @Test
    public void md5Test() {
        StringBuilder input = new StringBuilder();
        input.append("testappkey").append("testsecret").append("1405495206727");
        System.out.print("32bit result:" + DigestUtils.md5Hex(input.toString()) + "\n");
        System.out.print("16bit result:"+ DigestUtils.md5Hex(input.toString()).substring(8, 24) + "\n");

        input.setLength(0);
        input.append("testappkey").append("testappsecret");
        log.info("只有一个appKey时的签名," + DigestUtils.md5Hex(input.toString()));

        input.setLength(0);
        input.append("testappkey").append("testappsecret").append("1415250311646");
        log.info("多个参数的签名," + DigestUtils.md5Hex(input.toString()));

        input.setLength(0);
        input.append("testappkey").append("testappsecret").append("兑吧");
        log.info("带中文参数的签名," + DigestUtils.md5Hex(input.toString()));

        log.info(DigestUtils.md5Hex("CS705132"));
        try {
            log.info(DigestUtils.md5Hex(DigestUtils.md5Hex("111".getBytes("utf-8"))));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * SHA1加密,不可逆
     */
    @Test
    public void SHA1Test(){
        String str = "abc";
        log.info(DigestUtils.sha1(str));
    }


    /**
     * base64加密 、解密,可逆
     */
    @Test
    public void Base64Test(){
        //加密
        String str= "abc"; // abc为要加密的字符串
        byte[] b = Base64.encodeBase64(str.getBytes(), true);
        System.out.println(new String(b));
        //解密
        String str2 = "YWJj"; // YWJj为要解密的字符串
        byte[] ba = Base64.decodeBase64(str2.getBytes());
        System.out.println(new String(ba));
    }


}
