package dao;

import models.News;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oNewsDao implements NewsDao {

    private final Sql2o sql2o;
    public  Sql2oNewsDao(Sql2o sql2o){
        this.sql2o = sql2o;
    }
    @Override
    public void add(News news) {
        String sql ="INSERT INTO news(title, content,departmentId) VALUES (:title, :content,:departmentId)";
        try(Connection conn = sql2o.open()){
            int id = (int) conn.createQuery(sql, true)
                    .bind(news)
                    .executeUpdate()
                    .getKey();
           news.setId(id);
        }catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public News findById(int id) {
        String sql = "SELECT * FROM news WHERE id=:id";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(sql)
                    .addParameter("id", id)
                    .throwOnMappingFailure(false)
                    .executeAndFetchFirst(News.class);
        }
    }

    @Override
    public void updateNews(int id, String title, String content,int departmentId) {
        String sql = "UPDATE news SET (title, content,departmentId) = (:title, :content,:departmentId) WHERE id=:id";
        try(Connection conn = sql2o.open()){
            conn.createQuery(sql)
                    .addParameter("title", title)
                    .addParameter("content", content)
                    .addParameter("departmentId",departmentId)
                    .addParameter("id", id)
                    .executeUpdate();
        }catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public List<News> getAllNews(int departmentId) {
        String sql = "SELECT * FROM news WHERE departmentId =:departmentId";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(sql).addParameter("departmentId", departmentId)
                    .throwOnMappingFailure(false)
                    .executeAndFetch(News.class);
        }
    }

    @Override
    public void clearAll() {
        String sql = "DELETE FROM news";
        try (Connection conn = sql2o.open()){
            conn.createQuery(sql)
                    .executeUpdate();
        }catch (Sql2oException ex){
            System.out.println(ex);
        }

    }
}
