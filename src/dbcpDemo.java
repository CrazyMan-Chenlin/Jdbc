import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

public class dbcpDemo {
    private static String url=null;
    private static String userName=null;
    private static String password=null;
    private static String driverClassName=null;
    private static int maxActive=0;
    private static long maxWait=0;
    private static int initialSize=0;
    public static void main(String[] args) {
        //读取properties文件
        Properties properties=new Properties();
        InputStream in=dbcpDemo.class.getResourceAsStream("db2.properties");
        try {
            properties.load(in);

        //工厂类创建对象
        BasicDataSource bas= (BasicDataSource) BasicDataSourceFactory.createDataSource(properties);
       /* //设置初始连接数
    bas.setInitialSize(5);
        //设置url
        bas.setUrl(url);
        //设置用户名
        bas.setUsername(userName);
        //设置密码
        bas.setPassword(password);
        //设置驱动类名
        bas.setDriverClassName(driverClassName);
        //设置最大连接数
       bas.setMaxActive(maxActive);
        //设置最大等待时间
        bas.setMaxWait(maxWait);*/
            for (int i = 0; i <10 ; i++) {
                Connection conn=bas.getConnection();
                System.out.println(conn);
                if (i==3){
            conn.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
