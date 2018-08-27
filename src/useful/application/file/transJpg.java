/**
 * 从数据库转存图片到本地
 */
package useful.application.file;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

/**
 * @author Administrator
 *
 */
public class transJpg {
    public static void main(String[] args) throws Exception {
        Connection conn = null;
        String sql;
        ArrayList<String> photoList = new ArrayList<String>();
        ArrayList<String> loginIdList = new ArrayList<String>();
        // MySQL的JDBC URL编写方式：jdbc:mysql://主机名称：连接端口/数据库的名称?参数=值
        // 避免中文乱码要指定useUnicode和characterEncoding
        // 执行数据库操作之前要在数据库管理系统上创建一个数据库，名字自己定，
        // 下面语句之前就要先创建javademo数据库
        String url = "jdbc:mysql://localhost:3306/ezon?"
                + "user=root&password=654321&useUnicode=true&characterEncoding=UTF8";
        String url2 = "jdbc:mysql://localhost:3306/ezon?user=root&password=_cGjcN9!@HFIf5K6&useUnicode=true&characterEncoding=UTF8";
        String url3 = "jdbc:mysql://121.40.218.161:3306/ezon?user=root&password=_cGjcN9!@HFIf5K6&useUnicode=true&characterEncoding=UTF8";
        try {
            // 之所以要使用下面这条语句，是因为要使用MySQL的驱动，所以我们要把它驱动起来，
            // 可以通过Class.forName把它加载进去，也可以通过初始化来驱动起来，下面三种形式都可以
            Class.forName("com.mysql.jdbc.Driver");// 动态加载mysql驱动
            // or:
            // com.mysql.jdbc.Driver driver = new com.mysql.jdbc.Driver();
            // or：
            // new com.mysql.jdbc.Driver();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            
            System.out.println(df.format(new Date()) + "成功加载MySQL驱动程序");
            // 一个Connection代表一个数据库连接
            conn = DriverManager.getConnection(url3);
            // Statement里面带有很多方法，比如executeUpdate可以实现插入，更新和删除等
            Statement stmt = conn.createStatement();
            sql = "SELECT u.photo,u.loginId FROM user u where length(photo)>0 limit 5500,500";
            stmt.executeQuery(sql);// executeUpdate语句会返回一个受影响的行数，如果返回-1就没有成功

            ResultSet rs = stmt.executeQuery(sql);// executeQuery会返回结果的集合，否则返回空值
            while (rs.next()) {
            	if (StringUtils.isNotBlank(rs.getString(1)) && StringUtils.isNotBlank(rs.getString(2))){
            		photoList.add(rs.getString(1));
                	loginIdList.add(rs.getString(2));
            	}
            	
            }
            rs.close();
            stmt.close();
            StringBuffer path = new StringBuffer();
            final String filePath = "E://backup//userIcon//";
            //final String filePath = "/var/www/pic/userIcon/";
            final String houz = ".jpg";
            String imgFilePath = null;
            OutputStream out;
            
            
            System.out.println(df.format(new Date()) + "开始生成图片：");// new Date()为获取当前系统时间

            //生成图片到文件夹
            for (int i = 0 ;i < loginIdList.size(); i++){
            	path.setLength(0);
            	
            	byte[] c = Base64.decodeBase64(photoList.get(i));
                imgFilePath = path.append(filePath).append(loginIdList.get(i)).append(houz).toString();
        		try {
        			out = new FileOutputStream(imgFilePath);
        			out.write(c);  
        	        out.flush();  
        	        out.close(); 
        		} catch (FileNotFoundException e) {
        			e.printStackTrace();
        		} catch (IOException e) {
        			e.printStackTrace();
        		}  
            }
                
            
            System.out.println(df.format(new Date()) +"生成了" + photoList.size() + "张图片");
        } catch (SQLException e) {
            System.out.println("MySQL操作错误");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
 
    }
 
}
