
import java.sql.SQLException;

public class MetaDataDemo2 {
    public static void main(String[] args) throws SQLException {

         //SQLUtil.update("insert into student values(?,?,?,?,?)",new Object[]{1,"ROY","ÄÐ",90.99,"2018-9-9"});
         SQLUtil.update("update student set name=?,gender=? where id=?",new Object[]{"lily","Å®",1});

    }
}
