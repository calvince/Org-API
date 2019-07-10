package dao;

import models.News;

import java.util.List;

public interface NewsDao {
    //Add new for a department
    void add(News news);

    //Find news by the id
    News findById(int id);

    //Update department news
    void updateNews(int id, String title, String content,int departmentId);

    //Get all department news by department id
    List<News> getAllNews();

    void clearAll();
}
