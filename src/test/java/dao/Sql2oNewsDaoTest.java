package dao;

import models.Department;
import models.News;
import org.junit.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class Sql2oNewsDaoTest {
    private static Connection conn;
    private static Sql2oNewsDao newsDao;
    private static Sql2oDepartmentDao departmentDao;

    @BeforeClass
    public  static void setUp() throws Exception {
        String connectingString = "jdbc:postgresql://localhost:5432/newsapi_test";
        Sql2o sql2o = new Sql2o(connectingString,"calvo-linus","Somoca1421@.");
        newsDao = new Sql2oNewsDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        newsDao.clearAll();
    }
    @AfterClass
    public static void shutDown() throws Exception{
        conn.close();
    }

    @Test
    public void addNews() throws Exception{
        News news1 = setupNews();
        newsDao.add(news1);
        int newId = news1.getId();
        assertEquals(newId,news1.getId());
    }


    public News setupNews(){
        return new News("Business","Kenya lost 400 billions",4);
    }
}