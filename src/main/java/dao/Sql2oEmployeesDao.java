package dao;

import models.Department;
import models.Employees;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class Sql2oEmployeesDao implements EmployeesDao {

    private final Sql2o sql2o;

    public Sql2oEmployeesDao(Sql2o sql2o){
        this.sql2o = sql2o;
    }
    @Override
    public void add(Employees employees) {
        String sql = "INSERT INTO employees(name, position, role,address) VALUES (:name, :position, :role,:address)";
        try(Connection conn = sql2o.open()){
            int id = (int) conn.createQuery(sql, true)
                    .bind(employees)
                    .executeUpdate()
                    .getKey();
            employees.setId(id);
        }catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public Employees findById(int id) {
        String sql = "SELECT * FROM employees WHERE id = :id";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(sql).addParameter("id", id).executeAndFetchFirst(Employees.class);
        }
    }

    @Override
    public List<Employees> getAllEmployees() {
        String sql = "SELECT * FROM employees";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(sql).executeAndFetch(Employees.class);
        }
    }

    @Override
    public void addEmployeeToDepartment(Employees employees, Department department) {
        String sql = "INSERT INTO departments_employees(departmentId, employeeId) VALUES (:departmentId, :employeeId)";
        try(Connection conn = sql2o.open()){
            conn.createQuery(sql)
                    .addParameter("departmentId", department.getId())
                    .addParameter("employeeId", employees.getId())
                    .throwOnMappingFailure(false)
                    .executeUpdate();
        }catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public List<Department> getAllDepartmentsForEmployee(int employeeId) {
        ArrayList<Department> allDepartments = new ArrayList<>();
        String matchToGetDepartmentId = "SELECT departmentId FROM departments_employees WHERE employeeId =:employeeId";
        try(Connection conn = sql2o.open()){
            List<Integer> allDepartmentIds = conn.createQuery(matchToGetDepartmentId).addParameter("employeeId", employeeId)
                    .executeAndFetch(Integer.class);
            for(Integer departmentId : allDepartmentIds){
                String getFromDepartments = "SELECT * FROM departments WHERE id=:departmentId";
                allDepartments.add(conn.createQuery(getFromDepartments).addParameter("departmentId", departmentId).executeAndFetchFirst(Department.class));
            }
        }catch (Sql2oException ex){
            System.out.println(ex);
        }
        return allDepartments;
    }

    @Override
    public void clearAll() {
        String sql = "DELETE from employees";
        try(Connection conn = sql2o.open()){
            conn.createQuery(sql).executeUpdate();
        }catch (Sql2oException ex){
            System.out.println(ex);
        }
    }
}

