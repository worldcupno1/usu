package useful.application.wechat;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import test.emax.TestHttpClient;
import util.http.HttpUtil;

import java.util.Map;

/**
 * 类似于微信的网页提交工具
 * Created by lvm on 2016/3/21.
 */
public class PostHandle {
    private static Logger log = LogManager.getLogger(PostHandle.class);

    static final String PRODUCE_Header = "http://121.40.218.161/phone?";

    static final String URL_GET_MENU = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
    static final String URL_GET_USER_INFO = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN" +
            "&openid=OPENID&lang=zh_CN";
    static final String URL_SET_STEP = "https://api.weixin.qq.com/hardware/bracelet/setstep?" +
            "access_token=ACCESS_TOKEN&openid=OPENID&timestamp=TIMESTAMP&step=STEP";
    static final String URL_UPDATE_DEVICE_INFO = "https://api.weixin.qq.com/device/authorize_device?" +
            "access_token=ACCESS_TOKEN";


    static String getToken() throws Exception {
        TestHttpClient t = new TestHttpClient();
        t.setUseHeader(PRODUCE_Header);
        String ret = t.queryToken(t);
        JSONObject j = JSONObject.parseObject(ret);
        Map<String,String> map = (Map)j.get("data");
        String token = map.get("accessToken");
//        log.info(token);
        return token;
    }

    static void getMenu(String token){
        String url = URL_GET_MENU.replace("ACCESS_TOKEN",token);
        String result = HttpUtil.executePost(url,"none");
        log.info(result);
    }

    static void getUserInfo(String token,String openid,String lang){
        String url = URL_GET_USER_INFO.replace("ACCESS_TOKEN",token).replace("OPENID",openid);
        if (StringUtils.isNotBlank(lang)){
            url = url.replace("zh_CN",lang);
        }

        String result = HttpUtil.executePost(url,"none");
        log.info(result);
    }

    /**
     * 设置微信运动步数
     * @param token
     * @param openid
     * @param setpNum
     */
    static void setStep(String token,String openid,int setpNum){
        String url = URL_SET_STEP.replace("ACCESS_TOKEN",token).replace("OPENID",openid);
        String timestamp = String.valueOf(System.currentTimeMillis()/1000);
        url = url.replace("TIMESTAMP",timestamp);
        url = url.replace("STEP",String.valueOf(setpNum));
        String result = HttpUtil.executePost(url,"none");
        log.info(result);
    }

    static void updateDeviceInfo(String token,String json){
        String url = URL_UPDATE_DEVICE_INFO.replace("ACCESS_TOKEN",token);
        String result = HttpUtil.executePost(url,json);
        log.info(result);
    }
    /**
     * @param args
     */
    public static void main(String[] args) {

        try {
//            getMenu(getToken());
//            getUserInfo(getToken(),"o3OHZwhdtAqOHg2CqkSEZpESEGj8",null);
            setStep(getToken(),"o3OHZwjKiRyWueR4MIeTIiE8Tiy8",1100);
            /*updateDeviceInfo(getToken(),"{\"device_num\":\"1\",\n" +
                    "\"device_list\":[{\n" +
                    "\"id\":\"S1_000015\",\n" +
                    "\"mac\":\"E0E5CF1AFDC7\",\n" +
                    "\"connect_protocol\":\"3\",\n" +
                    "\"auth_key\":\"\",\n" +
                    "\"close_strategy\":\"2\",\n" +
                    "\"conn_strategy\":\"5\",\n" +
                    "\"crypt_method\":\"0\",\n" +
                    "\"auth_ver\":\"0\",\n" +
                    "\"manu_mac_pos\":\"-1\",\n" +
                    "\"ser_mac_pos\":\"-2\"}],\n" +
                    "\"op_type\":\"1\",\n" +
                    "\"product_id\": \"3860\"}");*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
