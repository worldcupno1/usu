import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 普通的测试类，有些方法经常忘了怎么用。。
 */
public class Usu {
	public static Logger log = LogManager.getLogger(Usu.class);


    @Test
    public void ge(){
        String a = "20180805";
        String b = "20180809";
        log.info(b.compareTo(a));
        BigDecimal decimal = new BigDecimal("1");
        log.info(1 == decimal.intValue());
        log.info(decimal.toString().equals("1"));
    }

	@Test
    public void getString(){
	    String equ = "%253D";
	    String yu = "%2526";
        StringBuilder b = new StringBuilder();
        b.append("https://d.alipay.com/i/index2.htm?scheme=alipays%3A%2F%2Fplatformapi%2Fstartapp%3FappId%3D20000067%26url%3Dhttps%253A%252F%252Fopenauth.alipay.com%252Foauth2%252FpublicAppAuthorize.htm%253Fapp_id%253D2016112803443590%2526scope%253Dauth_userinfo%2526redirect_uri%253Dhttps%253A%252F%252Fk12jiaofeisit.alipay-eco.com%252Fcallback%252Fhome%253Ftype%253D7");
        b.append(yu).append("schoolCode").append(equ).append("35010200000197");
        b.append(yu).append("studentNo").append(equ).append("5a2b9e27fc4fee2063e020e8");
        b.append(yu).append("value").append(equ).append("350421198409230013");
        b.append(yu).append("isvNo").append(equ).append("5a240d64c71c88588ffd6ca5");
        b.append(yu).append("orderno").append(equ).append("5a2fc1c6c71c8842b2f8ae63");
        log.info(b.toString());
    }


    @Test
    public void urlencode() throws UnsupportedEncodingException {
        log.info("https://d.alipay.com/i/index2.htm?scheme=" + URLEncoder.encode("alipays://platformapi/startapp?appId=20000067&url=https://openauth.alipay.com/oauth2/publicAppAuthorize.htm?app_id=2016112803443590&scope=auth_userinfo&redirect_uri=https://k12jiaofeisit.alipay-eco.com/callback/home?type=7&schoolCode=35010200000190&studentNo=5a2b9e27fc4fee2063e020e8&value=350421198409230013&isvNo=2088821414288163&orderno=5a2b9e27fc4fee2063e020f7"));
        log.info("https://d.alipay.com/i/index2.htm?scheme=" + URLEncoder.encode("alipays://platformapi/startapp?appId=20000067&url=https://openauth.alipay.com/oauth2/publicAppAuthorize.htm?app_id=2016112803443590&scope=auth_userinfo&redirect_uri=https://k12jiaofeisit.alipay-eco.com/callback/home?type=7&schoolCode=35010200000190&studentNo=5a2b9e27fc4fee2063e020e8&value=350421198409230013&isvNo=2088821414288163&orderno=5a2b9e27fc4fee2063e020f7"),"UTF-8");

        //解码
        log.info(URLDecoder.decode("https://d.alipay.com/i/index2.htm?scheme=alipays%3A%2F%2Fplatformapi%2Fstartapp%3FappId%3D20000067%26url%3Dhttps%253A%252F%252Fopenauth.alipay.com%252Foauth2%252FpublicAppAuthorize.htm%253Fapp_id%253D2016112803443590%2526scope%253Dauth_userinfo%2526redirect_uri%253Dhttps%253A%252F%252Fk12jiaofeisit.alipay-eco.com%252Fcallback%252Fhome%253Ftype%253D7%2526schoolCode%253D61011300000262%2526studentNo%253D59706e43c71c88359fea2f93%2526value%253D15389188268%2526isvNo%253D5861f007fc4fee0da8188207%2526orderno%253D59706e43c71c88359fea2f96","utf-8"));
    }

    //格式化字符串
    @Test
    public void formatStr(){
        log.info(String.format("%06d", 99));
    }

