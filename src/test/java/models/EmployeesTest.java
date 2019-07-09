package models;

import org.junit.Test;

import static org.junit.Assert.*;

public class EmployeesTest {
    @Test
    public void EmployeesInstantiatesCorrectly_true() throws Exception{
        Employees employees = setupEmpl();
        assertTrue(employees instanceof Employees);
    }
    @Test
    public void EmployeesInstantiatesCorrectlyWith_Values()throws Exception{
        Employees employees= setupEmpl();
        assertEquals("Watchman",employees.getName());
        assertEquals("CEO",employees.getPosition());
        assertEquals("oversight",employees.getRole());
        assertEquals("24 NE Western",employees.getAddress());
    }
    @Test
    public void setId()throws Exception{
        Employees employees = setupEmpl();
        employees.setId(5);
        assertNotEquals(2,employees.getId());

    }


    //helper method
    public Employees setupEmpl(){
        return new Employees("Watchman", "CEO", "oversight","24 NE Western");
    }

}