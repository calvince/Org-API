package dao;

import models.Department;
import models.Employees;
import org.junit.*;
import org.sql2o.Sql2o;
import org.sql2o.Connection;
import java.util.Arrays;

import static org.junit.Assert.*;

public class Sql2oEmployeesDaoTest {
    private static Sql2oEmployeesDao employeesDao;
    private  static Connection conn;
    private static Sql2oDepartmentDao departmentDao;

    @BeforeClass
    public static void setUp() throws Exception {
        String connectingString= "jdbc:postgresql://localhost:5432/newsapi_test";
        Sql2o sql2o = new Sql2o(connectingString,"calvo-linus","Somoca1421@.");
        employeesDao = new Sql2oEmployeesDao(sql2o);
        departmentDao = new Sql2oDepartmentDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        employeesDao.clearAll();
        departmentDao.clearAll();
    }
    @AfterClass
    public static void shutDown() throws Exception{
        conn.close();
    }

    @Test
    public void addToDBandSetId() {
        Employees employees = setupEmp();
        employeesDao.add(employees);
        int empId= employees.getId();
        assertEquals(employees.getId(),empId);

    }

    @Test
    public void findById() {
        Employees employees = setupEmp();
        employeesDao.add(employees);
        Employees employees1 = employeesDao.findById(employees.getId());
        assertEquals(employees1,employees);
    }

    @Test
    public void getAllEmployees(){
        Employees employees = setupEmp();
        employeesDao.add(employees);
        Employees employees1 = setupEmp();
        employeesDao.add(employees1);
        assertTrue(employeesDao.getAllEmployees().contains(employees));
        assertTrue(employeesDao.getAllEmployees().contains(employees1));
    }

    @Test
    public void addEmployeeToDepartment() {
        Employees employees = setupEmp();
        employeesDao.add(employees);
        Department department = new Department("accounts", "Check balances", 20);
        departmentDao.add(department);
        Department department1 = new Department("accounts", "Check balances", 20);
        departmentDao.add(department1);
        employeesDao.addEmployeeToDepartment(employees,department);
        employeesDao.addEmployeeToDepartment(employees,department1);
        Department[] addedEmToDepartment = {department,department1};
        assertEquals(Arrays.asList(addedEmToDepartment),employeesDao.getAllDepartmentsForEmployee(employees.getId()));
    }

    @Test
    public void clearAll() {
        Employees employees = setupEmp();
        employeesDao.add(employees);
        employeesDao.clearAll();
        assertEquals(0,employeesDao.getAllEmployees().size());
    }


    //helper method
    public Employees setupEmp(){
        return new Employees("Watchman", "CEO", "oversight","24 NE Western");
    }
}