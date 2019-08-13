package str.json;

import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Date;
import java.util.HashMap;

/**文件和json对象转换
 * Created by lvm on 2016/3/1.
 */
public class File2json {
    static Logger log = LogManager.getLogger(File2json.class);
    //缓冲区长度
    public static final int BUFFER_SIZE = 1024;

    /**
     * @param args
     */
    public static void main(String[] args) {
        FileInputStream input=null;
        BufferedInputStream bis=null;
        BufferedReader reader ;
        BufferedOutputStream bos ;
        FileOutputStream fos;
        StringBuilder str = new StringBuilder();
        JSONObject jsonObj ;

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("username", "zhangsan");
        map.put("age", 24);
        map.put("sex", new Date());

        String json = JSONObject.toJSONString(map);
        log.info(json);

//        File f = new File("c:/");
//        f.mkdirs();
        File f = new File("c:/1.json");

        try {
            f.createNewFile();
            log.info(f.canWrite());
            if (f.canWrite()){
                fos = new FileOutputStream(f);
                bos = new BufferedOutputStream(fos,BUFFER_SIZE);
                bos.write(json.getBytes());
                bos.flush();
            }
            log.info("文件创建成功。");
            //
            String tmp;
            //文件形式存在，以bufferReader来读取，二进制文件以BufferedOutputStream来读取
            if (f.exists()){
//                input = new FileInputStream(f);
//                bis = new BufferedInputStream(input);
                reader = new BufferedReader (new FileReader(f));
                while ((tmp = reader.readLine()) != null) {
                    log.info(tmp);
                    str.append(tmp);
                }
                log.info("文件内容=" + str);
                jsonObj = JSONObject.parseObject(str.toString());
                log.info(jsonObj.getString("sex"));
            }


        } catch (IOException e) {
            log.error("创建文件失败");
            e.printStackTrace();
        }

    }

}
