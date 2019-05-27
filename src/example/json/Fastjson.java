/**
 * 
 */
package example.json;

import java.util.*;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import constant.Common;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.junit.Test;
import test.weixin.*;

/**
 * @author lvm
 *
 */
public class Fastjson {
    private static Logger log = LogManager.getLogger();

    @Test
    public void testToJsonString(){
        //fastjson简单实用例子
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("username", "zhangsan");
        map.put("age", 24);
        map.put("sex", new Date());

        //map集合
        HashMap<String, Object> temp = new HashMap<String, Object>();
        temp.put("name", "xiaohong");
        temp.put("age", "23");
        map.put("girlInfo", temp);

        //list集合
        List<String> list = new ArrayList<String>();
        list.add("爬山");
        list.add("骑车");
        list.add("旅游");
        map.put("hobby", list);

        /*JSON 序列化，默认序列化出的JSON字符串中键值对是使用双引号，如果需要单引号的JSON字符串， [eg:String jsonString = JSON.toJSONString(map,   SerializerFeature.UseSingleQuotes);]
         *fastjson序列化时可以选择的SerializerFeature有十几个属性，你可以按照自己的需要去选择使用。*/

        //根据自定义格式输出日期
        String jsonString2 = JSON.toJSONStringWithDateFormat(map, Common.DATE_24_HOURS_FORMAT , SerializerFeature.WriteDateUseDateFormat);
        log.debug("JSON=" + jsonString2);
    }

    @Test
    public void testParseObject(){
        List<String> liststr = new ArrayList<String>();
        liststr.add("fastjson1");
        liststr.add("fastjson2");
        liststr.add("fastjson3");
        String jsonString = JSON.toJSONString(liststr);
        System.out.println("json字符串:"+jsonString);
        //解析json字符串
        List<String> list2 = JSON.parseObject(jsonString,new TypeReference<List<String>>(){});
        System.out.println(list2.get(1));
    }

    @Test
    public void testParseComplexObject() {
        log.info("开始解析json对象");
        HashMap<String, Object> h2 = new HashMap<String, Object>();
        h2.put("device_type", "zz");
        h2.put("device_id", "33223");
        HashMap<String, Object> h3 = new HashMap<String, Object>();
        h3.put("device_type", "uu");
        h3.put("device_id", "7985");
        List<Map> lm = new ArrayList<Map>();
        lm.add(h2);
        lm.add(h3);
        String jsonStr2 = JSONObject.toJSONString(lm);
        System.out.println(jsonStr2);

        List<Map> parseList = JSONObject.parseObject(jsonStr2, new TypeReference<List<Map>>() {
        });
//        JSONArray jsonArr = jsonObject.getJSONArray("picType");
        System.out.println(parseList.get(0).get("device_id"));
        log.info(parseList.get(1).get("device_type"));
    }

}
