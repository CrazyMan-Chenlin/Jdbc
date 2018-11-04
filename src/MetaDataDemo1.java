import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

public class MetaDataDemo1 {
    public static void main(String[] args) throws SQLException {
        ComboPooledDataSource cpds=new ComboPooledDataSource();
        Connection conn = cpds.getConnection();
        //ʹ�����ݿ�Ԫ����(DatabaseMetaData)
        DatabaseMetaData metaData = conn.getMetaData();
        //get���ݿ�����
        System.out.println(metaData.getDatabaseProductName());
        //get���ݿ�汾
        System.out.println(metaData.getDatabaseMajorVersion());
        System.out.println(metaData.getDatabaseMinorVersion());
        //get�����汾
        System.out.println(metaData.getDriverMajorVersion());
        System.out.println(metaData.getDatabaseMinorVersion());

    }
}
