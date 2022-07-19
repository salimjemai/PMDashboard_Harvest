//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class WriteDataIntoPersonRescord
{
    ArrayList<Person> userList =  new ArrayList<Person>(  );
    public WriteDataIntoPersonRescord()
    {
    }

    public void processJSONFileToUsersRecord(JSONObject json, MainUserDataResponse Users) throws JSONException
    {
        Object next_page=json.get("next_page");
        int per_page= (Integer)json.get( "per_page" );
        Object links = json.get("links");
        int total_pages = (Integer)json.get( "total_pages" );
        Object previous_page = json.get("previous_page");
        int page = (Integer)json.get( "page" );
        Object users = json.get("users");

        Users.setNextPage( next_page );
        Users.setPer_page( per_page );
        Users.setLink( links );
        Users.setTotal_pages( total_pages );
        Users.setPrevious_page( previous_page );
        Users.setPage( page );
        Users.setUsers( users );
    }
//
//    public void processJSONFileToPersonRecord(JSONArray User , Person P) throws JSONException {
//
//
//
//        int id = (Integer) User.get( "id" );
//        String first_name = (String) User.get( "first_name" );
//        String last_name = (String) User.get( "last_name" );
//        Object roles = User.get( "roles" );
//        String telephone = (String) User.get( "telephone" );
//        String email = (String) User.get( "email" );
//        Object default_hourly_rate =User.get( "default_hourly_rate" );
//        Object cost_rate = User.get( "cost_rate" );
//        String avatar_url = (String) User.get( "avatar_url" );
//        boolean is_contractor = (Boolean) User.get( "is_contractor" );
//        boolean is_project_manager = (Boolean) User.get( "is_project_manager" );
//        boolean is_admin = (Boolean) User.get( "is_admin" );
//        boolean has_access_to_all_future_projects = (Boolean) User.get( "has_access_to_all_future_projects" );
//        boolean can_create_projects = (Boolean) User.get( "can_create_projects" );
//        Object created_at = User.get( "created_at" );
//        Object updated_at = User.get( "updated_at" );
//        boolean is_active = (Boolean) User.get( "is_active" );
//        Object weekly_capacity = User.get( "weekly_capacity" );
//        Object timezone = User.get( "timezone" );
//        boolean can_see_rates = (Boolean) User.get( "can_see_rates" );
//        boolean can_create_invoices = (Boolean) User.get( "can_create_invoices" );
//
////        P = new Person(id,first_name,last_name,roles,default_hourly_rate,is_active);
//
////        userList(P);
//
//        P.setId( (Integer) id );
//        P.setFirst_name(first_name);
//        P.setLast_name(last_name);
//        P.setRoles(roles);
//        P.setTelephone(telephone);
//        P.setEmail(email);
//        P.setDefault_hourly_rate(default_hourly_rate);
//        P.setCost_rate(cost_rate);
//        P.setAvatar_url(avatar_url);
//        P.setIs_contractor(is_contractor);
//        P.setIs_project_manager(is_project_manager);
//        P.setIs_admin(is_admin);
//        P.setHas_access_to_all_future_projects(has_access_to_all_future_projects);
//        P.setCan_create_projects(can_create_projects);
//        P.setCreated_at(created_at);
//        P.setUpdated_at(updated_at);
//        P.setIs_active(is_active);
//        P.setWeekly_capacity(weekly_capacity);
//        P.setTimezone(timezone);
//        P.setCan_see_rates(can_see_rates);
//        P.setCan_create_invoices(can_create_invoices);
//
////        setUserList(P);
//
//    }
//
//    public void setUserList(Person P)
//    {
//        //add the user info to the arrayList
//        this.userList.add( P);
//    }
//
//    public ArrayList getUserList()
//    {
//        return this.userList;
//    }
//
//
//
//
}
//
