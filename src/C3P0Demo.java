import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

public class C3P0Demo {
    private static String jdbcUrl="jdbc:mysql://localhost:3306/contact?useSSL=true";
    private static String user="root";
    private static String password="123456";
    private static  String driverClass="com.mysql.jdbc.Driver";
    private static int initialPoolSize=5;
    private static int maxPoolSize=12;
    private static  int minPoolSize=3;
    private static  int checkoutTimeout=1000;

    public static void main(String[] args) throws PropertyVetoException,SQLException {
        ComboPooledDataSource cpds=new ComboPooledDataSource();
        //ComboPooledDataSource cpds=new ComboPooledDataSource("oracle");
        /*
        * oracle����c3p0-config.xml ��ǩΪ<named-config></name-config>�е�name
        * */
       /* cpds.setJdbcUrl(jdbcUrl);
        cpds.setUser(user);
        cpds.setPassword(password);
        cpds.setDriverClass(driverClass);
         cpds.setInitialPoolSize(initialPoolSize);//���ó�ʼ������
         cpds.setMaxPoolSize(maxPoolSize);//���������
        cpds.setCheckoutTimeout(checkoutTimeout);//���õȴ�ʱ��
        cpds.setMinPoolSize(minPoolSize);//������С������*/
        for (int i = 0; i <12 ; i++) {
            Connection connection = cpds.getConnection();
            System.out.println(connection);
        }
    }
}
