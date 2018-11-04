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
        //��ȡproperties�ļ�
        Properties properties=new Properties();
        InputStream in=dbcpDemo.class.getResourceAsStream("db2.properties");
        try {
            properties.load(in);

        //�����ഴ������
        BasicDataSource bas= (BasicDataSource) BasicDataSourceFactory.createDataSource(properties);
       /* //���ó�ʼ������
    bas.setInitialSize(5);
        //����url
        bas.setUrl(url);
        //�����û���
        bas.setUsername(userName);
        //��������
        bas.setPassword(password);
        //������������
        bas.setDriverClassName(driverClassName);
        //�������������
       bas.setMaxActive(maxActive);
        //�������ȴ�ʱ��
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
