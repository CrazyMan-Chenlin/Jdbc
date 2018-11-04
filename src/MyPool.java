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
    // 储存连接对象的容器
      private static  LinkedList<Connection> pool=new LinkedList<>();
    static {
        try {
            //创建properties对象
            Properties properties=new Properties();
            //构造输入流
            String path=JdbcUtil.class.getResource("db").getPath();
            InputStream in=new FileInputStream(path);
            //加载文件
            properties.load(in);
            //读取文件内容
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
    //连接池初始化连接数
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
            //1)使用静态代理类的方式去创建Connection的代理类
         /*  myConn=new MyConnection(this,conn);*/
            //2)使用动态代理类的方式去创建Connection的代理类
            /**
             *  使用到jdk的api：  Proxy类
             *  		用于创建动态代理类对象：
             *  		static Object newProxyInstance(
             *  						ClassLoader loader,
             *   						Class<?>[] interfaces,
             *   						InvocationHandler h
             *   						)
             * 			参数一：类加载器。
             * 			参数二： 代理类实现的接口列表
             * 			参数三： 接口 InvocationHandler： 代理类的调用处理程序的接口。（代理完代理对象之后，对其中的方法如何处理？？？）
             * 					Object invoke(
             * 						Object proxy,  代理类对象
             * 						Method method,  代理类对象调用的方法。
             * 						Object[] args  调用代理类对象方法时传入的参数列表
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
                       //2)调用回原来的方法，获取返回值
                       Object value = method.invoke(conn, args);//反射中的方法
                       // 返回结果
                       return value;
                   }
               }
           });
            return myConn;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("获取连接失败");
        }

    }
     //提供一个对外连接的方法
     public Connection getConnection() {
         //当并发小于或等于并发数时，才从池中取出
         if (pool.size() > 0) {
             return pool.removeFirst();
             }
             //当并发数超过初始化值时，程序自动连接
             if (currentCount<maxCount){
                  currentCount++;
                 return  JdbcUtil.getConnection();
             }
             throw new RuntimeException("超过最大连接数");
     }
     //提供释放连接对象的方法
       public void releaseConnnection(Connection conn){
        pool.addLast(conn);
       }
}
