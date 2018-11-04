import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcDemo {


    public static void main(String[] args) {
        Connection connection =null;
        Statement statement=null;
        try {

            /*connection = JdbcUtil.getConnection();*/
            String sql="INSERT into Contact_Infor VALUES (1,'³ÂÁÕ','ÄÐ',18,'»á³¤','269344402@qq.com',13428325591);";
            statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            } finally {
        }
           JdbcUtil.close(statement,connection);
    }
}
