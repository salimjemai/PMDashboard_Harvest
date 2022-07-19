import java.sql.*;

public class Create_Insert_Queries
{

    public String InsertDataToUsersTable(int Id, String first_name, String Last_name, Object Role, boolean Status, Object Hourly_rate)
    {
        String InsertIntoUsersQuery = "Insert into users values ("+Id+",'"+first_name+"','"+Last_name+"','"+Role+"',"+Status+","+Hourly_rate+");";
        return InsertIntoUsersQuery;
    }

    public String InsertDataToChangesTable(int id_users, String first_name, String Last_name, Object role, Object status, Object hourly_rate, Timestamp timestamp)
    {
        String InsertIntoChangesQuery = "Insert into changes (id_users,first_name,last_name,role,status,hourly_rate,Timestamp) values ("+id_users+",'"+first_name+"','"+Last_name+"','"+role+"',"+status+",'"+hourly_rate+"','"+timestamp+"');";

        return InsertIntoChangesQuery;
    }

   //method to display the query
    public  void displayUsersTable(ResultSet resultSet) throws SQLException {
        int Id = 0;
        String First_name =null;
        String Last_name = null;
        String Role = null;
        boolean Status = false;
        Object Hourly_rate = 0;

        String sqlMessage = null;

        try
        {
            //display the header of the table
            System.out.println( String.format( "%-10s %-14s %-20s %-30s %-14s %-23s ",
                    "ID","First Name","Last Name","Role","Status","Default H_Rate"+"\n"+
                            "-------------------------------------------------------------------------------------------------------------"));

            while (resultSet.next())
            {
                Id = resultSet.getInt("id_users");
                First_name = resultSet.getString("first_name");
                Last_name = resultSet.getString("last_name");
                Role = resultSet.getString("role");
                Status = resultSet.getBoolean( "status" );
                Hourly_rate = resultSet.getObject( "hourly_rate" );

                System.out.println( String.format("%-10s %-14s %-16s %-35s %-15s %-35s",Id,First_name,Last_name,Role,Status,Hourly_rate));

            } // End of while loop

            System.out.printf("-------------------------------------------------------------------------------------------------------------\n");
        }
        catch (SQLException e)
        {
            if (e != null)
                sqlMessage = e.getMessage();

            System.out.println("SQL Error Message 1: " + sqlMessage);
        }
    } //end of method displayfirstJointQuery

    public void displayChangesTable(ResultSet resultSet) throws SQLException

    {

        String sqlMessage = null;
        try
        {
            //display the header of the table
            System.out.println( String.format( "%-8s %-15s %-18s %-65s %-15s %-20s %-15s",
                    "ChangeID","F_Name","L_Name","Role","Status","H_Rate","Timestamp"));
            System.out.println( "-------------------------------------------------------------------------------------" +
                    "----------------------------------------------------------------------------------");

            while (resultSet.next())
            {
                int ChangeID = resultSet.getInt( "ChangeID" );
                int Id = resultSet.getInt("id_users");
                String First_name = resultSet.getString("first_name");
                String Last_name = resultSet.getString("last_name");
                Object Role = resultSet.getString("role");
                Object Status = resultSet.getBoolean( "status" );
                Object Hourly_rate = resultSet.getObject( "hourly_rate" );
                Object Timestamp = resultSet.getObject("Timestamp"  );

                System.out.println( String.format("%-8s %-15s %-15s %-70s %-13s %-18s %-15s",ChangeID,First_name,Last_name,Role,Status,Hourly_rate,Timestamp));

            } // End of while loop

            System.out.printf("--------------------------------------------------------------------------------------" +
                    "----------------------------------------------------------------------------------\n");
        }
        catch (SQLException e)
        {
            if (e != null)
                sqlMessage = e.getMessage();

            System.out.println("SQL Error Message 1: " + sqlMessage);
        }
    }

    public void createUserAndChangesTables(Statement statement) throws SQLException
    {
        String CreateUsersTblQuery =
                "Create Table IF NOT EXISTS users (" +
                        "  id_users int(11) NOT NULL," +
                        "  first_name varchar(45) DEFAULT NULL," +
                        "  last_name varchar(45) DEFAULT NULL," +
                        "  role varchar(45) DEFAULT NULL," +
                        "  status tinyint(4) DEFAULT NULL," +
                        "  hourly_rate TINYINT DEFAULT NULL," +
                        "  PRIMARY KEY (id_users));";

        String CreateChangesTblQuery = "Create table IF NOT EXISTS changes(" +
                "  changeID INT NOT NULL AUTO_INCREMENT," +
                "  id_users INT(11) ," +
                "  first_name VARCHAR(100)," +
                "  last_name VARCHAR(100)," +
                "  role VARCHAR(100)," +
                "  status VARCHAR(100)," +
                "  hourly_rate VARCHAR (20)," +
                "  Timestamp TIMESTAMP ," +
                "  Primary key (ChangeID)," +
                "  FOREIGN KEY (id_users) REFERENCES users(id_users));";

        try
        {
            int executeScript1 = statement.executeUpdate( CreateUsersTblQuery);
            int executeScript2 = statement.executeUpdate( CreateChangesTblQuery );
//            System.out.println( "Table users was successfully created!!" );

        }
        catch (SQLException e)
        { String sqlMessage1 = null;
            if (e != null)
                sqlMessage1 = e.getMessage();

            System.out.println("SQL Error Message 1: " + sqlMessage1);
        }

    }
    public void createDB(Statement statement) throws SQLException
    {
        String createDatabase = "create database if not exists harvestdb ;";
        String useDB = "USE harvestdb ;";
        try
        {
            int executeScript3 = statement.executeUpdate( createDatabase );
            int executeScript4 = statement.executeUpdate( useDB );
        }
        catch (SQLException e)
        { String sqlMessage1 = null;
            if (e != null)
                sqlMessage1 = e.getMessage();

            System.out.println("SQL Error Message 1: " + sqlMessage1);
        }
    }
}



