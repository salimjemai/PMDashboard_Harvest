public class MainUserDataResponse
{
    private Object nextPage;
    private int per_page ;
    private Object link;
    private int total_pages;
    private Object previous_page;
    private int page;
    private Object users;
    private Object total_entries;

    public MainUserDataResponse()
    {
        this.nextPage = nextPage;
        this.per_page = per_page;
        this.link = link;
        this.total_pages = total_pages;
        this.previous_page = previous_page;
        this.page = page;
        this.users = users;
        this.total_entries = total_entries;
    }

    public Object getNextPage() {  return nextPage;  }

    public void setNextPage(Object nextPage) { this.nextPage = nextPage; }

    public int getPer_page() { return per_page; }

    public void setPer_page(int per_page) { this.per_page = per_page; }

    public Object getLink() { return link; }

    public void setLink(Object link) { this.link = link; }

    public int getTotal_pages() { return total_pages;}

    public void setTotal_pages(int total_pages) { this.total_pages = total_pages; }

    public Object getPrevious_page() { return previous_page; }

    public void setPrevious_page(Object previous_page) { this.previous_page = previous_page;}

    public int getPage() { return page; }

    public void setPage(int page) { this.page = page; }

    public Object getUsers() { return users;}

    public void setUsers(Object users) { this.users = users;}

    public Object getTotal_entries() { return total_entries; }

    public void setTotal_entries(Object total_entries) { this.total_entries = total_entries;  }

    @Override
    public String toString() {
        return "MainUserDataResponse{" +
                "nextPage=" + nextPage +
                ", per_page=" + per_page +
                ", link=" + link +
                ", total_pages=" + total_pages +
                ", previous_page=" + previous_page +
                ", page=" + page +
                ", users=" + users +
                ", total_entries ="+total_entries+
                '}';
    }
}
