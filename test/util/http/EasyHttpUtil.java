package util.http;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import util.SSLClient;

import java.io.*;
import java.util.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * http工具类
 * Created by lvm on 2015/11/27.
 */
public class EasyHttpUtil {
    private static Logger log = LogManager.getLogger(EasyHttpUtil.class);

    /**
     * utf-8编码
     */
    static class Utf8ResponseHandler implements ResponseHandler<String> {
        public String handleResponse(final HttpResponse response)
                throws HttpResponseException, IOException {
            final StatusLine statusLine = response.getStatusLine();
            final HttpEntity entity = response.getEntity();
            if (statusLine.getStatusCode() >= 300) {
                EntityUtils.consume(entity);
                throw new HttpResponseException(statusLine.getStatusCode(),
                        statusLine.getReasonPhrase());
            }
            return entity == null ? null : EntityUtils
                    .toString(entity, "UTF-8");
        }

    }

    /**
     * 发出http请求
     * @param url
     * @return
     */
    public static String httpGet(String url){
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet(url);
            HttpResponse response;

            // Create a custom response handler
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
                @Override
                public String handleResponse(final HttpResponse response)
                        throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity)
                                : null;
                    } else {
                        throw new ClientProtocolException(
                                "Unexpected response status: " + status);
                    }
                }

            };
            String responseBody;
            responseBody = httpclient.execute(httpget, responseHandler);
            return responseBody;

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }



    public static void httpPost() throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String url = "http://localhost:8080/device";
//        String url = "http://121.40.218.161/wechat";
//        url = url.replace("ATOKEN","Wmh4OcP7fhrzwn-LzXw-dKpNnC-4x82B6FXWVD__mcRbE1IsvdWn-C9F2mNGAr21EiTrsq8XiHYUFUosmYH13wQqq8RnUJQadSfn0kVCrYSW5Q40UIAJ0kHBPFb2GPkzDPRbACALXZ");
        HttpPost httppost = new HttpPost(url);

        HashMap<String, Object> paraMap = new HashMap<String, Object>();
        ArrayList<String> device_id_list = new ArrayList<String>();
        device_id_list.add("EZON_S1_FFFFEC");

        paraMap.put("device_num", "1");
        paraMap.put("device_id_list", device_id_list);

        StringEntity myEntity = new StringEntity(JSONObject.toJSONString(paraMap), "UTF-8");
        httppost.addHeader("Content-Type", "text/json");
        httppost.setEntity(myEntity);
        HttpResponse response = httpclient.execute(httppost);
        HttpEntity resEntity = response.getEntity();
        InputStreamReader reader = new InputStreamReader(resEntity.getContent(), "UTF-8");
        char[] buff = new char[1024];
        int length = 0;
        while ((length = reader.read(buff)) != -1) {
            log.info(new String(buff, 0, length));
        }
        httpclient.close();
    }

    /*
     * 利用HttpClient进行post请求的工具类
     */
    public String doPostWithMap(String url, Map<String,String> map, String charset){
        HttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try{
            httpClient = new SSLClient();
            httpPost = new HttpPost(url);
//            httpPost.setHeader("Content-Type","application/json");
            //设置参数
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            Iterator iterator = map.entrySet().iterator();
            while(iterator.hasNext()){
                Map.Entry<String,String> elem = (Map.Entry<String, String>) iterator.next();
                list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));
            }
            if(list.size() > 0){
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,charset);
                httpPost.setEntity(entity);
            }
            HttpResponse response = httpClient.execute(httpPost);
            if(response != null){
                HttpEntity resEntity = response.getEntity();
                if(resEntity != null){
                    result = EntityUtils.toString(resEntity,charset);
                }
            }
        }catch(Exception ex){
            log.error(ex);
        }
        return result;
    }

    //gzip压缩的post提交
    public static void httpGzipPost(String url, String params) throws IOException{
        CloseableHttpClient http = HttpClients.createDefault();
        HttpPost httpost = new HttpPost(url);
        log.info("url=" + url);
        //添加头信息告诉服务端可以对Response进行GZip压缩
        httpost.setHeader("Content-Encoding", "gzip");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        GZIPOutputStream gzipOut = new GZIPOutputStream(baos,params.length());
        gzipOut.write(params.getBytes());
        gzipOut.flush();
        gzipOut.finish();
        gzipOut.close();

        httpost.setEntity(new ByteArrayEntity(baos.toByteArray()));

        try {
            HttpResponse response = http.execute(httpost);
            InputStream is= response.getEntity().getContent();
            is= new GZIPInputStream(is);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            StringBuffer sb = new StringBuffer("\n");
            while((line = br.readLine())!=null) {
                sb.append(line).append("\n");
            }
            log.info(sb.toString());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            http.close();
        }
    }

    /**
     * post提交
     * @param url
     * @param body
     * @return
     */
    public static String executePost(String url, String body){
        try {
            HttpPost httpPost = new HttpPost(url);
            StringEntity entity = new StringEntity(body, "UTF-8");
            httpPost.setEntity(entity);
            HttpClient httpclient = HttpClients.createDefault();
            HttpResponse response = httpclient.execute(httpPost);
            String resultContent = new Utf8ResponseHandler()
                    .handleResponse(response);
            log.info("result=" + resultContent);
            return resultContent;
        } catch (Exception e) {
            log.error("post提交出错",e);
            throw new RuntimeException(e);
        }
    }



    //模拟带文件表单post提交
    public static String postMultipartEntity(String url,File filePath) throws IOException {
        // 开启一个客户端 HTTP 请求
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);//创建 HTTP POST 请求
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addTextBody("param1","中国");
        builder.addBinaryBody("files",filePath);
        builder.addTextBody("method","uploadFile");
        HttpEntity entity = builder.build();// 生成 HTTP POST 实体
        post.setEntity(entity);

        HttpResponse response = httpclient.execute(post);// 发起请求 并返回请求的响应
        int status = response.getStatusLine().getStatusCode();
        if (status >= 200 && status < 300) {
            HttpEntity responseEntity = response.getEntity();
            return responseEntity != null ? EntityUtils.toString(responseEntity)
                    : null;
        } else {
            throw new ClientProtocolException(
                    "Unexpected response status: " + status);
        }
    }

    public static void main(String[] args) {
        try {
            String result = EasyHttpUtil.postMultipartEntity("http://localhost:15080/test/receive",
                    new File("D:\\local_data\\log\\chain.log"));
//            String result = HttpUtil.postMultipartEntity("http://172.21.105.203:15080/test/receive",
//                    new File("D:\\local_data\\log\\chain.log"));
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 动态修改springboot日志级别
     */
    @Test
    public void changeLogLevel(){
        String url = "http://localhost:15080/loggers/com.ruijie.ReceiveController";
//      日志等级有"OFF","ERROR","WARN","INFO","DEBUG","TRACE"

        String body = "{\"configuredLevel\": \"error\"}";
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type","application/json");
        StringEntity entity = new StringEntity(body, "UTF-8");
        httpPost.setEntity(entity);
        HttpClient httpclient = HttpClients.createDefault();
        HttpResponse response = null;
        try {
            response = httpclient.execute(httpPost);
            String resultContent = new Utf8ResponseHandler()
                    .handleResponse(response);
            log.info("result=" + resultContent);
        } catch (IOException e) {
            log.error("出错了",e);
        }
    }
}
