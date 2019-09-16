package api.http.fjims;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import util.http.HttpUtil;

import java.io.IOException;
import java.util.HashMap;

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
     * 登录测试
     */
    @Test
    public void login() throws IOException {
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
}
