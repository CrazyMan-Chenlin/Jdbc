import java.io.*;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BigDataDemo2 {

    public static void main(String[] args) {
        FileWrite();

    }

    private static void FileRead() {
        //Blob 64k
        //mediumBlob  16M
        //longBlob  4G
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            /*conn = JdbcUtil.getConnection();*/
            String sql="UPDATE news SET Attachments=? WHERE ID =?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(2,1);
            //Data too long for column 'Attachments' at row 1 数据过长
          /*
          *  You can change this value on the server by setting the max_allowed_packet' variable.
          *  mysql默认最大上传文件1M以下 可在my.ini下修改
          * */
            FileInputStream fis=new FileInputStream("C:/USERS/CHENLIN/DESKTOP/GRANDMOTHER.MP3");
            pstmt.setBlob(1,fis);
            pstmt.executeUpdate();
            System.out.println("插入成功");
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.close(pstmt,conn);
        }
    }
    private static void FileWrite() {
        //Blob 64k
        //mediumBlob  16M
        //longBlob  4G
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
           /* conn = JdbcUtil.getConnection();*/
            String sql="SELECT * FROM news WHERE id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,1);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                Blob attachments = rs.getBlob("attachments");
                InputStream is = attachments.getBinaryStream();
                FileOutputStream fos=new FileOutputStream("C:/USERS/CHENLIN/DESKTOP/GRANDMOTHER.MP3");
                byte[] buf=new byte[1024];
                int len=0;
                while((len=is.read(buf))!=-1){
                    fos.write(buf,0,len);
                }
                fos.close();
                is.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.close(pstmt,conn);
        }
    }
}
