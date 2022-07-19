import org.json.JSONArray;
import org.json.JSONException;

import javax.swing.*;
import java.awt.*;
import java.io.NotSerializableException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

public class DeduplicateData
{
    private Person P;

    public ArrayList readJSONArrayAndCreatePersonArray(JSONArray array) throws JSONException
    {

        ArrayList<Person> personArrayList = new ArrayList<Person>();
        String sqlMessage = null;
        String JsonMessage = null;
        try {
            //Loop through the parsed user data
            for (int i = 0; i < array.length(); i++) {
                //call the Person constructor to initialize the instances/attributes
                P = new Person( (Integer) array.getJSONObject( i ).get( "id" )
                        , (String) array.getJSONObject( i ).get( "first_name" )
                        , (String) array.getJSONObject( i ).get( "last_name" )
                        , (Object) array.getJSONObject( i ).get( "roles" )
                        , (Object) array.getJSONObject( i ).get( "default_hourly_rate" )
                        , (Boolean) array.getJSONObject( i ).get( "is_active" ) );

                //call the Person class setters
                P.setId( (Integer) array.getJSONObject( i ).get( "id" ) );
                P.setFirst_name( (String) array.getJSONObject( i ).get( "first_name" ) );
                P.setLast_name( (String) array.getJSONObject( i ).get( "last_name" ) );
                P.setRoles( (Object) array.getJSONObject( i ).get( "roles" ) );
                P.setDefault_hourly_rate( (Object) array.getJSONObject( i ).get( "default_hourly_rate" ) );
                P.setIs_active( (Boolean) array.getJSONObject( i ).get( "is_active" ) );

                //stroe the user information from the API response to an ArrayListe of type Person
                personArrayList.add( P );
            }

        } catch (JSONException a) {
            if (a != null)
                JsonMessage = a.getMessage();
            System.out.println( "JSON Error Message 1: " + JsonMessage );
        }
        return personArrayList;
    }
    public void addChangesToChangesTable(Statement statement,Statement statement3,Statement statement2,Connection connection, ArrayList<Person> personArrayList) throws SQLException, JSONException,NotSerializableException
    {

        SimpleDateFormat dateFormat = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss" );
        String date = dateFormat.format( new Date() );

        Timestamp timestamp = new Timestamp( System.currentTimeMillis() );
        Create_Insert_Queries create_insert_queries = new Create_Insert_Queries();


        ResultSet resultSet = null;
//        ResultSet resultSetCommit = null;
        int resultSetInsert = 0;
        String SQLInsertChangesQuery = "";

        //declare a counter to keep track of the changes count
        int count = 0;
        //initialise variables
        int ID = 0;
        String FNAME = "";
        String LNAME = "";
        Object ROLE = "";
        Object ISACTIVE = false;
        Object HOURLY_RATE = null;


        //display the header of the table
//        System.out.println( String.format( "%-10s %-14s %-20s %-30s %-12s %-23s %-5s",
//                "ID", "First Name", "Last Name", "Role", "Status", "Default H_Rate", "Timestamp" + "\n" +
//                        "---------------------------------------------------------------------------------------------------------------------------------------" ) );
        try{
            for (int i = 0; i < personArrayList.size(); i++) {
                resultSet = statement.executeQuery( "select * from users where id_users = "
                        + personArrayList.get( i ).getId() + " and (role != '"
                        + personArrayList.get( i ).getRoles() + "' or status !="
                        + personArrayList.get( i ).getIs_active() + " or hourly_rate != '"
                        + personArrayList.get( i ).getDefault_hourly_rate() + "')" );
                //                                                    +" and  (hourly_rate!="+userArrayList.get( i ).getDefault_hourly_rate()+
                //                                                    " or role != '"+userArrayList.get( i ).getRoles() +"' or status != "+userArrayList.get( i ).getIs_active()+");");
                while (resultSet.next())
                {
                    count++;
                    int id_users = resultSet.getInt( "id_users" );
                    String first_name = resultSet.getString( "first_name" );
                    String last_name = resultSet.getString( "last_name" );
                    String role = resultSet.getString( "role" );
                    boolean is_active = resultSet.getBoolean( "status" );
                    Object default_hourly_rate = resultSet.getObject( "hourly_rate" );

                    ID = personArrayList.get( i ).getId();
                    FNAME = personArrayList.get( i ).getFirst_name();
                    LNAME = personArrayList.get( i ).getLast_name();
                    ROLE = personArrayList.get( i ).getRoles();
                    ISACTIVE = personArrayList.get( i ).getIs_active();
                    HOURLY_RATE = personArrayList.get( i ).getDefault_hourly_rate();

                    if(!personArrayList.get( i ).getDefault_hourly_rate().equals( default_hourly_rate ))
                    {
                        HOURLY_RATE ="Old: "+default_hourly_rate+" New: "+personArrayList.get( i ).getDefault_hourly_rate();
                    }

                    else if (!personArrayList.get( i ).getRoles().equals( role ))
                    {
                        ROLE = "Old: "+ role +" New: "+personArrayList.get( i ).getRoles();
                    }

                    else if (!personArrayList.get( i ).getFirst_name().equals( first_name ))
                    {
                        FNAME = "Old: "+first_name +" New: "+personArrayList.get( i ).getFirst_name() ;
                    }

                    else if (!personArrayList.get( i ).getLast_name().equals( last_name ))
                    {
                        FNAME = "Old: "+last_name +" New: "+personArrayList.get( i ).getLast_name() ;
                    }
                    else if (personArrayList.get( i ).getIs_active()!=is_active)
                    {
                        String StatusDB = null;
                        String StatusAPI=null;
                        if (is_active == true)
                        {
                            StatusDB = "Active";
                        }
                        else
                            StatusDB = "Inactive";
                        if (personArrayList.get( i ).getIs_active()==true)
                        {
                            StatusAPI = "Active";
                        }
                        else
                            StatusAPI = "Inactive";

                        ISACTIVE = "Old: "+ StatusDB + " New: "+StatusAPI;
                    }

                    SQLInsertChangesQuery = (String) create_insert_queries.InsertDataToChangesTable( ID, FNAME, LNAME, ROLE, ISACTIVE, HOURLY_RATE, timestamp);

                    resultSetInsert = statement2.executeUpdate( SQLInsertChangesQuery );

                }// End of while loop

            }// End of for loop
//             statement2.executeQuery( "commit ;" );
            if (resultSetInsert>=0)
                System.out.println("["+ count+ "] Row were added to the table successfully" );
            else
                System.out.println( "No record was added!!" );
        }
        catch(
                SQLException sqlexception)

        {
            String sqlMessage = null;
            if (sqlexception != null)
                sqlMessage = sqlexception.getMessage();
            System.out.println( "SQL Error Message 1: " + sqlMessage+" \n");
        }
        finally{
//            connection.close();
        }
    }