    @Test
    public void mapOrder(){
        StringBuilder b = new StringBuilder();
        Map a = new HashMap();
        a.put("zid",1233);
        a.put("appkey","appkey");

        a.put("user",493);
        Iterator entries = a.entrySet().iterator();

        while (entries.hasNext()) {
            Map.Entry entry = (Map.Entry) entries.next();
            b.append(entry.getKey()).append("=").append(entry.getValue() + "&");
        }

        log.info(b);
    }

    @Test
    public void ascii(){
        byte[] code = new byte[20];
        code[0] = 0x45;
        code[1] = 0x5a;
        code[2] = 0x4f;
        code[3] = 0x4e;
        code[7] = 0x06;
        code[8] = 0x01;

        String contentStr = null;
        try {
            contentStr = new String(code,"ascii");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        log.info(contentStr);
    }

    @Test
    public void uuidTest(){
        String a  = UUID.randomUUID().toString();
        log.info(a);
        log.info(UUID.randomUUID());
    }

    @Test
    public void setLength(){
        StringBuilder s = new StringBuilder();
        s.append("12345678");
        s.setLength(s.length() - 2);
        log.info(s.toString());
    }

    @Test
    public void parseJsonText(){
        String t = "{\"errcode\":0,\"errmsg\":\"ok\",\"device_num\":1," +
                "\"code_list\":[{\"device_id\":\"EZON_S1_FFFFEC\"," +
                "\"ticket\":\"http:\\/\\/we.qq.com\\/d\\/AQDc4dnCHfBdqGjOnhaxN8BWG-RyKlhJOij5UB_B\"}]}";
        JSONObject j = JSONObject.parseObject(t);

        List<Map> parseList = JSONObject.parseObject(j.getString("code_list"),new TypeReference<List<Map>>(){});
        log.info(parseList.size());
        log.info(parseList.get(0).get("ticket"));
    }

    @Test
    public void escapeStr(){
        String ticket = "http:\\/\\/we.qq.com\\/d\\/AQDc4dnCHfBdqGjOnhaxN8BWG-RyKlhJOij5UB_B";
        log.info(ticket.replace("\\",""));
    }

    /**
     * 十进制转16进制
     */
    @Test
    public  void hex2dec(){
        byte b = (byte)0x01;
        String hex = Integer.toHexString(b & 0xFF);
        if (hex.length() == 1) {
            hex = '0' + hex;
        }
        log.info(hex);
        String dec = String.valueOf(Integer.parseInt(hex,16));
        if (dec.length() == 1) {
            dec = '0' + dec;
        }
        log.info(dec);
//        return dec;
    }



    /**
     * 延迟几秒
     */
    @Test
    public void timeSleep(){
        log.info("计时开始");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("计时结束");
    }

    /**
     * 截取字符串
     */
    @Test
    public void substr(){
        String s = new String("这个例子不明显呀");
        log.info(s.substring(4,6));
        log.info(s.substring(s.length() - 1));
        log.info(s.substring(0).equals(s.substring(0,s.length())));

        String u = "1123<returnstatus>success</returnstatus>123333fdfg";
		System.out.println(u.substring(u.indexOf("<returnstatus>") + 14, u.indexOf("</returnstatus>")));
    }


    /**
     * 比较字符串的大小
     */
    @Test
    public void compareString(){
        assert "160209".compareTo("160201") > 0;
//        log.info();
    }



    /**
     * 正则表达式
     */
    @Test
    public void regex(){
		Pattern p1 = null,p2 = null;
        Matcher m1 = null,m2 = null;
        String phone = "059183455182";
        //固话号码，前面四位为区号0591－0599，区号后跟7－8位数字
        p2 = Pattern.compile("^059[1-9][0-9]{7,8}$");
        m2 = p2.matcher(phone);
        System.out.println(m2.matches());
    }



    /*
        获取当前系统信息
     */
    @Test
    public void getSystemInfo(){
        System.out.println("===========os.name:"+System.getProperties().getProperty("os.name"));
		System.out.println("===========" + System.getProperties().getProperty("os.name").contains("Windows"));
		if (System.getProperties().getProperty("os.name").toLowerCase().contains("linux")){
			System.out.println("linux");
		}
		if (System.getProperties().getProperty("os.name").toLowerCase().contains("centos")){
			System.out.println("centos");
		}
    }

    /**
     * 随机数
     */
    @Test
    public void randomNum(){
        Random ran=new Random();
		double n = ran.nextDouble();
		System.out.println(String.valueOf(n).substring(2, 6));
		System.out.println(n);
    }



    /**
     * 特殊字符分割字符串
     */
    @Test
    public void splitStr(){
        String u = "2015048350001A1100000000KDEPB0001A1100000000IDOLFNY1001A1100000000005ZN2007-08-152014-09-016s1WmDVNFWE=vRfjl+XbqeUZ6GrzAi4Igw==四川广元JBR/Hr7HpXu4fxzItVC7ZpFndtfY72IhCcXREfHVXKg=xi3TGBsPl07bidIz0rkDzrWPWwkaLUQ7O/7wiLt5Ahc=1978-09-06~~2VPkGK3MrmFW686DmwMF/g==1ci24eGDAIYPmdZQGDkxtk8xQOJpGVOuJSSobz3FnY4=2004-07-012VPkGK3MrmFW686DmwMF/g==KsTe9WNPZVo=6StvgPnVtZA=0001A1100000000005F316StvgPnVtZA=1001A1100000000005XP7gl8jOuzy11hPc3mWP9YWfDKsZGBFvUYWuBgiyFocOU=1001A1100000000005YD1001A1100000000005XR1001A11000000000063W四川广元6StvgPnVtZA=6StvgPnVtZA=y4BHKtniSp8=~351040712014-10-30 22:12:031001A11000000005RN2H0~018906917896@189.cn2N2014-12-23 16:48:101001A11000000012C9J30001A1100000000IDOLF2015-03-23 14:11:39~2004.071001A1100000000006APN~1978.091001A1100000000006B2~~~NN";

//        String []ary = u.split("\u0024");
        String []ary = StringUtils.splitByWholeSeparatorPreserveAllTokens(u, "");
        System.out.println(StringUtils.countMatches(u, ""));
        System.out.println(ary.length);
    }

    @Test
    public void splitStr2(){
        String url = "uid=9383&sdi=9393&jd=确实啊&djf=opoi&";
        String []ary = StringUtils.split(url,"&");
        log.info(ary.length);
    }

    //随机字符串产生
    @Test
    public void randomStr(){
        // 产生5位长度的随机字符串，中文环境下是乱码
        String r = RandomStringUtils.random(5);
        System.out.println(r);

        // 使用指定的字符生成5位长度的随机字符串
        r = RandomStringUtils.random(5, new char[] { 'a', 'b', 'c', 'd', 'e',
                'f', '1', '2', '3' });
        System.out.println(r);

        // 生成指定长度的字母和数字的随机组合字符串
        r = RandomStringUtils.randomAlphanumeric(5);
        System.out.println(r);

        // 生成随机数字字符串
        r = RandomStringUtils.randomNumeric(5);
        System.out.println(r);

        // 生成随机[a-z]字符串，包含大小写
        r = RandomStringUtils.randomAlphabetic(5);
        System.out.println(r);

        // 生成从ASCII 32到126组成的随机字符串
        r = RandomStringUtils.randomAscii(4);
        System.out.println(r);
    }

    /**
     * 去除list中重复的string
     */
    @Test
    public void ListRepeatTest(){
        List<String> list = new ArrayList<String>();
        list.add("1");
        list.add("1");
        list.add("2");
        list.add("3");
        Set<String> set = new LinkedHashSet<String>();
        set.addAll(list);
        list.clear();
        list.addAll(set);
        log.info(list);
    }
}
