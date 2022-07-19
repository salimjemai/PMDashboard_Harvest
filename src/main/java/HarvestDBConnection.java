import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;
//import java.util.concurrent.TimeUnit;

public class HarvestDBConnection {

    private static Connection connection = null;
//    private static Object JdbcConnection;
    private static String sqlMessage="";
    private static final String DBInstanceURL = "harvest-widget-db-instance.c4dqa1rshpr3.us-east-1.rds.amazonaws.com";
    private static final String UserName = "InventiveInterns";
    private static final String UserPassword = "Inventive2018";
    private static Create_Insert_Queries create_insert_queries ;

    public void setConnection() throws ClassNotFoundException, SQLException
    {

        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection("jdbc:mysql://"+DBInstanceURL+":3306/harvestdb",UserName, UserPassword );

            Statement statement = this.connection.createStatement();

            create_insert_queries.createDB(statement);
        }

//        "harvest-widget-db-instance.c4dqa1rshpr3.us-east-1.rds.amazonaws.com:3306/harvest-widget-db-instance?autoReconnect=true&useSSL=false",
//                "InventiveInterns", "Inventive2018"
        catch (Exception c)
        {
            if (connection !=null)
            {
                c.getCause();
            }
            System.out.println( "Connection Failed! Check output console" );
        }
    }

    public Connection getConnection()
    {
        return this.connection;
    }

    public void connectionValidation() throws InterruptedException, SQLException
    {
        if (getConnection() != null)
        {
            System.out.println( "\n-------- MySQL JDBC Connection Testing ------------\n" );
            System.out.print( "Connecting to DB " );
            TimeUnit.SECONDS.sleep( 1 );
            System.out.print( "." );
            TimeUnit.SECONDS.sleep( 1 );
            System.out.print( "." );
            TimeUnit.SECONDS.sleep( 1 );
            System.out.print( ". \n" );
            TimeUnit.SECONDS.sleep( 1 );
            System.out.println( "Successfully connected to the Database!" );
            TimeUnit.SECONDS.sleep( 1/2 );
        }
//        else
//        {    throw new SQLException( "Connection failed!" );}
        System.out.println( "------------- End Of Connection Test ---------------" );
    }
    public void close(Statement statement, Connection con){
        try{
            statement.close();
            con.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}