    public String isTableUserEmpy(Statement statement)throws SQLException
    {
        String sqlMessage ="";
        String isTableEmpty=null;
        ResultSet resultSet = statement.executeQuery( "select * from users");

        try
        {
            if (resultSet.next() == false)
            {
                isTableEmpty = "YES";
            }
            else
            {
                do
                {
                    isTableEmpty = "NO";
                }
                while (resultSet.next());
            }
        }
        catch(SQLException e)
        {
            if(e!=null)
                sqlMessage = e.getMessage();
            System.out.println("SQL Error Message 1: "+ sqlMessage );
        }
        return isTableEmpty;
    }


    public boolean isTableChangesEmpy(Statement statement)throws SQLException
    {
        String sqlMessage ="";
        boolean isTableChangesEmpty=false;
        ResultSet resultSet1 = statement.executeQuery( "select * from changes");

        try

        {
            if (resultSet1.next() == false)
            {
                isTableChangesEmpty = true;
            }
            else
            {
                do
                {
                    isTableChangesEmpty = false;
                }
                while (resultSet1.next());
            }
        }
        catch(SQLException e)
        {
            if(e!=null)
                sqlMessage = e.getMessage();
            System.out.println("SQL Error Message 1: "+ sqlMessage );
        }
        return isTableChangesEmpty;
    }

