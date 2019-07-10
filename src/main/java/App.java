import com.google.gson.Gson;
import dao.Sql2oDepartmentDao;
import dao.Sql2oEmployeesDao;
import dao.Sql2oNewsDao;
import exceptions.ApiException;
import models.Department;
import models.Employees;
import models.News;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import static spark.Spark.*;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;


import java.util.HashMap;
import java.util.Map;

import static spark.Spark.port;

public class App {
    public static void main(String[]args ){
        staticFileLocation("/public");
        ProcessBuilder processBuilder = new ProcessBuilder();
        Integer port;
        if (processBuilder.environment().get("PORT")!= null){
            port = Integer.parseInt(processBuilder.environment().get("PORT"));
        }else {
            port = 4567;
        }
        port(port);
        Sql2oDepartmentDao departmentDao;
        Sql2oNewsDao newsDao;
        Sql2oEmployeesDao employeesDao;
        Connection conn;
        Gson gson = new Gson();

        String connectingString = "jdbc:postgresql://localhost:5432/newsapi_test";
        Sql2o sql2o = new Sql2o(connectingString,"calvo-linus","Somoca1421@.");
        departmentDao = new Sql2oDepartmentDao(sql2o);
        newsDao = new Sql2oNewsDao(sql2o);
        employeesDao = new Sql2oEmployeesDao(sql2o);
        conn = sql2o.open();

        //get: get all
        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "news-detail.hbs");
        }, new HandlebarsTemplateEngine());

        //get:department new
        get("/new/department",(request, response) -> {
            Map<String,Object> model = new HashMap<>();
            return new ModelAndView(model,"department-form.hbs");
        },new HandlebarsTemplateEngine());

        post("/new/department", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            String department = req.queryParams("department");
            String description = req.queryParams("description");
            int number_employees = Integer.parseInt(req.queryParams("number_employees"));
            Department department1 = new Department(department,description,number_employees);
            departmentDao.add(department1);
            model.put("departments",departmentDao.getAll());
            return new ModelAndView(model,"department-detail.hbs");
        },new HandlebarsTemplateEngine());

        //get:post all users
        get("/new/employees",(request, response) -> {
            Map<String,Object> model = new HashMap<>();
            return new ModelAndView(model,"employees-form.hbs");
        },new HandlebarsTemplateEngine());

        post("/new/employees", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            String name = req.queryParams("name");
            String position = req.queryParams("position");
            String role = req.queryParams("role");
            String address = req.queryParams("address");
            Employees employees = new Employees(name,position,role,address);
            employeesDao.add(employees);
            model.put("employees",employeesDao.getAllEmployees());
            return new ModelAndView(model,"employees-detail.hbs");
        },new HandlebarsTemplateEngine());

        //get:new all users
        get("/new/news",(request, response) -> {
            Map<String,Object> model = new HashMap<>();
            model.put("deps", departmentDao.getAll());
            return new ModelAndView(model,"news-form.hbs");
        },new HandlebarsTemplateEngine());

        post("/new/news", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            String title = req.queryParams("title");
            String content = req.queryParams("content");
           int departmentId = Integer.parseInt(req.queryParams("departmentId"));
           News news = new News(title,content, departmentId);
            newsDao.add(news);
            model.put("news",newsDao.getAllNews());
            return new ModelAndView(model,"news-detail.hbs");
        },new HandlebarsTemplateEngine());


        //gson ones

        //post: Add news
        post("/news/new","application/json",(request, response) -> {
            News news = gson.fromJson(request.body(),News.class);
            newsDao.add(news);
            response.type("application/json");
            response.status(201);
            return gson.toJson(news);
        });

        //get:View All news
        get("/news","application/json",(request, response) -> {
            int departmentId = Integer.parseInt(request.params("departmentId"));
            return gson.toJson(newsDao.getAllNews());
        });

        //post:Add department
        post("/department/new","application/json",(request, response) -> {
            Department department = gson.fromJson(request.body(),Department.class);
            departmentDao.add(department);

            response.type("application/json");
            response.status(201);
            return gson.toJson(departmentDao.getAll());
        });

        //get:view all departments
        get("/department","application/json",(request, response) -> {
            return gson.toJson(departmentDao.getAll());
        });

        //post: Add news to department
        post("/department/:id/news/new","application/json",(request, response) -> {
            int id = Integer.parseInt(request.params("id"));
            News news = gson.fromJson(request.body(),News.class);
            news.setId(id);
            newsDao.add(news);
            response.type("application/json");
            response.status(201);
            return gson.toJson(news);
        });

        //get:View all news for department
        get("/departments/:id/depNews", "application/json", (request, response) -> {
            int id = Integer.parseInt(request.params("id"));
            return gson.toJson(newsDao.getAllNews());
        });

        //post:add a user(employees)
        post("/employees/new", "application/json", (request, response) -> {
            Employees employees = gson.fromJson(request.body(), Employees.class);
            employeesDao.add(employees);
            response.type("application/json");
            response.status(201);
            return gson.toJson(employees);
        });

        //Get: View all users
        get("/users", "application/json", (request, response) -> {
            return gson.toJson(employeesDao.getAllEmployees());
        });

        //post:Add a department to a user
        post("employees/employeeId/department/:departmentId","application/json",(request, response) -> {
            int emplyeeId = Integer.parseInt(request.params("employeeId"));
            int departmentId = Integer.parseInt(request.params("departmentId"));
            Employees employeesFound = employeesDao.findById(emplyeeId);
            Department departmentfound = departmentDao.findById(departmentId);

            if (departmentfound != null && employeesFound != null){
                departmentDao.addDepartmentToEmployees(departmentfound,employeesFound);
                response.type("application/json");
                response.status(201);
                return gson.toJson("Employees and Department have been associated");
            }
            else {
                throw new ApiException(404, String.format("Employee or Department does not exist"));
            }
        });

        //get:View all departments a user belongs to

        get("/employees/:employeeId/department","application/json",(request, response) -> {
            int employeeId = Integer.parseInt(request.params("employeeId"));
            Employees employees = employeesDao.findById(employeeId);

            if (employees == null){
                throw new Exception("No Employee with that id");
            }else if(employeesDao.getAllDepartmentsForEmployee(employeeId).size() == 0){
                return "{\"message\":\"Employee not associated with any department\"}";
            }else {
                return gson.toJson(employeesDao.getAllDepartmentsForEmployee(employeeId));
            }
        });

        //filter for response
//        after((request, response) -> {
//            response.type("application/json");
//        });


    }
}
