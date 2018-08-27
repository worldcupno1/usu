package example.jdbc;
import java.sql.*;

public class OracleTest {
	public static void main(String[] args) {
		// String serverName = "localhost";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("1");
			String url = "jdbc:oracle:thin:@192.168.57.57:1521:orcl";
			Connection conn = DriverManager
					.getConnection(url, "bbscs", "bbscs");
			System.out.println("2");
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from bbscs_config");
			while (rs.next()) {
				System.out.println(rs.getString("id"));
				System.out.println(rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
