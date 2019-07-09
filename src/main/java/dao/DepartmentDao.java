package dao;

import models.Department;
import models.Employees;

import java.util.List;

public interface DepartmentDao {
    void add(Department department);

    //Find by id
    Department findById(int id);

    //Get all departments
    List<Department> getAll();

    //Get all news for a department
    List<News> getAllNews(int departmentId);

    //Add a department to a user
    void addDepartmentToEmployees(Department department, Employees employees);

    //Get all users in a particular department
    List<Employees> getAllEmployeesForADepartment(int employee_id);

    //delete
    void clearAll();
}
