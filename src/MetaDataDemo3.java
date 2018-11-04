import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.*;
import java.util.List;

public class MetaDataDemo3 {
    public static void main(String[] args) {
        //test();
        List<Student> s1 = SQLUtil.query("select * from student where id=?", new Object[]{1}, Student.class);
        System.out.println(s1.toString());
    }

    private static void test() {
        ComboPooledDataSource cpds=new ComboPooledDataSource();
        try {
            Connection conn = cpds.getConnection();
            String sql ="select * from student";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ParameterMetaData pmd = pstmt.getParameterMetaData();
            int parameterCount = pmd.getParameterCount();
            Object[] values=null;
            if (values!=null){
                for (int i = 1; i <=parameterCount ; i++) {
                     pstmt.setObject(i,values[i-1]);
                }
            }
            ResultSet rs = pstmt.executeQuery();
            //�õ��������Ԫ����
            ResultSetMetaData metaData = rs.getMetaData();
            //�õ�������
            int columnCount = metaData.getColumnCount();
            while (rs.next()){
                //����ÿ��
                for (int i = 1; i <=columnCount ; i++) {
                    Object  value=rs.getObject(i);
                    System.out.print(value+"\t");

                }
                System.out.println();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

