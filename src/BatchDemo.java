import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class BatchDemo {
    //��statemet
     /*public static void testByStatement(){
         Connection conn=null;
         Statement stmt=null;
         try {
             conn=JdbcUtil.getConnection();
             stmt=conn.createStatement();
             for (int i = 0; i <2000 ; i++) {
                 stmt.execute("INSERT  into student VALUES ("+i+",'����','��',20)");
             }

         } catch (SQLException e) {
             e.printStackTrace();
         }finally {
             JdbcUtil.close(stmt,conn);
         }*/
         //������batch
         /*public static void testByBatchStatement(){
             Connection conn=null;
             Statement stmt=null;
             try {
                 conn=JdbcUtil.getConnection();
                 stmt=conn.createStatement();
                 for (int i = 0; i <2000 ; i++) {
                   //��ӵ�������
                     stmt.addBatch("INSERT  into student VALUES ("+i+",'����','��',20)");
                     if(i%20==0){
                         //�ύ������
                         stmt.executeBatch();
                         //��ջ�����
                         stmt.clearBatch();
                     }
                 }

             } catch (SQLException e) {
                 e.printStackTrace();
             }finally {
                 JdbcUtil.close(stmt,conn);
             }
     }*/
         //��preparestatement
    /*public static void testByPreStatement(){
        Connection conn=null;
        PreparedStatement pstmt=null;
        try {
            conn=JdbcUtil.getConnection();
            pstmt=conn.prepareStatement("INSERT  into student VALUES (?,?,?,?)");
            for (int i = 0; i <2000 ; i++) {
               pstmt.setInt(1,i);
               pstmt.setString(2,"����");
               pstmt.setString(3,"��");
               pstmt.setInt(4,20);
                pstmt.executeUpdate();
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtil.close(pstmt,conn);
        }
    }*/
    public static void testByBtachPreStatement(){
        Connection conn=null;
        PreparedStatement pstmt=null;
        try {
           /* conn=JdbcUtil.getConnection();*/
            pstmt=conn.prepareStatement("INSERT  into student VALUES (?,?,?,?)");
            for (int i = 0; i <2000 ; i++) {
                //��ӵ�������
               pstmt.setInt(1,i);
               pstmt.setString(2,"����");
               pstmt.setString(3,"��");
               pstmt.setInt(4,20);
               //��ӻ�����
               pstmt.addBatch();
               if (i%20==0){
                   //�ύ������
                   pstmt.executeBatch();
                   //�رջ�����
                   pstmt.clearBatch();
               }
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtil.close(pstmt,conn);
        }
    }
        //����ʱ��
     public static void testTime(){
         long startTime=System.currentTimeMillis();
         testByBtachPreStatement();
         long endTimes=System.currentTimeMillis();
         System.out.println(endTimes-startTime+"ms");
     }
    public static void main(String[] args) {
           testTime();
    }
}
// ���ۣ� mysqlû�л�����ƣ�prepareStatement�Ż� û��Ч��