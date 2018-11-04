import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BeanUtilDemo {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException
    {
        /*test();*/
       /* test2();*/
       //从一个map集合中拷贝到一个javabean对象
        ConvertUtils.register(new MyDateConvert(),Date.class);
       Map map=new HashMap();
       map.put("id","001");
       /* map.put("name","jack");*/
       map.put("name",new String[]{"jack","roy"});
       //如果拷贝的数据是数组的话，则拷贝数组的第一个数据
        map.put("gender","true");
        map.put("score","88.88");
        map.put("birth","2018/9/4");
        Student student2 = (Student) Class.forName("Student").newInstance();
         BeanUtils.copyProperties(student2,map);
        System.out.println(student2.toString());
    }

    private static void test2() throws InstantiationException, IllegalAccessException, ClassNotFoundException, InvocationTargetException {
        // 从一个javabean对象拷贝到另一个javabean对象
        Student student = new Student();
        student.setId(1);
        student.setName("roy");
        student.setScore(89.88);
        student.setGender(true);
        student.setBirth(new Date());
        Student student2 = (Student) Class.forName("Student").newInstance();
        BeanUtils.copyProperties(student2,student);
        System.out.println(student2.toString());
    }

    private static void test() throws InstantiationException, IllegalAccessException, ClassNotFoundException, InvocationTargetException {
       //拷贝一个javaBean对象的属性
        Student student = new Student();
        student.setId(1);
        student.setName("roy");
        student.setScore(89.88);
        student.setGender(true);
        student.setBirth(new Date());
        //通过反射构造对象
        Student student2 = (Student) Class.forName("Student").newInstance();
       /* BeanUtils.copyProperty(student2,"id", student.getId());
        BeanUtils.copyProperty(student2,"name", student.getName());
        BeanUtils.copyProperty(student2,"gender", student.getGender());
        BeanUtils.copyProperty(student2,"score", student.getScore());
        BeanUtils.copyProperty(student2,"birth", student.getBirth());*/

        ConvertUtils.register(new MyDateConvert(),Date.class);
        BeanUtils.copyProperty(student2,"id", "001");
        BeanUtils.copyProperty(student2,"name", "陈琳");
        BeanUtils.copyProperty(student2,"gender", true);
        BeanUtils.copyProperty(student2,"score", "89.99");
        BeanUtils.copyProperty(student2,"birth", "2018/9/4");
        System.out.println(student2.toString());
    }
}
 //自定义日期转换器
     class MyDateConvert implements Converter{

     @Override
     public Object convert(Class aClass, Object value) {
         SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
         if (aClass==Date.class){
             String date=(String) value;
             try {
                 Date curDate = sdf.parse(date);
                 return curDate;
             } catch (ParseException e) {
                 e.printStackTrace();
             }
         }
         return null;
     }
 }
