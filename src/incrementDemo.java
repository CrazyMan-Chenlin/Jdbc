import java.sql.*;

public class incrementDemo
{
    public static void main(String[] args) {
        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try {
            /*conn=JdbcUtil.getConnection();*/
            String deptSql="insert into dept(name) VALUE (?)";
            //Statement.RETURN_GENERATED_KEYS ������������ֵ
            pstmt=conn.prepareStatement(deptSql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1,"���ά����");
            pstmt.executeUpdate();
            rs=pstmt.getGeneratedKeys();
            int deptId=0;
            while(rs.next()){
               deptId=rs.getInt(1);
            }
            String employeeSql="insert into employee(name,deptId) value (?,?)";
            pstmt=conn.prepareStatement(employeeSql);
            pstmt.setString(1,"����");
            pstmt.setInt(2,deptId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.close(rs,pstmt,conn);
        }


    }
}
