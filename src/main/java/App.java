import DAO.Sql2oDepartmentDao;
import DAO.Sql2oNewsDao;
import DAO.Sql2oUserDao;
import DAO.User;
import MODELS.Department;
import MODELS.DepartmentNews;
import MODELS.News;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.net.URI;
import java.net.URISyntaxException;

import static spark.Spark.post;

public class App {
    private static Sql2oNewsDao newsDao;
    private static Sql2oDepartmentDao dptDao;
    private static Sql2oUserDao userDao;
    private static Sql2o sql2o;
    private static URI dbUri;
    private static Logger logger = LoggerFactory.getLogger(App.class);
    private static Gson gson = new Gson();
    private  static Connection con;

    public static void main(String[] args) {

        ProcessBuilder process = new ProcessBuilder();

        Integer port = (process.environment().get("PORT") != null) ?
                Integer.parseInt(process.environment().get("PORT")):7654;
        port(port);

        String connectionStr="jdbc:postgresql://localhost:5432/newsportal";

        try {
            if (System.getenv("DATABASE_URL") == null) {
                dbUri = new URI("postgres://localhost:5432/wildlife_tracker");
                sql2o = new Sql2o(connectionStr,"pkminor","password");

            } else {

                dbUri = new URI(System.getenv("DATABASE_URL"));
                int dbport = dbUri.getPort();
                String host = dbUri.getHost();
                String path = dbUri.getPath();
                String username = (dbUri.getUserInfo() == null) ? null : dbUri.getUserInfo().split(":")[0];
                String password = (dbUri.getUserInfo() == null) ? null : dbUri.getUserInfo().split(":")[1];
                sql2o = new Sql2o("jdbc:postgresql://" + host + ":" + dbport + path, username, password);
            }

        } catch (URISyntaxException e ) {
            logger.error("Unable to connect to database.");
        }

        con = sql2o.open();

        newsDao = new Sql2oNewsDao(sql2o);
        dptDao = new Sql2oDepartmentDao(sql2o);
        userDao = new Sql2oUserDao(sql2o);

        staticFileLocation("/public");

        get("/",(req,res)->{
            res.redirect("index.html"); return null;
        });

        get("/users", (req,res)->{
            return  gson.toJson(userDao.getAllUsers());
        });
        get("/departments", (req,res)->{
            return  gson.toJson(dptDao.getDepartmentWithUserCount());
        });
        get("/users/:id",(req,res)->{
            int user_id = Integer.parseInt(req.params("id"));
            return gson.toJson(userDao.findUserById(user_id));
        });
        get("/departments/:id",(req,res)->{
            int dpt_id = Integer.parseInt(req.params("id"));
            return gson.toJson(dptDao.findDepartmentById(dpt_id));
        });
        get("/departments/:id/users",(req,res)->{
            int dpt_id = Integer.parseInt(req.params("id"));
            return gson.toJson(dptDao.getDepartmentUsersById(dpt_id));
        });
        get("/departments/:id/news",(req,res)->{
            int dpt_id = Integer.parseInt(req.params("id"));
            return gson.toJson(dptDao.getDepartmentNewsById(dpt_id));
        });
        get("/news", (req,res)->{
            return  gson.toJson(newsDao.getAllNews());
        });
        get("/news/general", (req,res)->{
            return  gson.toJson(newsDao.getGeneralNews());
        });
        get("/news/department", (req,res)->{
            return  gson.toJson(newsDao.getDepartmentNews());
        });

        post("/Departments/new", "application/json", (req,res)->{
            Department dpt = gson.fromJson(req.body(),Department.class);

            dptDao.addDepartment(dpt);
            res.status(201);
            res.type("application/json");
            res.redirect("/departments");
            return null;//gson.toJson(dpt);
        });
        post("/Users/new", "application/json", (req,res)->{
            User user = gson.fromJson(req.body(), User.class);

            userDao.addUser(user);
            res.status(201);
            res.type("application/json");

            res.redirect("/users");
            return null; //gson.toJson(user);
        });
        post("/News/new", "application/json", (req,res)->{
            News news = gson.fromJson(req.body(), News.class);

            newsDao.addGeneralNews(news);
            res.status(201);
            res.type("application/json");
            res.redirect("/news/general");
            return null; //gson.toJson(news);
        });
        post("/DepartmentNews/new", "application/json", (req,res)->{
            DepartmentNews dnews = gson.fromJson(req.body(), DepartmentNews.class);

            newsDao.addDepartmentNews(dnews);
            res.status(201);
            res.type("application/json");

            res.redirect("/news/department");
            return null; //gson.toJson(dnews);
        });

        //FILTERS
        after((req, res) ->{
            //res.type("application/json");
        });

    }

    private static void port(Integer port) {
    }

}
