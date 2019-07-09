package models;

public class News {
    private String title;
    private String content;
    private int departmentId;
    private int id;

    public News(String title,String content,int departmentId){

        this.title= title;
        this.content = content;
        this.departmentId = departmentId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
