import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.*;

import java.sql.SQLException;
import java.util.List;

public class DBUtilDemo2 {
    public static void main(String[] args) {
        QueryRunner  qr =new QueryRunner(JdbcUtil2.getDataSources());
        /*
        * ResultSetHandler�ӿ�: ���ڰѽ������װ�ɲ�ͬ���͵Ķ���
        * ArrayHandler��: �ѽ�����ĵ�һ�н����װ�ɶ�������
        * ArrayListHandler�ࣺ �ѽ������ÿһ�н����װ�ɶ������飬��ÿ�������������List������
        * BeanHandler�ࣺ �ѽ�����ĵ�һ�У���װ��javabean����(����)
        * BeanListHandler�ࣺ �ѽ������ÿ�з�װ��javabean����ÿ��javabean����List�����У����ã�
        * ScalarHandler�ࣺ��ѯ�ۺϺ���(����:count(*))  (����)
         * */
        //t1(qr);
        //t2(qr);
        //t3(qr);
        //t4(qr);
        try {
            Long count= (Long) qr.query("select count(*) from student", new ScalarHandler(), new Object[]{});
            System.out.println(count);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void t4(QueryRunner qr) {
        try {
            List<Student> studentList = (List<Student>)qr.query("select * from student", new BeanListHandler(Student.class), new Object[]{});
            for (Student stu: studentList
                 ) {
                System.out.println(stu.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void t3(QueryRunner qr) {
        try {
            Student student =(Student) qr.query("select * from student where id=?", new BeanHandler(Student.class), new Object[]{2});
            System.out.println(student.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void t2(QueryRunner qr) {
        try {
            List<Object[]> objectList =(List<Object[]>) qr.query("select * from student", new ArrayListHandler(), new Object[]{});
            for (Object[] objs: objectList
                 ) {
                for (Object obj: objs
                     ) {
                    System.out.println(obj);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void t1(QueryRunner qr) {
        try {
            Object[] obj = (Object[]) qr.query("select * from student", new ArrayHandler(), new Object[]{});
            for (Object object:obj
                    ) {
                System.out.println(object);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
