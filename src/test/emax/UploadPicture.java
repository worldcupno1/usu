/**
 * 
 */
package test.emax;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Administrator
 *
 */
public class UploadPicture {
	private static Logger logger = LogManager.getLogger();
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*String str = "http://localhost:8080/phone?service=user.uploadPic&sid=" + "0d93b165-62bb-4699-9270-873b180cdc62" +
                "&json={\"picType\":\"data\",\"serverId\":35,\"watchId\":179,\"relType\":\"step\",\"ifShare\":1}";*/
        String str = "http://localhost:8080/phone?service=user.shareWithPic&sid=" + "82b47d07-b8a9-4258-8f01-0f5a896ee518" +
                "&json={\"userId\":\"a3e78e23-93d0-4dad-970c-28607e8562e2\",\"remark\":\"never be a click2\"}";
		String filePath = "E:\\backup\\share\\t12.jpg";

        logger.info(str);
        try {
		String picType = "data";
			URL url = new URL(str);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			//connection.addRequestProperty("picType", picType);
			connection.setRequestProperty("content-type", "text/html");
			BufferedOutputStream out = new BufferedOutputStream(
					connection.getOutputStream());

			// 读取文件上传到服务器
			File file = new File(filePath);
			FileInputStream fileInputStream = new FileInputStream(file);
			byte[] bytes = new byte[1024];
			int numReadByte = 0;
			while ((numReadByte = fileInputStream.read(bytes, 0, 1024)) > 0) {
				out.write(bytes, 0, numReadByte);
			}

			out.flush();
            fileInputStream.close();
            // 读取URLConnection的响应
            BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());
            DataInputStream in = new DataInputStream(bis);
            String result = null;
            StringBuilder builder = new StringBuilder();
            while ((result = in.readLine()) != null) {
                builder.append(result.trim());
            }

            logger.info(builder);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}