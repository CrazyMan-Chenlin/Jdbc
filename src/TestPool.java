import java.sql.Connection;
import java.sql.SQLException;

public class TestPool {
    public static void main(String[] args) {
        MyPool myPool = new MyPool();
        for (int i = 0; i < 10; i++) {
            Connection conn = myPool.getConnection();
            System.out.println(conn);
            if (i == 3) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    e.getMessage();
                }
            }
        }
    }
}