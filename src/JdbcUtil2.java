import com.mchange.v2.c3p0.ComboPooledDataSource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JdbcUtil2 {
    //构建连接池对象
    private static DataSource cpds=new ComboPooledDataSource();
    //返回DataSource对象
    public static DataSource  getDataSources(){
        return cpds;
    }
    //返回connect对象
    public static Connection getConnection(){
        try {
            return cpds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
