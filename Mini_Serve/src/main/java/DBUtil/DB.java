package DBUtil;

import java.sql.DriverManager;
import java.sql.SQLException;
import com.mysql.jdbc.Connection;


public class DB {
    private static String driver;
    private static String url;
    private static String username;
    private static String password;
    static {
        driver="com.mysql.jdbc.Driver";
        url="jdbc:mysql://127.0.0.1/mini";
        username="root";
        password="123456";
    }
    public static Connection open(){
        try {
            Class.forName(driver);
            return (Connection) DriverManager.getConnection(url,username,password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void close(Connection conn){
        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
