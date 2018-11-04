import org.apache.commons.dbutils.QueryRunner;

import java.sql.SQLException;

public class DBUtilDemo {
    public static void main(String[] args) {
        QueryRunner qr=new QueryRunner(JdbcUtil2.getDataSources());
        try {
            qr.update("insert into student values(?,?,?,?,?)",new Object[]{2,"ROY","ÄÐ",66.66,"2018-9-9"});
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
