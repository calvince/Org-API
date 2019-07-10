package dao;

import models.Department;
import models.Employees;

import java.util.List;

public interface EmployeesDao {
    void add(Employees employees);

    //Find a user by the id
    Employees findById(int id);

    //Get all users
    List<Employees> getAllEmployees();

    //Add a user to a department
    void addEmployeeToDepartment(Employees employees, Department department);

    //Get all departments Employee belongs to
    List<Department> getAllDepartmentsForEmployee(int employee_id);
    void clearAll();
}
