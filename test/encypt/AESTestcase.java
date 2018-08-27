package encypt;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;

public class AESTestcase {
    private static Logger log = LogManager.getLogger(AESTestcase.class);

    /**
     * AES密钥长度可以选择128位【16字节】，192位【24字节】和256位【32字节】密钥（其他不行，因此别乱设密码哦）。
     */

    /**使用AES对字符串加密
     * @param str utf8编码的字符串
     * @param key 密钥（16字节）
     * @return 加密结果
     * @throws Exception
     */
    public  byte[] aesEncrypt(String str, String key) throws Exception {
        if (str == null || key == null) return null;
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes("utf-8"), "AES"));
        byte[] bytes = cipher.doFinal(str.getBytes("utf-8"));
        return  bytes;
    }
    /**使用AES对数据解密
     * @param bytes utf8编码的二进制数据
     * @param key 密钥（16字节）
     * @return 解密结果
     * @throws Exception
     */
    public  String aesDecrypt(byte[] bytes, String key) throws Exception {
        if (bytes == null || key == null) return null;
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes("utf-8"), "AES"));
        bytes = cipher.doFinal(bytes);
        return new String(bytes, "utf-8");
    }
    /**
     * 上面代码是对字符串进行的加解密。但要注意的是AES算法的所有参数都是字节码的（包括密钥）。因此字符串字符需要转换成
     * 字节码后进行加密str.getBytes("utf-8")按照字符串的编码进行转换。另外参数：”AES/ECB/PKCS5Padding”在加密和解密时
     * 必须相同，可以直接写”AES”,这样就是使用默认模式（C#和java默认的模式不一样，C#中默认的是这种,java的默认待研究）。
     * 分别的意思为：AES是加密算法，ECB是工作模式，PKCS5Padding是填充方式。
     AES是分组加密算法，也称块加密。每一组16字节。这样明文就会分成多块。当有一块不足16字节时就会进行填充。
     一共有四种工作模式：

     ECB 电子密码本模式：相同的明文块产生相同的密文块，容易并行运算，但也可能对明文进行攻击。
     CBC 加密分组链接模式：一块明文加密后和上一块密文进行链接，不利于并行，但安全性比ECB好，是SSL,IPSec的标准。
     CFB 加密反馈模式：将上一次密文与密钥运算，再加密。隐藏明文模式，不利于并行，误差传递。
     OFB 输出反馈模式：将上一次处理过的密钥与密钥运算，再加密。隐藏明文模式，不利于并行，有可能明文攻击，误差传递。
     PKCS5Padding的填充方式是差多少字节就填数字多少；刚好每一不足16字节时，那么就会加一组填充为16.还有其他填充模式
     【Nopadding,ISO10126Padding】（不影响算法，加密解密时一致就行）。
     */

    /**
     * AES加解密
     */
    @Test
    public void aesTest() throws Exception {
        String key = "c0e9fcff59ecc3b8b92939a1a2724a44";

        byte[] bytes = aesEncrypt("abc",key);
        log.info("加密字符串=" + bytes.toString());
        String result = aesDecrypt(bytes,key);
        log.info("解密后字符串=" + result);
    }

    /**
     * 随机生成秘钥
     */
    @Test
    public void getKey(){
        try {
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(128);//要生成多少位，只需要修改这里即可128, 192或256
            SecretKey sk = kg.generateKey();
            byte[] b = sk.getEncoded();
            log.info(String.valueOf(b));
            String s = byteToHexString(b);
            System.out.println(s);
            System.out.println("十六进制密钥长度为"+s.length());
            System.out.println("二进制密钥的长度为"+s.length()*4);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            System.out.println("没有此算法。");
        }
    }
    /**
     * byte数组转化为16进制字符串
     * @param bytes
     * @return
     */
    public  String byteToHexString(byte[] bytes){
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String strHex=Integer.toHexString(bytes[i]);
            if(strHex.length() > 3){
                sb.append(strHex.substring(6));
            } else {
                if(strHex.length() < 2){
                    sb.append("0" + strHex);
                } else {
                    sb.append(strHex);
                }
            }
        }
        return  sb.toString();
    }

    /**
     * 使用对称密钥进行加密
     */
    @Test
    public  void getA231() throws Exception{
        String keys = "c0e9fcff59ecc3b8b92939a1a2724a44";   //密钥
        byte[] keyb = hexStringToByte(keys);
        String mingwen = "hello word!";                         //明文
        SecretKeySpec sKeySpec = new SecretKeySpec(keyb, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, sKeySpec);
        byte[] bjiamihou = cipher.doFinal(mingwen.getBytes());
        System.out.println(byteToHexString(bjiamihou));     //加密后数据为ecf0a6bc80dbaf657eac9b06ecd92962
    }

    /**
     * 使用对称密钥进行解密
     */
    @Test
    public  void getA232() throws Exception{
        String keys = "c0e9fcff59ecc3b8b92939a1a2724a44";   //密钥
        byte[] keyb = hexStringToByte(keys);
        String sjiami = "ecf0a6bc80dbaf657eac9b06ecd92962"; //密文
        byte[] miwen = hexStringToByte(sjiami);
        SecretKeySpec sKeySpec = new SecretKeySpec(keyb, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, sKeySpec);
        byte[] bjiemihou = cipher.doFinal(miwen);
        System.out.println(new String(bjiemihou));
    }

    /**
     * 自动生成AES128位密钥
     */
    public  void getA221(){
        try {
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(128);//要生成多少位，只需要修改这里即可128, 192或256
            SecretKey sk = kg.generateKey();
            byte[] b = sk.getEncoded();
            String s = byteToHexString(b);
            System.out.println(s);
            System.out.println("十六进制密钥长度为"+s.length());
            System.out.println("二进制密钥的长度为"+s.length()*4);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            System.out.println("没有此算法。");
        }
    }



    /**
     * 十六进制string转二进制byte[]
     */
    public static byte[] hexStringToByte(String s) {
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                System.out.println("十六进制转byte发生错误！！！");
                e.printStackTrace();
            }
        }
        return baKeyword;
    }
}
