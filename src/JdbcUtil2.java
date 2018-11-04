import com.mchange.v2.c3p0.ComboPooledDataSource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JdbcUtil2 {
    //�������ӳض���
    private static DataSource cpds=new ComboPooledDataSource();
    //����DataSource����
    public static DataSource  getDataSources(){
        return cpds;
    }
    //����connect����
    public static Connection getConnection(){
        try {
            return cpds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
