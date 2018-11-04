import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class CallableStatementDemo {
    public static void main(String[] args) {
        Connection connection=null;
        CallableStatement callableStatement=null;
        //1.��������
        try {
           /* connection = JdbcUtil.getConnection();*/
            //2.׼��sql
            String sql="CALL pro_GetName(?,?)";
            //3.Ԥ����
            callableStatement=connection.prepareCall(sql);
            //4.������ֵ
            //�������
            callableStatement.setInt(1,1);
            //�������
            callableStatement.registerOutParameter(2, Types.VARCHAR);
            //5.ִ������sql
            callableStatement.executeUpdate();
            //�������������У�����������鿴���
            //����Ĳ����������������λ��һ��
            String name=callableStatement.getString(2);
            System.out.println(name);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.close(callableStatement,connection);
        }

    }
}
