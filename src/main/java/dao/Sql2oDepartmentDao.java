package dao;

import models.Department;
import models.Employees;
import models.News;
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
        String sql = "INSERT INTO departments (department, description,  number_employees) VALUES (:department, :description, :number_employees)";
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
        String sql = "SELECT * FROM news WHERE departmentId=:departmentId";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(sql).addParameter("departmentId", departmentId)
                    .throwOnMappingFailure(false)
                    .executeAndFetch(News.class);
        }
    }

        //many to many relationships
    @Override
    public void addDepartmentToEmployees(Department department, Employees employees) {
        String sql = "INSERT INTO departments_employees(departmentId, employeeId) VALUES (:departmentId, :employeeId)";
        try(Connection conn = sql2o.open()){
            conn.createQuery(sql)
                    .addParameter("departmentId", department.getId())
                    .addParameter("employeeId", employees.getId())
                    .executeUpdate();
        }catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public List<Employees> getAllEmployeesForADepartment(int departmentId) {
        ArrayList<Employees> allUsers = new ArrayList<>();
        String matchToGetTheUserIds = "SELECT employeeId FROM departments_employees WHERE departmentId =:departmentId";
        try(Connection conn = sql2o.open()){
            List<Integer> allEmployeesIds = conn.createQuery(matchToGetTheUserIds)
                    .addParameter("departmentId", departmentId)
                    .executeAndFetch(Integer.class);
            for(Integer employeeId: allEmployeesIds){
                String getFromUsers = "SELECT * FROM employees WHERE id=:employeeId";
                allUsers.add(conn.createQuery(getFromUsers)
                        .addParameter("employeeId", employeeId)
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
