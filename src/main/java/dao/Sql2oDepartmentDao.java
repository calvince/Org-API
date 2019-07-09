package dao;

import models.Department;
import models.Employees;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class Sql2oDepartmentDao implements DepartmentDao {
    private final Sql2o sql2o;
    public  Sql2oDepartmentDao(Sql2o sql2o){
        this.sql2o = sql2o;
    }
    @Override
    public void add(Department department) {
        String sql = "INSERT INTO departments (department, description, number_employees) VALUES (:department, :description, :number_employees)";
        try(Connection conn = sql2o.open()){
            int id = (int) conn.createQuery(sql, true)
                    .bind(department)
                    .executeUpdate()
                    .getKey();
            department.setId(id);
        }catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public Department findById(int id) {
        String sql = "SELECT * FROM departments WHERE id=:id";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Department.class);
        }
    }

    @Override
    public List<Department> getAll() {
        String sql = "SELECT * FROM departments";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(sql).executeAndFetch(Department.class);
        }
    }

    @Override
    public List<News> getAllNews(int departmentId) {
        String sql = "SELECT * FROM news WHERE departmentId =:departmentId";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(sql).addParameter("departmentId", departmentId)
                    .throwOnMappingFailure(false)
                    .executeAndFetch(News.class);
        }
    }

    @Override
    public void addDepartmentToEmployees(Department department, Employees employees) {
        String sql = "INSERT INTO departments_users(department_id, user_id) VALUES (:department_id, :user_id)";
        try(Connection conn = sql2o.open()){
            conn.createQuery(sql)
                    .addParameter("department_id", department.getId())
                    .addParameter("user_id", employees.getId())
                    .executeUpdate();
        }catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public List<Employees> getAllEmployeesForADepartment(int department_id) {
        ArrayList<Employees> allUsers = new ArrayList<>();
        String matchToGetTheUserIds = "SELECT employee_id FROM departments_users WHERE department_id =:department_id";
        try(Connection conn = sql2o.open()){
            List<Integer> allEmployeesIds = conn.createQuery(matchToGetTheUserIds)
                    .addParameter("department_id", department_id)
                    .executeAndFetch(Integer.class);
            for(Integer employee_id: allEmployeesIds){
                String getFromUsers = "SELECT * FROM employees WHERE id=:employee_id";
                allUsers.add(conn.createQuery(getFromUsers)
                        .addParameter("employee_id", employee_id)
                        .executeAndFetchFirst(Employees.class));
            }
        }catch (Sql2oException ex){
            System.out.println(ex);
        }
        return allUsers;
    }

    @Override
    public void clearAll() {
        String sql = "DELETE from departments";
        try(Connection conn = sql2o.open()){
            conn.createQuery(sql).executeUpdate();
        }catch (Sql2oException ex){
            System.out.println(ex);
        }
    }
}