    public void insertNewRecordsIntoUsersTable(ArrayList<Person> userList,Statement statementInsert) throws SQLException
    {
        int rowcount = 0;
        int sqlInsertIntoUsersTable =0;
        String sqlMessage="";
        Create_Insert_Queries create_insert_queries = new Create_Insert_Queries();
        try
        {
            //add users new records
            for (int i=0;i<userList.size();i++)
            {
                int ID =userList.get( i ).getId();
                String FNAME = userList.get( i ).getFirst_name();
                String LNAME = userList.get( i ).getLast_name();
                Object ROLE = userList.get( i ).getRoles();
                boolean ISACTIVE = userList.get( i ).getIs_active();
                Object HOURLY_RATE = userList.get( i ).getDefault_hourly_rate();

                sqlInsertIntoUsersTable = statementInsert.executeUpdate( create_insert_queries.InsertDataToUsersTable( ID,FNAME,LNAME,ROLE, ISACTIVE, HOURLY_RATE ));
                ResultSet resultSet = statementInsert.executeQuery("Commit ;");
                rowcount++;
            }
            //debug message to show that the data was added successfully or not
            if (sqlInsertIntoUsersTable>=0)
                System.out.println("["+ rowcount+ "] Row were added to the table successfully" );
            else
                System.out.println( "No record was added!!" );
        }
        catch(SQLException e)
        {
            if(e!=null)
                sqlMessage = e.getMessage();
            System.out.println( "SQL Error Message 1: "+sqlMessage );
        }
    }

    public String isUserIDEquals(Statement statement,ArrayList<Person> personArrayList) throws SQLException
    {
        String uderIDEqual = null;
        ResultSet resultSet ;
        try {
            for (int i = 0; i < personArrayList.size(); i++)
            {
                resultSet = statement.executeQuery("Select * from users where id_users ="+personArrayList.get( i ).getId());

                if (resultSet.next())
                {
                    uderIDEqual = "YES";

                }
                else
                {
                    uderIDEqual = "NO";
                    break;
                }

            }
        } catch (SQLException e) {
            String sqlMessage = null;
            if (e != null)
                sqlMessage = e.getMessage();
            System.out.println( "SQL Error Message 1: " + sqlMessage );
        }

        return uderIDEqual;
    }

    public void addNewUserRecord(Statement statement, ArrayList<Person> person,Create_Insert_Queries create_insert_queries) throws SQLException
    {
        int count =0;
        int sqlInsertIntoUsersTable =0;
        ResultSet resultSet =null;
        try {
            for (int i = 0; i < person.size(); i++)
            {
                resultSet = statement.executeQuery("Select * from users where id_users ="+person.get( i ).getId());

                if (!resultSet.next())
                {
                    count++;

                    sqlInsertIntoUsersTable = statement.executeUpdate( create_insert_queries.InsertDataToUsersTable(person.get( i ).getId()
                            , person.get( i ).getFirst_name(), person.get( i ).getLast_name(), person.get( i ).getRoles(),
                            person.get( i ).getIs_active(), person.get( i ).getDefault_hourly_rate() ));

                    ResultSet resultSet1 = statement.executeQuery("Commit ;");

                }
            }

        } catch (SQLException e) {
            String sqlMessage = null;
            if (e != null)
                sqlMessage = e.getMessage();
            System.out.println( "SQL Error Message 1: " + sqlMessage );
        }
    }

    public void addNewChangesRecord(Statement statement,Statement statement1 ,ArrayList<Person> personArrayList,Create_Insert_Queries create_insert_queries) throws SQLException
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss" );
        String date = dateFormat.format( new Date() );

        Timestamp timestamp = new Timestamp( System.currentTimeMillis() );
        int count =0;
        ResultSet resultSet;
        String SQLInsertChangesQuery = "";
        ResultSet resultSet1;
        int insertNewChanges;

        int id_usersChanges =0;
        String first_nameChanges =null;
        String last_nameChanges = null;
        String roleChanges =null;
        Object is_activeChanges = null;
        Object default_hourly_rateChanges = null;

