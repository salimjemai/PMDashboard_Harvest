//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//
// commit

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class harvestAPIConnection {
    public static Scanner input;
    public static String tableExist = "";
    public static String changesTableExist = "";
    public static String sqlMessage1 = "";
    public static String sqlMessage2 = "";
//    Person P;


    public static void main(String[] args) throws Exception {

        int id;
        String fname;
        String lname;
        Object roles;
        String email;
        Object default_hourly_rate;
        boolean is_active;

        //declare and initialize database username and password
//        String userDB = "";
//        String passwordDB="";

        //define the authentication variables
        String url = "https://api.harvestapp.com/api/v2/users";
        String applicationType = "application/json";
        String appName = "MyApp(salimjemai2015@gmail.com)";
        String accessToken = "1646320.pt.YznwxofDavpbXAyGIH1mMoIuwCCXTYe8EameW9Ke-Ey287-5SbTZ--z2MtcZH01FvuZ249qadSqfQVWrs0qpiA";
        String accountID = "962234";

        //instantiate an object of the API URL
        URL API_URL = new URL( url );

        //instantiate a conn object that will create a connection using the API URL
        HttpURLConnection conn = (HttpURLConnection) API_URL.openConnection();

        //send the GET request and the authentication to server
        conn.setRequestMethod( "GET" );
        conn.setRequestProperty( "Accept", applicationType );
        conn.setRequestProperty( "User-Agent", appName );
        conn.setRequestProperty( "Authorization", "Bearer " + accessToken );
        conn.setRequestProperty( "Harvest-Account-ID", accountID );

        //This string will store the response as we iterate in the response returned by the server
        String inputLine = "";

        //we dumped the json payload to the standard out stream
        BufferedReader in = new BufferedReader( new InputStreamReader( conn.getInputStream() ) );

        //this scanner is reading the data from the BufferedReader object "in"
        Scanner sc = new Scanner( in );

        //writing records into intpuLine while iterating thru the response
        while (sc.hasNext()) {
            inputLine += sc.nextLine();
        }
        //parse the json object to string
        JSONObject json = new JSONObject( inputLine );

        //create an object of type MainUserDataResponse class to store the users data
        MainUserDataResponse USR = new MainUserDataResponse();

        //instantiate an object of type WriteDataIntoPersonRescord
        WriteDataIntoPersonRescord obj1 = new WriteDataIntoPersonRescord();

        //call obj1 processJSONFileToUsersRecord method to store each JSON object into a Java Object
        //that will be used later-on
        obj1.processJSONFileToUsersRecord( json, USR );

        //convert the API JSON raw data to objects
        JSONObject employeeData = new JSONObject( inputLine );

        //parse the user array
        JSONArray userArray = new JSONArray( employeeData.get( "users" ).toString() );

        HarvestDBConnection connection = new HarvestDBConnection();

        //create a data base connection
        connection.setConnection();

        //print the connection status   //
        //database connection test
        connection.connectionValidation();

        //create a statement object
        Statement statement = connection.getConnection().createStatement();

        //create a statement object for insertion
        Statement statementInsert = connection.getConnection().createStatement();

        //create a statement object for commit
        Statement statementCommit = connection.getConnection().createStatement();

        //statement for create
        Statement statementCreate1 = connection.getConnection().createStatement();
        Statement statementCreate2 = connection.getConnection().createStatement();


        Create_Insert_Queries create_insert_queries = new Create_Insert_Queries();

        // create database tables Users and Changes
        create_insert_queries.createUserAndChangesTables( statement );


        DeduplicateData deduplicateData = new DeduplicateData();

        //read the data from the API endpoit then save into an arrayList of tye person
        ArrayList<Person> personArrayList = deduplicateData.readJSONArrayAndCreatePersonArray( userArray );



//        //read the person arraylist
//        System.out.println( String.format( "%-7s %-10s %-12s %-22s %-18s %-29s %-10s",
//                "Record#","ID", "First Name", "Last Name", "Status", "Role", "Default H_Rate" + "\n" +
//                        "\"-------------------------------------------------------------------------------------------------------------------" ) );
//        for (int i = 0; i < personArrayList.size(); i++) {
//            System.out.println( String.format( "%-7s %-10s %-14s %-20s %-16s %-35s %-12s"
//                    , i + 1
//                    , personArrayList.get( i ).getId()
//                    , personArrayList.get( i ).getFirst_name()
//                    , personArrayList.get( i ).getLast_name()
//                    , personArrayList.get( i ).getIs_active()
//                    , personArrayList.get( i ).getRoles()
//                    , personArrayList.get( i ).getDefault_hourly_rate() ) );
//        }
//        System.out.println( "-------------------------------------------------------------------------------------------------------------------" );

        String isUserTableEmpty = deduplicateData.isTableUserEmpy( statement );

        System.out.println( "\nUser table is Empty? " + isUserTableEmpty + "\n" );

        //the switch case statement will detemine if no create the table then run the code
        //if YES just run the code without creating the tables
        switch (isUserTableEmpty) {
            case "YES": // if the table doesn't exist do this case (create the tables and popluate the data)
            {
                //insert new records to the user table
                deduplicateData.insertNewRecordsIntoUsersTable( personArrayList, statementInsert );
//                int executeInsert = statementInsert.executeUpdate();
                break;
            }
            case "NO": //if the user table isn't empty
            {

                String isUserIDEquals = deduplicateData.isUserIDEquals( statement, personArrayList );

                switch (isUserIDEquals)
                {
                    case "YES": // if Id users are equals then check for any changes in the rest of the columns

                        //            verify if the changes table empty
                        //display the changes table
                        boolean changesTableEmpty = deduplicateData.isTableChangesEmpy(statement);
                        String isChangesUserIDEqual = deduplicateData.changesUserIDEqual( statement,personArrayList );


                        ResultSet res1;

                        if (changesTableEmpty == true)
                        {
                            System.out.println( "Reading the changes.... \n" );
                            //adding the changes to the changes table
                            deduplicateData.addChangesToChangesTable( statement, statementCommit, statementInsert, connection.getConnection(), personArrayList );
                            System.out.println( "\n" );
                        }
                        else //if the changes table has records check for duplicate
                        {
                            boolean noChanges = deduplicateData.noChanges( statement,personArrayList );

                            if (noChanges)   //if there is no changes in the API data
                                System.out.println( "there is no changes\n" );

                            else  //if there is any changes in the API users record including first_name and last_name
                                {
                                deduplicateData.addNewChangesRecord( statement,statementCreate1,personArrayList,create_insert_queries );
                            }

                        }
                        //method that verify if the id_users from API vs database aren't matching add new record to the users table
                        break;

                    case "NO": // ids are not the same add new record
                    {
                        System.out.println( "We have new user to add\n" );
                        deduplicateData.addNewUserRecord( statement, personArrayList, create_insert_queries ); // add any new record to the database
                        break;
                    }
                }
            }
            break;
        }
        System.out.println( "\n**************************Users Table************************** \n" );

//        deduplicateData.displayUserJTable(statementCreate2);
//
//        deduplicateData.displayChangesJTable( statementCreate1 );

        ResultSet res = statement.executeQuery( "select * from users" );

//        display the data from the users table
        create_insert_queries.displayUsersTable( res );


        boolean changesTableEmpty = deduplicateData.isTableChangesEmpy(statement);
        if (changesTableEmpty)
        {
            System.out.println( "The changes table is empty !" );
        }
        else {
            System.out.println( "\n**************************Changes Table************************** \n" );
            ResultSet res1 = statement.executeQuery( "select * from changes" );

            //        display the data from the users table
            create_insert_queries.displayChangesTable( res1 );
        }

    }
}
