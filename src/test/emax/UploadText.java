/**
 *
 */
package test.emax;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.HttpUtil;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

/**
 * @author Administrator
 */
public class UploadText {
    private static Logger logger = LogManager.getLogger();

    static final String SERVICE_EQ = "service=";
    static final String SID_EQ = "&sid=";
    static final String JSON_EQ = "&json=";
    static final String SERVICE_GET_SID = SERVICE_EQ + "common.getSid";
    static final String SERVICE_LOGIN = SERVICE_EQ + "user.login";

    static final String User_LoginId_7 = "17090090954";
    static final String User_Id_7 = "c8dfb741-e6b8-41cc-950a-57f23470cf39";
    static final String User_Password_7 = "e10adc3949ba59abbe56e057f20f883e";

    static String getSid() throws Exception {
        StringBuilder url = new StringBuilder("http://localhost:8080/phone?");
        url.append(SERVICE_GET_SID);
        String result = HttpUtil.httpGet(url.toString());

        JSONObject jsonObject = JSONObject.parseObject(result);
        String sid = (String) jsonObject.get("data");
        String sidDes = DecodeUtils.decodeDES(sid);

        StringBuilder url2 = new StringBuilder("http://localhost:8080/phone?");
        url2.append(SERVICE_LOGIN).append(SID_EQ);
        url2.append(sidDes);

        HashMap<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("loginId", User_LoginId_7);
        paraMap.put("password", User_Password_7);

        String paraStr = JSON.toJSONString(paraMap);
        url2.append(JSON_EQ + URLEncoder.encode(paraStr, "UTF-8"));
        String result2 = HttpUtil.httpGet(url2.toString());
        logger.info(result2);
        return sidDes;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        /*String str = "http://localhost:8080/phone?service=user.uploadPic&sid=" + "0d93b165-62bb-4699-9270-873b180cdc62" +
                "&json={\"picType\":\"data\",\"serverId\":35,\"watchId\":179,\"relType\":\"step\",\"ifShare\":1}";*/
        try {
            String str = "http://localhost:8080/phone?service=user.saveLog2Server&sid=" + getSid();
            String filePath = "D:\\下载文件夹\\新建文本文档.txt";
            logger.info(str);

            URL url = new URL(str);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("content-type", "text/plain");
            connection.setRequestProperty("Charsert", "UTF-8");

            DataOutputStream out = new DataOutputStream(
                    connection.getOutputStream());
            List<String> list = IOUtils.readLines(new BufferedInputStream(new FileInputStream(filePath)),"utf-8");
            for (String temp:list) {
                out.writeUTF(temp);
            }
            out.flush();
            // 读取URLConnection的响应
            String result = IOUtils.toString(connection.getInputStream(), "UTF-8");
            logger.info(result);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {

        }

    }

}