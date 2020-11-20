package main;

import java.sql.*;

public class DatabaseConnection{
    protected Connection conn;
    protected Statement stmt;
    protected final String host = "localhost";
    protected final String port = "5432";
    protected final String user = "postgres";
    protected final String password = "123456";
    protected final String database = "cafe";

    public void connect(){
        try{
            Class.forName("org.postgresql.Driver"); // Nap trinh dieu khien Postgresql
        }catch(ClassNotFoundException ex){
            System.out.print("Error: " + ex.getMessage());
        }
        try{
            String url = "jdbc:postgresql://" + host + ":" + port + "/" + database;
            conn = DriverManager.getConnection(url, user, password);
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        }catch(SQLException se){
            System.err.println("Error: " + se.getMessage());
        }
    }

    public Statement getStmt(){
        return stmt;
    }

    public void close() throws SQLException{
        if(conn != null){
            conn.close();
        }
    }
}
