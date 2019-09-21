package util.http;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;


/**
 * Title:  http工具类<br>
 * Description: http工具类<br>
 * Date: 2017\11\23 0023 11:10<br>
 * Copyright (c)   <br>
 *
 * @author lvm
 */

public class HttpUtil {
    private static Logger log = LogManager.getLogger(HttpUtil.class);

    private static final int HTTP_STATUS_CODE_200 = 200;
    private static final int HTTP_STATUS_CODE_300 = 300;
    /**
     * 超时时间
     */
    private static final int TIMEOUT = 5000;

    /**
     * utf-8编码
     */
    static class Utf8ResponseHandler implements ResponseHandler<String> {
        @Override
        public String handleResponse(final HttpResponse response)
                throws HttpResponseException, IOException {
            final StatusLine statusLine = response.getStatusLine();
            final HttpEntity entity = response.getEntity();
            if (statusLine.getStatusCode() >= HTTP_STATUS_CODE_300) {
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
                    if (status >= HTTP_STATUS_CODE_200 && status < HTTP_STATUS_CODE_300) {
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


    /**
     * 利用HttpClient进行post请求的工具类
     * @param url
     * @param map 参数map
     * @param charset 编码
     * @return
     */
    public static String doPostWithMap(String url, Map<String,Object> map, String charset){
        CloseableHttpClient httpClient =  HttpClients.createDefault();
        //配置超时时间
        RequestConfig requestConfig = RequestConfig.custom().
                setConnectTimeout(TIMEOUT).setConnectionRequestTimeout(TIMEOUT)
                .setSocketTimeout(TIMEOUT).setRedirectsEnabled(true).build();
        HttpPost httpPost = new HttpPost(url);
        //设置超时时间
        httpPost.setConfig(requestConfig);
        String result = null;
        if (StringUtils.isBlank(charset)){
            charset = "UTF-8";
        }
        try{
            //装配post请求参数
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

    /**
     * 支持gzip压缩的post提交
     * @param url
     * @param params
     * @throws IOException
     */
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
     * 带body的post提交
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


    /**
     * 模拟带文件表单post提交
     * @param url 提交url
     * @param filePath 文件地址
     * @return
     * @throws IOException
     */
    public static String postMultipartEntity(String url,File filePath) throws IOException {
        // 开启一个客户端 HTTP 请求
        CloseableHttpClient httpclient = HttpClients.createDefault();
        //创建 HTTP POST 请求
        HttpPost post = new HttpPost(url);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        // file，类似表单中的name的值，后台通过这个名字取文件
        builder.addBinaryBody("file",new FileInputStream(filePath), ContentType.MULTIPART_FORM_DATA, "fileName");
        builder.addTextBody("param1","中国");
        builder.addTextBody("method","uploadFile");
        // 生成 HTTP POST 实体
        HttpEntity entity = builder.build();
        post.setEntity(entity);
        // 发起请求 并返回请求的响应
        HttpResponse response = httpclient.execute(post);
        int status = response.getStatusLine().getStatusCode();
        if (status >= HTTP_STATUS_CODE_200 && status < HTTP_STATUS_CODE_300) {
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
            String result = HttpUtil.postMultipartEntity("http://localhost:15080/test/receive",
                    new File("D:\\local_data\\log\\chain.log"));
//            String result = HttpUtil.postMultipartEntity("http://172.21.105.203:15080/test/receive",
//                    new File("D:\\local_data\\log\\chain.log"));
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
