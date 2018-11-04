import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Properties;

public class MyPool {
    private  static String JdbcDriver=null;
    private  static String DbUrl=null;
    private  static String user=null;
    private  static String password=null;
    // �������Ӷ��������
      private static  LinkedList<Connection> pool=new LinkedList<>();
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
        } catch (Exception e) {
            e.printStackTrace();
            throw  new RuntimeException(e);
        }
    }
    public static LinkedList<Connection> getPool() {
        return pool;
    }
    //���ӳس�ʼ��������
    private int initCount=5;
    private int currentCount=0;
    private int maxCount=10;

    public MyPool(){
        for (int i = 0; i <=initCount ; i++) {
            currentCount++;
            Connection conn = this.createConcection();
            pool.addFirst(conn);
        }
    }
    public Connection createConcection(){
        final Connection conn;
        try {
            conn = DriverManager.getConnection(DbUrl,user,password);
            //1)ʹ�þ�̬������ķ�ʽȥ����Connection�Ĵ�����
         /*  myConn=new MyConnection(this,conn);*/
            //2)ʹ�ö�̬������ķ�ʽȥ����Connection�Ĵ�����
            /**
             *  ʹ�õ�jdk��api��  Proxy��
             *  		���ڴ�����̬���������
             *  		static Object newProxyInstance(
             *  						ClassLoader loader,
             *   						Class<?>[] interfaces,
             *   						InvocationHandler h
             *   						)
             * 			����һ�����������
             * 			�������� ������ʵ�ֵĽӿ��б�
             * 			�������� �ӿ� InvocationHandler�� ������ĵ��ô������Ľӿڡ���������������֮�󣬶����еķ�����δ���������
             * 					Object invoke(
             * 						Object proxy,  ���������
             * 						Method method,  �����������õķ�����
             * 						Object[] args  ���ô�������󷽷�ʱ����Ĳ����б�
             * 					)
             *
             *
             *
             */
            Connection myConn=(Connection) Proxy.newProxyInstance(MyPool.class.getClassLoader(), new Class[]{Connection.class}, new InvocationHandler() {
               @Override
               public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                   String methodName=method.getName();
                   if("close".equals(methodName)){
                       MyPool.getPool().addLast(conn);
                       return null;
                   }else{
                       //2)���û�ԭ���ķ�������ȡ����ֵ
                       Object value = method.invoke(conn, args);//�����еķ���
                       // ���ؽ��
                       return value;
                   }
               }
           });
            return myConn;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("��ȡ����ʧ��");
        }

    }
     //�ṩһ���������ӵķ���
     public Connection getConnection() {
         //������С�ڻ���ڲ�����ʱ���Ŵӳ���ȡ��
         if (pool.size() > 0) {
             return pool.removeFirst();
             }
             //��������������ʼ��ֵʱ�������Զ�����
             if (currentCount<maxCount){
                  currentCount++;
                 return  JdbcUtil.getConnection();
             }
             throw new RuntimeException("�������������");
     }
     //�ṩ�ͷ����Ӷ���ķ���
       public void releaseConnnection(Connection conn){
        pool.addLast(conn);
       }
}