        try {

            for (int i = 0; i < personArrayList.size(); i++)
            {
                resultSet1 = statement1.executeQuery( "select * from users where id_users = "
                                            + personArrayList.get( i ).getId() + " and (role != '"
                                            + personArrayList.get( i ).getRoles() + "' or status !="
                                            + personArrayList.get( i ).getIs_active() + " or hourly_rate != '"
                                            + personArrayList.get( i ).getDefault_hourly_rate() + "')" );

                resultSet = statement.executeQuery( "select * from changes where id_users = "
                                            + personArrayList.get( i ).getId() + " " +
                                            " and  first_name LIKE '%"+ personArrayList.get( i ).getFirst_name()+
                                            "%' and last_name LIKE '%"+ personArrayList.get( i ).getLast_name()+
                                            "%' and role LIKE '%"+ personArrayList.get( i ).getRoles() +
                                            "%' and status LIKE "+ personArrayList.get( i ).getIs_active() +
                                            " and hourly_rate LIKE '%"+ personArrayList.get( i ).getDefault_hourly_rate() + "%'" );



                if (resultSet1.next())
                {
                    int ID = personArrayList.get( i ).getId();
                    String FNAME = personArrayList.get( i ).getFirst_name();
                    String LNAME = personArrayList.get( i ).getLast_name();
                    Object ROLE = personArrayList.get( i ).getRoles();
                    Object ISACTIVE = personArrayList.get( i ).getIs_active();
                    Object HOURLY_RATE = personArrayList.get( i ).getDefault_hourly_rate();


                    int id_users = resultSet1.getInt( "id_users" );
                    String first_name = resultSet1.getString( "first_name" );
                    String last_name = resultSet1.getString( "last_name" );
                    String role = resultSet1.getString( "role" );
                    boolean is_active = resultSet1.getBoolean( "status" );
                    Object default_hourly_rate = resultSet1.getObject( "hourly_rate" );

                    if(!personArrayList.get( i ).getDefault_hourly_rate().equals( default_hourly_rate ))
                    {
                        HOURLY_RATE ="Old: "+default_hourly_rate+" New: "+personArrayList.get( i ).getDefault_hourly_rate();
                    }

                    else if (!personArrayList.get( i ).getRoles().equals( role ))
                    {
                        ROLE = "Old: "+ role + " New: "+personArrayList.get( i ).getRoles();
                    }

                    else if (!personArrayList.get( i ).getFirst_name().equals( first_name ))
                    {
                        FNAME = "Old: "+first_name +" New: "+personArrayList.get( i ).getFirst_name();
                    }

                    else if (!personArrayList.get( i ).getLast_name().equals( last_name ))
                    {
                        FNAME = "Old: "+last_name +" New: "+personArrayList.get( i ).getLast_name();
                    }
                    else if (personArrayList.get( i ).getIs_active()!=is_active)
                    {
                        ISACTIVE = "Old: "+is_active +" New: "+personArrayList.get( i ).getIs_active();
                    }


                    if (resultSet.next())
                    {
                        id_usersChanges = resultSet1.getInt( "id_users" );
                        first_nameChanges = resultSet1.getString( "first_name" );
                        last_nameChanges = resultSet1.getString( "last_name" );
                        roleChanges = resultSet1.getString( "role" );
                        is_activeChanges = resultSet1.getBoolean( "status" );
                        default_hourly_rateChanges = resultSet1.getObject( "hourly_rate" );

//                        System.out.println( "Changes already exist !!" );
//                        break;
                    }
                    else
                    {
                        if(!personArrayList.get( i ).getDefault_hourly_rate().equals( default_hourly_rate ))
                        {
                            HOURLY_RATE ="Old: "+default_hourly_rate+" New: "+personArrayList.get( i ).getDefault_hourly_rate();
                        }

                        if (!personArrayList.get( i ).getRoles().equals( role ))
                        {
                            ROLE = "Old: "+ role + " New: "+personArrayList.get( i ).getRoles();
                        }

                        if (!personArrayList.get( i ).getFirst_name().equals( first_name ))
                        {
                            FNAME = "Old: "+first_name +" New: "+personArrayList.get( i ).getFirst_name();
                        }

                        if (!personArrayList.get( i ).getLast_name().equals( last_name ))
                        {
                            FNAME = "Old: "+last_name +" New: "+personArrayList.get( i ).getLast_name();
                        }
                        if (personArrayList.get( i ).getIs_active()!=is_active)
                        {
                            ISACTIVE = "Old: "+is_active +" New: "+personArrayList.get( i ).getIs_active();
                        }

//                        if (HOURLY_RATE != default_hourly_rateChanges || is_activeChanges != ISACTIVE || roleChanges != ROLE)
                            SQLInsertChangesQuery = create_insert_queries.InsertDataToChangesTable( ID, FNAME, LNAME, ROLE, ISACTIVE, HOURLY_RATE, timestamp );
                        statement.executeUpdate( SQLInsertChangesQuery );
                        statement1.executeQuery("Commit ;");
                    }

                }
            }
        } catch (SQLException e) {
            String sqlMessage = null;
            if (e != null)
                sqlMessage = e.getMessage();
            System.out.println( "SQL Error Message 1: " + sqlMessage );
        }
    }

    public String changesUserIDEqual(Statement statement,ArrayList<Person> personArrayList) throws SQLException
    {
        String userIDEqual = null;
        ResultSet resultSet ;
        try {
            for (int i = 0; i < personArrayList.size(); i++)
            {
                resultSet = statement.executeQuery("Select * from changes where id_users ="+personArrayList.get( i ).getId() );

                if (resultSet.next())
                {
                    userIDEqual = "YES";
//                    System.out.println( " We have Some Matching records\n");
                    break;
                }
                else
                {
                    userIDEqual = "NO";
//                    System.out.println( "We don't have any  matching record \n");
                    break;

                }

            }
        } catch (SQLException e) {
            String sqlMessage = null;
            if (e != null)
                sqlMessage = e.getMessage();
            System.out.println( "SQL Error Message 1: " + sqlMessage );
        }
        return userIDEqual;
    }

    public void displayUserJTable(Statement statement) throws Exception,SQLException
    {

        try {
            ResultSet res = null;

            ResultSet res1 = null;

            res = statement.executeQuery( "select * from users" );

//            res1 = statement.executeQuery( "select * from changes" );

            //get meta data for table users
            ResultSetMetaData resultSetMetaData1 = res.getMetaData();

            //get meta data for table changes
            int usersColumnCount = resultSetMetaData1.getColumnCount();

            Vector usrCulumVector = new Vector( usersColumnCount );

            for (int i = 1; i <= usersColumnCount; i++) {
                usrCulumVector.add( resultSetMetaData1.getColumnName( i ) );
            }

            Vector data = new Vector();

            Vector row = new Vector();

            while (res.next())
            {
                row = new Vector(usersColumnCount);
                for(int i=1;i<= usersColumnCount;i++ )
                {
                    row.add( res.getString( i ) );
                }
                data.add( row );

            }
            JFrame frame = new JFrame("Users Table");
            frame.setSize( 800,300 );
            frame.setLocationRelativeTo( null );
            frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
            JPanel panel = new JPanel();
            JTable userTable = new JTable( data,usrCulumVector );
            JScrollPane jsp = new JScrollPane( userTable );
            panel.setLayout( new BorderLayout(  ) );
            panel.add(jsp,BorderLayout.CENTER);
            frame.setContentPane( panel );
            frame.setVisible( true );
        }
        catch (SQLException f)
        {
            String sqlMessage = null;
            if (f != null)
                sqlMessage = f.getMessage();
            JOptionPane.showMessageDialog( null, "SQL Error :"+ sqlMessage );
        } catch(Exception e)
        {
            JOptionPane.showMessageDialog( null, "Error reading the JTable " );
        }
    }

    public void displayChangesJTable(Statement statement) throws Exception,SQLException
    {

        try {
            ResultSet res = null;

            ResultSet res1 = null;

            res = statement.executeQuery( "select * from changes" );

//            res1 = statement.executeQuery( "select * from changes" );

            //get meta data for table users
            ResultSetMetaData resultSetMetaData1 = res.getMetaData();

            //get meta data for table changes
            int usersColumnCount = resultSetMetaData1.getColumnCount();

            Vector usrCulumVector = new Vector( usersColumnCount );

            for (int i = 1; i <= usersColumnCount; i++) {
                usrCulumVector.add( resultSetMetaData1.getColumnName( i ) );
            }

            Vector data = new Vector();

            Vector row = new Vector();

            while (res.next())
            {
                row = new Vector(usersColumnCount);
                for(int i=1;i<= usersColumnCount;i++ )
                {
                    row.add( res.getString( i ) );
                }
                data.add( row );

            }
            JFrame frame = new JFrame("Changes Table");
            frame.setSize( 800,120 );
            frame.setLocation( 285,540 );
            frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
            JPanel panel = new JPanel();
            JTable userTable = new JTable( data,usrCulumVector );
            JScrollPane jsp = new JScrollPane( userTable );
            panel.setLayout( new BorderLayout(  ) );
            panel.add(jsp,BorderLayout.CENTER);
            frame.setContentPane( panel );
            frame.setVisible( true );
        }
        catch (SQLException f)
        {
            String sqlMessage = null;
            if (f != null)
                sqlMessage = f.getMessage();
            JOptionPane.showMessageDialog( null, "SQL Error :"+ sqlMessage );
        } catch(Exception e)
        {
            JOptionPane.showMessageDialog( null, "Error reading the JTable " );
        }
    }
}





