package lab1;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
    Connection conn = null;

    public boolean isConnectionAvailable() {        
        try {
            String url = "jdbc:sql:todo-db/todo.db";
            conn = DriverManager.getConnection(url);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
