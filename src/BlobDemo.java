import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.sql.*;

public class BlobDemo {
    public static void main(String[] args) {
        ReadFile();
    }

    private static void ReadFile() {
        Connection conn=null;
        PreparedStatement pstmt=null;
        try{
        /*conn=JdbcUtil.getConnection();*/
        String sql="INSERT into attachments VALUE (?,?,?,?,?)";

            pstmt=conn.prepareStatement(sql);
            pstmt.setInt(1,2);
            pstmt.setString(2,"七夕节血案频发");
            String path=BlobDemo.class.getResource("url.txt").getPath();
            FileInputStream fis=new FileInputStream(path);
            pstmt.setBlob(3,fis);
            //1.作为字符串插入
            //pstmt.setString(4,"2018-8-17");
            //2 java Date 类型插入 setDate()中的date类型是java.sql.Date 类型 不是java.util的
            pstmt.setDate(4,new java.sql.Date(new java.util.Date().getTime()));
            pstmt.setString(5,"洋溢");
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.close(pstmt,conn);
        }
    }
    private static void WriteFile() {
        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs = null;
        try{
           /* conn=JdbcUtil.getConnection();*/
            String sql="SELECT * FROM attachments WHERE ID=?";
            pstmt=conn.prepareStatement(sql);
            pstmt.setInt(1,1);
             rs = pstmt.executeQuery();
             if (rs.next()) {
                 Blob file = rs.getBlob("file");
                 InputStream is = file.getBinaryStream();
                 FileOutputStream fos = new FileOutputStream("D:url.txt");
                 byte[] buf = new byte[1024];
                 int len = 0;
                 while ((len = is.read(buf)) != -1) {
                     fos.write(buf, 0, len);
                 }
                 fos.close();
                 is.close();
             }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.close(rs,pstmt,conn);
        }
    }
}
