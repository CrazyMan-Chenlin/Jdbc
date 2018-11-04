import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class BatchDemo {
    //纯statemet
     /*public static void testByStatement(){
         Connection conn=null;
         Statement stmt=null;
         try {
             conn=JdbcUtil.getConnection();
             stmt=conn.createStatement();
             for (int i = 0; i <2000 ; i++) {
                 stmt.execute("INSERT  into student VALUES ("+i+",'陈琳','男',20)");
             }

         } catch (SQLException e) {
             e.printStackTrace();
         }finally {
             JdbcUtil.close(stmt,conn);
         }*/
         //批处理batch
         /*public static void testByBatchStatement(){
             Connection conn=null;
             Statement stmt=null;
             try {
                 conn=JdbcUtil.getConnection();
                 stmt=conn.createStatement();
                 for (int i = 0; i <2000 ; i++) {
                   //添加到缓存区
                     stmt.addBatch("INSERT  into student VALUES ("+i+",'陈琳','男',20)");
                     if(i%20==0){
                         //提交缓存区
                         stmt.executeBatch();
                         //清空缓存区
                         stmt.clearBatch();
                     }
                 }

             } catch (SQLException e) {
                 e.printStackTrace();
             }finally {
                 JdbcUtil.close(stmt,conn);
             }
     }*/
         //纯preparestatement
    /*public static void testByPreStatement(){
        Connection conn=null;
        PreparedStatement pstmt=null;
        try {
            conn=JdbcUtil.getConnection();
            pstmt=conn.prepareStatement("INSERT  into student VALUES (?,?,?,?)");
            for (int i = 0; i <2000 ; i++) {
               pstmt.setInt(1,i);
               pstmt.setString(2,"陈琳");
               pstmt.setString(3,"男");
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
                //添加到缓存区
               pstmt.setInt(1,i);
               pstmt.setString(2,"陈琳");
               pstmt.setString(3,"男");
               pstmt.setInt(4,20);
               //添加缓冲区
               pstmt.addBatch();
               if (i%20==0){
                   //提交缓存区
                   pstmt.executeBatch();
                   //关闭缓冲区
                   pstmt.clearBatch();
               }
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtil.close(pstmt,conn);
        }
    }
        //测试时间
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
// 结论： mysql没有缓冲机制，prepareStatement优化 没有效果