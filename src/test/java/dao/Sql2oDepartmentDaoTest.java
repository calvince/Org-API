package dao;

import models.Department;
import models.Employees;
import org.junit.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.Arrays;

import static org.junit.Assert.*;

public class Sql2oDepartmentDaoTest {
    private static Connection conn;
    private static Sql2oDepartmentDao departmentDao;
    private static Sql2oEmployeesDao employeesDao;

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
    public static void shutdown() throws Exception{
        conn.close();
    }

    @Test
    public void addDepo() {
        Department department = setupDep();
        departmentDao.add(department);
        int departId = department.getId();
        assertEquals(departId,department.getId());
    }

    @Test
    public void findById() {
        Department department = setupDep();
        departmentDao.add(department);
        Department department1 = departmentDao.findById(department.getId());
        assertEquals(department,department1);
    }

    @Test
    public void getAll() {
        Department department = setupDep();
        departmentDao.add(department);
        Department department1 = setupDep();
        departmentDao.add(department1);
        assertTrue(departmentDao.getAll().contains(department));
        assertTrue(departmentDao.getAll().contains(department1));
    }

    @Test
    public void getAllNews() {

    }

    @Test
    public void  addDepartmentToEmployees() {
        Department department = setupDep();
        departmentDao.add(department);
        Employees employees = new Employees("Watchman", "CEO", "oversight","24 NE Western");
        employeesDao.add(employees);
        Employees employees1 = new Employees("Watchman", "CEO", "oversight","24 NE Western");
        employeesDao.add(employees1);
        departmentDao.addDepartmentToEmployees(department,employees);
        departmentDao.addDepartmentToEmployees(department,employees1);
        Employees[] addDeptToEmployee = {employees,employees1};
        assertEquals(Arrays.asList(addDeptToEmployee),departmentDao.getAllEmployeesForADepartment(department.getId()));
    }

    @Test
    public void clearAll() {
        Department department = setupDep();
        departmentDao.add(department);
        departmentDao.clearAll();
        assertEquals(0,departmentDao.getAll().size());
    }

    public Department setupDep(){
        return new Department("accounts", "Check balances", 20);
    }
}