package util.huTool;

import cn.hutool.http.HttpUtil;
import cn.hutool.script.ScriptUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

/**
 * Title:  Hutool是项目中“util”包友好的替代，它节省了开发人员对项目中公用类和公用工具方法的封装时间，使开发专注于业务，同时可以最大限度的避免封装不完善带来的bug。 <br>
 * Description: <br>
 *     模块	介绍
 * hutool-aop	JDK动态代理封装，提供非IOC下的切面支持
 * hutool-bloomFilter	布隆过滤，提供一些Hash算法的布隆过滤
 * hutool-cache	简单缓存实现
 * hutool-core	核心，包括Bean操作、日期、各种Util等
 * hutool-cron	定时任务模块，提供类Crontab表达式的定时任务
 * hutool-crypto	加密解密模块，提供对称、非对称和摘要算法封装
 * hutool-db	JDBC封装后的数据操作，基于ActiveRecord思想
 * hutool-dfa	基于DFA模型的多关键字查找
 * hutool-extra	扩展模块，对第三方封装（模板引擎、邮件、Servlet、二维码、Emoji、FTP、分词等）
 * hutool-http	基于HttpUrlConnection的Http客户端封装
 * hutool-log	自动识别日志实现的日志门面
 * hutool-script	脚本执行封装，例如Javascript
 * hutool-setting	功能更强大的Setting配置文件和Properties封装
 * hutool-system	系统参数调用封装（JVM信息等）
 * hutool-json	JSON实现
 * hutool-captcha	图片验证码实现
 * hutool-poi	针对POI中Excel的封装
 * hutool-socket	基于Java的NIO和AIO的Socket封装
 * Date: 2020/3/10 10:09<br>
 * Copyright (c)   <br>
 *
 * @author lvm
 */

public class TestCase {
    private static Logger log = LogManager.getLogger(TestCase.class);

    /**
     * ScriptUtil.eval 执行Javascript脚本，参数为脚本字符串
     */
    @Test
    public void  testScriptUtil(){
        ScriptUtil.eval("print('Script 测试!');");
    }

    @Test
    public void http() {
        //GET请求
        String content = HttpUtil.get("http://www.weather.com.cn/data/sk/101230103.html");
        log.info(content);
    }

}
