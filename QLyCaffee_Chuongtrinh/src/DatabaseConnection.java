import java.sql.*;

public class DatabaseConnection{
    protected Statement stmt;
    protected final String host = "localhost";
    protected final String port = "5432";
    protected final String user = "postgres";
    protected final String password = "123456";
    protected final String database = "cafe";

    public void connect(){
        Connection con;
        try{
            Class.forName("org.postgresql.Driver"); // Nap trinh dieu khien Postgresql
        }catch(ClassNotFoundException ex){
            System.out.print("Error: " + ex.getMessage());
        }
        try{
            String url = "jdbc:postgresql://" + host + ":" + port + "/" + database;
            con = DriverManager.getConnection(url, user, password);
            this.stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        }catch(SQLException se){
            System.err.println("Error: " + se.getMessage());
        }
    }

    public Statement getStmt(){
        return this.stmt;
    }
}
