import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.beanutils.BeanUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLUtil {
    /*ͨ�õĸ��·���
     *����Ԫ���ݣ�ParameterMeatData��
     * */
    public static void update(String sql, Object[] values) {
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        try {
            Connection conn = cpds.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ParameterMetaData pmd = pstmt.getParameterMetaData();
            //�õ�sql�еĲ���
            int count = pmd.getParameterCount();
            //��ֵ
            for (int i = 1; i <= count; i++) {
                pstmt.setObject(i, values[i - 1]);
            }
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
         //<T>������ʾ���ͣ���ζ��List����add�������ͣ� ��ǰ���<T>������ʶ����֮�����ʹ��<T>������
    public static <T> List<T> query(String sql, Object[] values, Class<T> clazz) {
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        try {
            Connection conn = cpds.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ParameterMetaData pmd = pstmt.getParameterMetaData();
            int parameterCount = pmd.getParameterCount();
            if (values != null) {
                for (int i = 1; i <= parameterCount; i++) {
                    pstmt.setObject(i, values[i - 1]);
                }
            }
            ResultSet rs = pstmt.executeQuery();
            //�õ��������Ԫ����
            ResultSetMetaData metaData = rs.getMetaData();
            //�õ�������
            int columnCount = metaData.getColumnCount();
            List list = new ArrayList();

            while (rs.next()) {
                //����javabean����
                Object obj = clazz.getDeclaredConstructor().newInstance();
                //����ÿ��
                for (int i = 1; i <= columnCount; i++) {
                    Object value = rs.getObject(i);
                    //�õ�������
                    String columnName = metaData.getColumnName(i);
                    //��װ��������
                    BeanUtils.copyProperty(obj, columnName, value);
                }
                list.add(obj);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}

