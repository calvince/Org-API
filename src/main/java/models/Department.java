package models;

public class Department {
    private String department;
    private String description;
    private int number_employees;
    private int id;


    public Department(String department, String description, int number_employees){

        this.department= department;
        this.description = description;
        this.number_employees= number_employees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Department)) return false;

        Department that = (Department) o;

        if (getNumber_employees() != that.getNumber_employees()) return false;
        if (getId() != that.getId()) return false;
        if (!getDepartment().equals(that.getDepartment())) return false;
        return getDescription().equals(that.getDescription());
    }

    @Override
    public int hashCode() {
        int result = getDepartment().hashCode();
        result = 31 * result + getDescription().hashCode();
        result = 31 * result + getNumber_employees();
        result = 31 * result + getId();
        return result;
    }

    public String getDepartment() {
        return department;
    }

    public String getDescription() {
        return description;
    }

    public int getNumber_employees() {
        return number_employees;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



}
