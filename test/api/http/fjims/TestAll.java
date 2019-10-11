package api.http.fjims;

import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import util.http.HttpUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Title:  api测试类 <br>
 * Description: <br>
 * Date: 2019/9/16 16:53<br>
 * Copyright (c)   <br>
 *
 * @author worldcupno1
 */

public class TestAll {
    public static Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    /**
     * 本地头
     */
    static final String LOCAL_HEADER = "http://localhost:28080/glyh/api";

    /**
     * 登录
     */
    static final String LOGIN = "/user/login";
    /**
     * 小修保养列表
     */
    static final String LIST_GLYH_TASK = "/glyh/task/glyhTask";
    /**
     * 小修保养，保存
     */
    static final String SAVE_GLYH_TASK = LIST_GLYH_TASK + "/save";

    /**
     * 巡查数据
     */
    static final String PATROL = "/glyh/patrol/glyhPatrolData";
    static final String SAVE_PATROL = PATROL + "/save";

    /**
     * 登录测试
     */
    @Test
    public void login() {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("user","first");
        params.put("password","c6c57330f983ed4b44c8bba1ee87d1e05574dcd9e391791a2b132770");
        String result = HttpUtil.httpGet(LOCAL_HEADER + LOGIN + "?loginName=first&password=147258");
        log.info(result);
    }

    /**
     * 小修保养列表
     */
    @Test
    public void listGlyhTask(){
        String result = HttpUtil.httpGet(LOCAL_HEADER + LIST_GLYH_TASK);
        log.info(result);
    }

    @Test
    public void saveGlyhTask(){
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("user","first");
        params.put("password","c6c57330f983ed4b44c8bba1ee87d1e05574dcd9e391791a2b132770");
        String result = HttpUtil.doPostWithMap(LOCAL_HEADER + SAVE_GLYH_TASK,params,"UTF-8");
        log.info(result);
    }

    @Test
    public void getGlyhRoadInfo(){
        String result = HttpUtil.httpGet(LOCAL_HEADER + "/glyh/datacard/glyhRoadInfo/get?id=34a44a9e2cd34edeb0d9fc1f1110023d");
        log.info(result);
    }

    @Test
    public void savePatrol(){
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("taskType","1");
        params.put("reportTime","2019-09-23 09:00:00");
        params.put("weather","晴");
        params.put("reporterName","吴某某");
        params.put("problem","无发现");
        params.put("solutions","无");
        params.put("notes","");
        log.info(JSONObject.toJSON(params));
        String result = HttpUtil.doPostWithMap(LOCAL_HEADER + SAVE_PATROL,params,"UTF-8");
        log.info(result);
    }

    @Test
    public void routineList(){
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("pageNo","1");
        params.put("pageSize","30");
        String result = HttpUtil.doPostWithMap(LOCAL_HEADER + "/glyh/routine/glyhRoutine/authc/",params,"UTF-8");
        log.info(result);
    }

    @Test
    public void routineDetailsDeta(){
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("pageNo","1");
        params.put("pageSize","30");
        String result = HttpUtil.doPostWithMap(LOCAL_HEADER + "/glyh/routine/glyhRoutineDetailsDeta",params,"UTF-8");
        log.info(result);
    }
}
