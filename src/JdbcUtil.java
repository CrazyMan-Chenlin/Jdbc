import java.io.*;
import java.sql.*;
import java.util.Properties;

public class JdbcUtil {
    private  static String JdbcDriver=null;
    private  static String DbUrl=null;
    private  static String user=null;
    private  static String password=null;
    static {
        try {
            //����properties����
            Properties properties=new Properties();
            //����������
             String path=JdbcUtil.class.getResource("db").getPath();
            InputStream in=new FileInputStream(path);
            //�����ļ�
            properties.load(in);
            //��ȡ�ļ�����
            JdbcDriver=properties.getProperty("JdbcDriver");
            DbUrl=properties.getProperty("DbUrl");
            user=properties.getProperty("user");
            password=properties.getProperty("password");
            Class.forName(JdbcDriver);
            System.out.println(JdbcDriver);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new RuntimeException(e);
        }
    }
    public static Connection getConnection(){
        Connection connection=null;
        try {
             connection = DriverManager.getConnection(DbUrl, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
     public static  void  close(Statement stmt,Connection conn){
        if (stmt!=null){
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
     }
     //�������Ӳμӣ�ѡ������
     public static  void  close(ResultSet set,Statement stmt, Connection conn){
            if (set!=null){
                try {
                    set.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        if (stmt!=null){
             try {
                 stmt.close();
             } catch (SQLException e) {
                 e.printStackTrace();
             }
         }
         if (conn!=null){
             try {
                 conn.close();
             } catch (SQLException e) {
                 e.printStackTrace();
             }
         }
     }
}
