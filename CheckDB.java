import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class CheckDB {
    public static void main(String[] args) {
        String url = "jdbc:mysql://ablazing.mysql.polardb.rds.aliyuncs.com:3306/ablazinghome?useSSL=false&serverTimezone=Asia/Shanghai";
        String user = "ablazing";
        String password = "Ablazing1";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement()) {
            
            System.out.println("Tables in ablazinghome:");
            ResultSet rs = stmt.executeQuery("SHOW TABLES;");
            while (rs.next()) {
                System.out.println(rs.getString(1));
            }
            rs.close();

            System.out.println("\nTable structure for banners:");
            rs = stmt.executeQuery("DESC banners;");
            while (rs.next()) {
                System.out.println(rs.getString(1) + " | " + rs.getString(2));
            }
            rs.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}