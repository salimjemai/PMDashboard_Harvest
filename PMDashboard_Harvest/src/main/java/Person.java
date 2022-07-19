//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

public class Person {
    //    private Object users;
    private int id;
    private String first_name;
    private String last_name;
    private Object roles;
    private Object telephone;
    private String email;
    private Object default_hourly_rate;
    private Object cost_rate;
    private String avatar_url;
    private boolean is_contractor;
    private boolean is_project_manager;
    private boolean is_admin;
    private boolean has_access_to_all_future_projects;
    private boolean can_create_projects;
    private Object created_at;
    private Object updated_at;
    private boolean is_active;
    private Object weekly_capacity;
    private Object timezone;
    private boolean can_see_rates;
    private boolean can_create_invoices;

    public Person(int id,String first_name, String last_name,Object roles,Object default_hourly_rate,boolean is_active)
    {
        this.id = this.id;
        this.first_name = this.first_name;
        this.last_name = this.last_name;
        this.roles = this.roles;
        this.default_hourly_rate = this.default_hourly_rate;
        this.is_active = this.is_active;
    }
    public Person() {
//        this.users = users;
        this.first_name = this.first_name;
        this.last_name = this.last_name;
        this.roles = this.roles;
        this.id = this.id;
        this.telephone = this.telephone;
        this.email = this.email;
        this.default_hourly_rate = this.default_hourly_rate;
        this.cost_rate = this.cost_rate;
        this.avatar_url = this.avatar_url;
        this.is_contractor = this.is_contractor;
        this.is_project_manager = this.is_project_manager;
        this.is_admin = this.is_admin;
        this.has_access_to_all_future_projects = this.has_access_to_all_future_projects;
        this.can_create_projects = this.can_create_projects;
        this.created_at = this.created_at;
        this.updated_at = this.updated_at;
        this.is_active = this.is_active;
        this.weekly_capacity = this.weekly_capacity;
        this.timezone = this.timezone;
        this.can_see_rates = this.can_see_rates;
        this.can_create_invoices = this.can_create_invoices;
    }

//    public void setUsers(Object users){ this.users=users; }
//
//    public Object getUsers() { return users;  }

    public int getId() {  return this.id;  }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return this.first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return this.last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Object getTelephone() {
        return this.telephone;
    }

    public void setTelephone(Object telephone) {
        this.telephone = telephone;
    }

    public Object getTimezone() {
        return this.timezone;
    }

    public void setTimezone(Object timezone) {
        this.timezone = timezone;
    }

    public Object getWeekly_capacity() {
        return this.weekly_capacity;
    }

    public void setWeekly_capacity(Object weekly_capacity) {
        this.weekly_capacity = weekly_capacity;
    }

    public boolean getHas_access_to_all_future_projects() {
        return this.has_access_to_all_future_projects;
    }

    public void setHas_access_to_all_future_projects(boolean has_access_to_all_future_projects) {
        this.has_access_to_all_future_projects = has_access_to_all_future_projects;
    }

    public boolean getIs_contractor() {
        return this.is_contractor;
    }

    public void setIs_contractor(boolean is_contractor) {
        this.is_contractor = is_contractor;
    }

    public boolean getIs_admin() {
        return this.is_admin;
    }

    public void setIs_admin(boolean is_admin) {
        this.is_admin = is_admin;
    }

    public boolean getIs_project_manager() {
        return this.is_project_manager;
    }

    public void setIs_project_manager(boolean is_project_manager) {
        this.is_project_manager = is_project_manager;
    }

    public boolean getCan_see_rates() {
        return this.can_see_rates;
    }

    public void setCan_see_rates(boolean can_see_rates) {
        this.can_see_rates = can_see_rates;
    }

    public boolean getCan_create_projects() {
        return this.can_create_projects;
    }

    public void setCan_create_projects(boolean can_create_projects) {
        this.can_create_projects = can_create_projects;
    }

    public boolean getCan_create_invoices() {
        return this.can_create_invoices;
    }

    public void setCan_create_invoices(boolean can_create_invoices) {
        this.can_create_invoices = can_create_invoices;
    }

    public boolean getIs_active() {
        return this.is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public Object getCreated_at() {
        return this.created_at;
    }

    public void setCreated_at(Object created_at) {
        this.created_at = created_at;
    }

    public Object getUpdated_at() {
        return this.updated_at;
    }

    public void setUpdated_at(Object updated_at) {
        this.updated_at = updated_at;
    }

    public Object getDefault_hourly_rate() {
        return this.default_hourly_rate;
    }

    public void setDefault_hourly_rate(Object default_hourly_rate) {
        this.default_hourly_rate = default_hourly_rate;
    }

    public Object getCost_rate() {
        return this.cost_rate;
    }

    public void setCost_rate(Object cost_rate) {
        this.cost_rate = cost_rate;
    }

    public Object getRoles() {
        return this.roles;
    }

    public void setRoles(Object roles) {
        this.roles = roles;
    }

    public String getAvatar_url() {
        return this.avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String toString() {
        return String.format("%s  %12s  %10s  %25s  %16s  %16s   %33s  %10s  %30s  %59s  %19s  %10s  %25s  %22s  %18s  %22s  %18s  %18s  %18s  %25s  %12s%n%s%n%4s " +
                        " %14s  %12s  %10s  %18s  %19s  %12s  %18s  %80s  %10s  %15s  %15s  %15s  %25s  %32s  %10s  %12s  %15s  %33s  %12s  %12s%n",
                "First Name", "Last Name", "Roles", "ID", "Phone Number", "Email", "Default Hourly Rate", "Cost Rate", "Avatar URL", "Contractor",
                "Project Manager", "Admin", "Access to all features", "Can Create Project", "Created at", "Updated at", "Active ", "Weekly Capacity",
                "Timezone", "Can See Rate", "Can Create Invoice",
                "-------------------------------------------------------------------------------------------------------------------------------------" +
                        "------------------------------------------------------------------------------------------------------------------------------" +
                        "-------------------------------------------------------------------------------------------------------------------------------" +
                        "-------------------------------------------------------------------------",
                this.first_name, this.last_name, this.roles, this.id, this.telephone, this.email, this.default_hourly_rate,
                this.cost_rate, this.avatar_url, this.is_contractor, this.is_project_manager, this.is_admin,
                this.has_access_to_all_future_projects, this.can_create_projects, this.created_at, this.updated_at,
                this.is_active, this.weekly_capacity, this.timezone, this.can_see_rates, this.can_create_invoices);
    }
}
