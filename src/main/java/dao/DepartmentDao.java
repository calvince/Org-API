package dao;

import models.Department;
import models.Employees;
import models.News;

import java.util.List;

public interface DepartmentDao {
    void add(Department department);

    //Find by id
    Department findById(int id);

    //Get all departments
    List<Department> getAll();

    //Get all news for a department
    List<News> getAllNews(int departmentId);

    //many to many relationship
    //Add a department to a user
    void addDepartmentToEmployees(Department department, Employees employees);
    //many to many relationship
    //Get all users in a particular department
    List<Employees> getAllEmployeesForADepartment(int employee_id);

    //delete
    //deleteById(int id);
    void clearAll();
}
