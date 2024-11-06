package sample.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.sql.DataSource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class DBUtils {
    private static final String DB_NAME="UserManagement";
    private static final String DB_USER_NAME="sa";
    private static final String DB_PASSWORD="12345";

    public static Connection getConnectionV1() throws ClassNotFoundException, SQLException {
        Connection conn=null;
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String url="jdbc:sqlserver://localhost:1433;databaseName="+DB_NAME;
            //+";encrypt=true;trustServerCertificate=true"; //this encrypt the connection and allows the server to accept ANY certificate
        conn= DriverManager.getConnection(url, DB_USER_NAME, DB_PASSWORD);
        return conn;
    }
    
    public static Connection getConnection() throws ClassNotFoundException, SQLException, NamingException{
        Connection conn=null;
        Context context = new InitialContext();
        Context end = (Context) context.lookup("java:comp/env");
        DataSource ds = (DataSource) end.lookup("DBCon");
        conn = ds.getConnection();
        return conn;
    }
}

