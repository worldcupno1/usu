package io;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by lvm on 2016/7/12.
 */
public class TestCase {
    public static Logger log = LogManager.getLogger(TestCase.class);

    //获取url下载的文件名
    @Test
    public void getFileName() {
        try {
            URL url = new URL("http://offline-live1.services.u-blox.com/GetOfflineData.ashx?token=a5_iKY1Fmk64od8ZA7t56w;gnss=gps;format=aid;days=1");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.connect();
            String field = http.getHeaderField("Content-Disposition");
            //获取filename="xxx"中的xxx
            String fileName = field.substring(field.indexOf("filename") + 9, field.length());
            log.info("文件名为" + fileName);
            File file = new File("e:/" + fileName);
            FileUtils.copyInputStreamToFile(url.openStream(),file);
            //记录
            JSONObject j = new JSONObject();
            j.put("downloadTime",System.currentTimeMillis());
            j.put("fileName",fileName);
            file = new File("e:/record.json");
            FileUtils.writeStringToFile(file,j.toString(),"utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //StringBuilderWriter读取inputStream,按字符读取
    @Test
    public void IOUtils() throws IOException {
        InputStream in = new URL( "http://commons.apache.org" ).openStream();
        try {
            System.out.println( IOUtils.toString( in,"utf-8" ) );
        } finally {
            IOUtils.closeQuietly(in);
        }
    }

    //readLine简便用法
    @Test
    public void FileUtilsExample() throws IOException {
        File file = new File("E:/backup/log/emax-error.log");
        List lines = FileUtils.readLines(file, "UTF-8");
        log.info(lines);
    }

    //去掉..
    @Test
    public void FilenameUtilsExample(){
        String filename = "C:/commons/io/../lang/project.xml";
        String normalized = FilenameUtils.normalize(filename);
        log.info(normalized);
    }

    //计算还有空间，单位Kb
    @Test
    public void FileSystemUtilsExample() throws IOException {
        long freeSpace = FileSystemUtils.freeSpaceKb("C:/");
        log.info("空间还有" + freeSpace);
    }

    //行迭代器
    @Test
    public void lineIteratorExample() throws IOException {
        File file = new File("E:/backup/log/emax-error.log");
        LineIterator it = FileUtils.lineIterator(file, "UTF-8");
        try {
            while (it.hasNext()) {
                String line = it.nextLine();
                /// do something with line
                log.info(line);
            }
        } finally {
            LineIterator.closeQuietly(it);
        }
    }
}
