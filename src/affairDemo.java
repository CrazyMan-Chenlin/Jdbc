import java.sql.*;

public class affairDemo {
    public static void main(String[] args) {
        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try {
          /*  conn=JdbcUtil.getConnection();*/
            conn.setAutoCommit(false);//���ֶ��ύ���񿪹�
            String delSql="UPDATE account set balance=balance-2000 WHERE NAME ='Roy'";
            String addSql="UPDATE account set balance=balance+2000 WHERE NAME ='jack'";
            pstmt=conn.prepareStatement(delSql);
            pstmt.executeUpdate();
            int i=1/0;
            pstmt=conn.prepareStatement(addSql);
            pstmt.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback(); //����ع�
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            JdbcUtil.close(rs,pstmt,conn);
        }

    }
}
