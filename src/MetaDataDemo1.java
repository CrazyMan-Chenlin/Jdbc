import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

public class MetaDataDemo1 {
    public static void main(String[] args) throws SQLException {
        ComboPooledDataSource cpds=new ComboPooledDataSource();
        Connection conn = cpds.getConnection();
        //使用数据库元数据(DatabaseMetaData)
        DatabaseMetaData metaData = conn.getMetaData();
        //get数据库名称
        System.out.println(metaData.getDatabaseProductName());
        //get数据库版本
        System.out.println(metaData.getDatabaseMajorVersion());
        System.out.println(metaData.getDatabaseMinorVersion());
        //get驱动版本
        System.out.println(metaData.getDriverMajorVersion());
        System.out.println(metaData.getDatabaseMinorVersion());

    }
}
