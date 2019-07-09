package models;

import org.junit.Test;

import static org.junit.Assert.*;

public class NewsTest {
    @Test
    public void NewsInstantiatesCorrectly() throws Exception{
        News news = setupNews();
        assertTrue(news instanceof News);
    }
    @Test
    public void NewsInstantiatesCorrectlyWith_Values() throws Exception{
        News news = setupNews();
        assertEquals("Football",news.getTitle());
        assertEquals("Nick Mwendwa sacked",news.getContent());
        assertEquals(3,news.getDepartmentId());
    }
    @Test
    public void setId() throws Exception{
        News news = setupNews();
        news.setId(7);
        assertNotEquals(3,news.getId());
    }
    //helper method
    public News setupNews(){
        return new News("Football","Nick Mwendwa sacked",3);
    }

}