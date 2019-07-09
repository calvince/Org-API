package models;

public class Employees {
    private String name;
    private String position;
    private String role;
    private String address;
    private int id;

    public Employees(String name,String position,String role,String address){

        this.name = name;
        this.position = position;
        this.role = role;
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employees)) return false;

        Employees employees = (Employees) o;

        if (getId() != employees.getId()) return false;
        if (!getName().equals(employees.getName())) return false;
        if (!getPosition().equals(employees.getPosition())) return false;
        if (!getRole().equals(employees.getRole())) return false;
        return getAddress().equals(employees.getAddress());
    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + getPosition().hashCode();
        result = 31 * result + getRole().hashCode();
        result = 31 * result + getAddress().hashCode();
        result = 31 * result + getId();
        return result;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public String getRole() {
        return role;
    }

    public String getAddress() {
        return address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
