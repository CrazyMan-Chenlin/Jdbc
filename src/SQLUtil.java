import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.beanutils.BeanUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLUtil {
    /*通用的更新方法
     *参数元数据（ParameterMeatData）
     * */
    public static void update(String sql, Object[] values) {
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        try {
            Connection conn = cpds.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ParameterMetaData pmd = pstmt.getParameterMetaData();
            //得到sql中的参数
            int count = pmd.getParameterCount();
            //赋值
            for (int i = 1; i <= count; i++) {
                pstmt.setObject(i, values[i - 1]);
            }
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
         //<T>用来表示泛型，意味着List可以add任意类型， 最前面的<T>用来标识，让之后可以使用<T>不报错
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
            //得到结果集的元数据
            ResultSetMetaData metaData = rs.getMetaData();
            //得到列数量
            int columnCount = metaData.getColumnCount();
            List list = new ArrayList();

            while (rs.next()) {
                //构造javabean对象
                Object obj = clazz.getDeclaredConstructor().newInstance();
                //遍历每列
                for (int i = 1; i <= columnCount; i++) {
                    Object value = rs.getObject(i);
                    //得到列名称
                    String columnName = metaData.getColumnName(i);
                    //封装到对象中
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

