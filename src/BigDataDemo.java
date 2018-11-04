import com.mysql.jdbc.Statement;

import java.io.*;
import java.sql.*;

public class BigDataDemo {

    public static void main(String[] args) {
       WriteFile();
    }

    private static void ReadFile() {
        //text
        //longtext 4G
        Connection conn = null;
        PreparedStatement pstmt = null;
          MyPool myPool=null;
        try {
            conn = JdbcUtil.getConnection();
            String sql="INSERT  into  news  VALUES (?,?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,1);
            pstmt.setString(2,"JDBC连接各大数据库的秘密！！！");
            //设置longtext文件
            String path = BigDataDemo.class.getResource("url.txt").getPath();
            FileReader reader=new FileReader(path);
            pstmt.setClob(3,reader);
            pstmt.executeUpdate();
            System.out.println("插入成功");
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.close(pstmt,conn);
        }
    }
    private static void WriteFile() {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            /*conn = JdbcUtil.getConnection();*/
            String sql="SELECT * from  news  where id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,1);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                //读取longtext内容
                //1.当字符串来读取
                /*String content=rs.getString("content");
                System.out.println(content);*/
                //2.当输出流来读取
                FileWriter fw=new FileWriter("C:/users/chenlin/desktop/url.txt");
                /*
                *  关键是getClob来获取内容
                *  getBlob是用来获取字节
                * */
                Clob content = rs.getClob("content");
                Reader cs = content.getCharacterStream();
                char[] chars=new char[1024];
                int len=0;
                while((len=cs.read(chars))!=-1){
                   fw.write(chars,0,len);
                }
                fw.close();
                cs.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.close(pstmt,conn);
        }
    }
}
