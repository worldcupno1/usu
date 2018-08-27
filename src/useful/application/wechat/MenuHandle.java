package useful.application.wechat;

import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import test.emax.TestHttpClient;
import test.weixin.*;
import util.HttpUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lvm on 2015/12/15.
 */
public class MenuHandle {
    private static Logger log = LogManager.getLogger(MenuHandle.class);


    static final String PRODUCE_Header = "http://121.40.218.161/phone?";
    static final String URL_GET_MENU = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";

    static final String URL_CREATE_MENU = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

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
    /**
     * @param args
     */
    public static void main(String[] args) {
        //生成json菜单
        ClickButton btn1 = new ClickButton();
        btn1.setName("排行榜");
        btn1.setType("click");
        btn1.setKey("myRankInfo");

        ViewButton v1 = new ViewButton();
        v1.setName("宜准商城");
        v1.setType("view");
        v1.setUrl("http://www.ezonmall.com");
        /*v1.setName("跳转");
        v1.setType("view");
        v1.setUrl("http://proj.applinzi.com/test");*/

        ViewButton v2 = new ViewButton();
        v2.setName("常见问题");
        v2.setType("view");
        v2.setUrl("http://ezonsport.com/pic/q&a.html");

        ViewButton v3 = new ViewButton();
        v3.setName("测试，请勿点击");
        v3.setType("view");
        v3.setUrl("http://ezonsport.com/test?pageName=orderTest");

        ViewButton v4 = new ViewButton();
        v4.setName("测试，请勿点击");
        v4.setType("view");
//        v4.setUrl("http://www.ezonsport.com/test");
        v4.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxa108b6a713cf30c7&redirect_uri=http%3A%2F%2Fezonsport.com%2Ftest%3FpageName%3DorderTest&response_type=code&scope=snsapi_base&state=123#wechat_redirect");

        ClickButton c1 = new ClickButton();
        c1.setName("测试数据，勿点");
        c1.setType("click");
        c1.setKey("CT");

        ComplexButton btn2 = new ComplexButton();
        btn2.setName("我的主页");
//        btn2.setSub_button(new Button[]{v1, v2, v3,v4,c1});

        ViewButton v5 = new ViewButton();
        v5.setName("F主页");
        v5.setType("view");
        v5.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxa108b6a713cf30c7&redirect_uri=http%3A%2F%2Fezonsport.com%2Ftest%3FpageName%3Dmain&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect");


        btn2.setSub_button(new Button[]{v4,c1});

        ComplexButton btn3 = new ComplexButton();
        btn3.setName("我的主页");
        btn3.setSub_button(new Button[]{v5,v4,c1});

        //创建菜单
        Menu menu = new Menu();
        menu.setButton(new Button[]{v5,btn1,v1});
//        menu.setButton(new Button[]{v5,v1});
        String j = JSONObject.toJSONString(menu);
        System.out.println(j);

        try {
            MenuHandle.createMenu(getToken(),j);
        } catch (Exception e) {
            log.error("执行出错",e);
        }


        /*ArrayList<String> device_id_list = new ArrayList<String>();
        device_id_list.add("deviceid");
        HashMap<String, Object> hmap = new HashMap<String, Object>();
        hmap.put("device_num", "1");
        hmap.put("device_id_list", device_id_list);
        String postParam2 = JSONObject.toJSONString(hmap);
//        System.out.println(postParam2);

        String re = "{\"code_list\":[{\"ticket\":\"http://we.qq.com/d/AQDc4dnC7P9JCb2u1kYv4La6p_9sySN17QqO615e\",\"device_id\":\"111111\"}],\"device_num\":1,\"errmsg\":\"ok\",\"errcode\":0}";
        JSONObject o = JSONObject.parseObject(re);
//        System.out.println(((Map<String, String>) o.getObject("code_list", List.class).get(0)).get("ticket"));*/

    }

    static void createMenu(String token,String json){
        String url = URL_CREATE_MENU.replace("ACCESS_TOKEN",token);
        String result = HttpUtil.executePost(url,json);
        log.info(result);
    }
}
