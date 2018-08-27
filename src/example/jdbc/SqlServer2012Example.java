package example.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by unique-lv on 2016/9/25.
 */
public class SqlServer2012Example {
    public static void main(String [] args)
    {
        String driverName="com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String dbURL="jdbc:sqlserver://localhost:1433;DatabaseName=xzfutures";
        String userName="sa";
        String userPwd="root";
        try
        {
            Class.forName(driverName);
            Connection dbConn= DriverManager.getConnection(dbURL,userName,userPwd);
            System.out.println(dbConn.getClientInfo());
            System.out.println("连接数据库成功");
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.print("连接失败");
        }
    }
}
