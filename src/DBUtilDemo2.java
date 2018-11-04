import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.*;

import java.sql.SQLException;
import java.util.List;

public class DBUtilDemo2 {
    public static void main(String[] args) {
        QueryRunner  qr =new QueryRunner(JdbcUtil2.getDataSources());
        /*
        * ResultSetHandler接口: 用于把结果集封装成不同类型的对象
        * ArrayHandler类: 把结果集的第一行结果封装成对象数组
        * ArrayListHandler类： 把结果集的每一行结果封装成对象数组，把每个对象数组放入List集合中
        * BeanHandler类： 把结果集的第一行，封装成javabean对象(常用)
        * BeanListHandler类： 把结果集的每行封装成javabean，把每个javabean放入List集合中（常用）
        * ScalarHandler类：查询聚合函数(例如:count(*))  (常用)
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
