package dao;

import models.DepartmentNews;
import models.News;
import org.junit.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.Arrays;
import java.util.Date;
import java.sql.Timestamp;

import static org.junit.Assert.*;

public class Sql2oNewsDaoTest {

    private static  Sql2oNewsDao newsDao;
    private static Connection con;
    @BeforeClass
    public static void setUp() throws Exception {
        String connectionStr="jdbc:postgresql://localhost:5432/newsportal_test";
        Sql2o sql2o = new Sql2o(connectionStr,"pkminor","password");

        newsDao = new Sql2oNewsDao(sql2o);
        con = sql2o.open();
        newsDao.clearAllNews(); //start with empty table
    }

    @After
    public void tearDown() throws Exception { newsDao.clearAllNews(); }

    @AfterClass
    public static void shutDown() throws Exception { con.close(); }

    @Test
    public void getAllNews_ReturnsAllNews_True() {
        News n1 = setupGeneralNews();
        News n2 = setupGeneralNews();

        newsDao.addGeneralNews(n1);
        newsDao.addGeneralNews(n2);

        DepartmentNews dn1 = setupDepartmentNews();
        DepartmentNews dn2 = setupDepartmentNews();

        newsDao.addDepartmentNews(dn1);
        newsDao.addDepartmentNews(dn2);

        assertEquals(4,newsDao.getAllNews().size());
        assertTrue(newsDao.getAllNews().containsAll(Arrays.asList(n1,n2,dn1,dn2)));

    }

    @Test
    public void getGeneralNews_ReturnsGeneralNews_True() {
        News n1 = setupGeneralNews();
        News n2 = setupGeneralNews();

        newsDao.addGeneralNews(n1);
        newsDao.addGeneralNews(n2);

        DepartmentNews dn1 = setupDepartmentNews();
        DepartmentNews dn2 = setupDepartmentNews();

        newsDao.addDepartmentNews(dn1);
        newsDao.addDepartmentNews(dn2);

        int n1_id = n1.getId();
        int n2_id = n2.getId();

        assertEquals(2,newsDao.getGeneralNews().size());
        assertTrue(newsDao.getGeneralNews().containsAll(Arrays.asList(n1,n2)));
    }

    @Test
    public void getDepartmentNews_ReturnsDepartmentNews_True() {
        //bug was due to setting deptId in addDepartmentNews rather than setting id.
        News n1 = setupGeneralNews();
        News n2 = setupGeneralNews();

        newsDao.addGeneralNews(n1);
        newsDao.addGeneralNews(n2);

        DepartmentNews dn1 = setupDepartmentNews();
        DepartmentNews dn2 = setupDepartmentNews();

        newsDao.addDepartmentNews(dn1);
        newsDao.addDepartmentNews(dn2);

        assertEquals(2,newsDao.getDepartmentNews().size());
        assertTrue(newsDao.getDepartmentNews().containsAll(Arrays.asList(dn1,dn2)));

    }

    @Test
    public void addGeneralNews_AddsGeneralNewsSetsId() {
        News n1 = setupGeneralNews();
        News n2 = setupGeneralNews();

        int oln1_id = n1.getId();
        int oln2_id = n2.getId();

        newsDao.addGeneralNews(n1);
        newsDao.addGeneralNews(n2);

        assertNotEquals(oln1_id, n1.getId());
        assertNotEquals(oln2_id, n2.getId());
        assertTrue(n2.getId() > n1.getId());
        assertTrue( 1==n2.getId() - n1.getId());
        assertEquals(2,newsDao.getGeneralNews().size());
        assertTrue(newsDao.getGeneralNews().containsAll(Arrays.asList(n1,n2)));
    }

    @Test
    public void addDepartmentNews_AddsDepartmentNewsSetsId() {
        DepartmentNews dn1 = setupDepartmentNews();
        DepartmentNews dn2 = setupDepartmentNews();

        int oldn1_id = dn1.getId();
        int oldn2_id = dn2.getId();

        newsDao.addDepartmentNews(dn1);
        newsDao.addDepartmentNews(dn2);

        assertNotEquals(oldn1_id, dn1.getId());
        assertNotEquals(oldn2_id, dn2.getId());
        assertTrue(dn2.getId() > dn1.getId());
        assertTrue( 1==dn2.getId() - dn1.getId());
        assertEquals(2,newsDao.getDepartmentNews().size());
        assertTrue(newsDao.getDepartmentNews().containsAll(Arrays.asList(dn1,dn2)));
    }

    @Test
    public void findGeneralNewsById_findsCorrectGeneralNews_True() {
        News n1 = setupGeneralNews();
        News n2 = setupGeneralNews();

        newsDao.addGeneralNews(n1);
        newsDao.addGeneralNews(n2);

        DepartmentNews dn1 = setupDepartmentNews();
        DepartmentNews dn2 = setupDepartmentNews();

        newsDao.addDepartmentNews(dn1);
        newsDao.addDepartmentNews(dn2);

        News foundNews = newsDao.findGeneralNewsById(n1.getId());
        assertEquals(foundNews, n1);

    }

    @Test
    public void findDepartmentNewsById_findsCorrectDepartmentNews_True() {

        News n1 = setupGeneralNews();
        News n2 = setupGeneralNews();

        newsDao.addGeneralNews(n1);
        newsDao.addGeneralNews(n2);

        DepartmentNews dn1 = setupDepartmentNews();
        DepartmentNews dn2 = setupDepartmentNews();

        newsDao.addDepartmentNews(dn1);
        newsDao.addDepartmentNews(dn2);

        DepartmentNews foundDepartmentNews = newsDao.findDepartmentNewsById(dn1.getId());
        assertEquals(foundDepartmentNews, dn1);
    }


    @Test
    public void updateGeneralNews_updatesUserIdContent_True() {
        News n1 = setupGeneralNews();
        News n2 = setupGeneralNews();

        newsDao.addGeneralNews(n1);
        newsDao.addGeneralNews(n2);

        int ol_uid = n1.getUserId();
        String ol_content=n1.getContent();

        int ol_uid2 = n2.getUserId();
        String ol_content2=n2.getContent();

        newsDao.updateGeneralNews(n1,2,"Climate change");

        assertNotEquals(ol_uid,n1.getUserId());
        assertNotEquals(ol_content,n1.getContent());
        assertEquals(ol_uid2,n2.getUserId());
        assertEquals(ol_content2,n2.getContent());
    }

    @Test
    public void updateDepartmentNews_updatesUserIdContentDepartmentId_True() {
        DepartmentNews dn1 = setupDepartmentNews();
        DepartmentNews dn2 = setupDepartmentNews();

        newsDao.addDepartmentNews(dn1);
        newsDao.addDepartmentNews(dn2);

        int ol_uid = dn1.getUserId();
        int ol_did = dn1.getDepartmentId();
        String ol_content=dn1.getContent();

        int ol_uid2 = dn2.getUserId();
        int ol_did2 = dn2.getDepartmentId();
        String ol_content2=dn2.getContent();

        newsDao.updateDepartmentNews(dn1,2,"Climate change",2);

        assertNotEquals(ol_uid,dn1.getUserId());
        assertNotEquals(ol_did,dn1.getDepartmentId());
        assertNotEquals(ol_content,dn1.getContent());

        assertEquals(ol_uid2,dn2.getUserId());
        assertEquals(ol_did2,dn2.getDepartmentId());
        assertEquals(ol_content2,dn2.getContent());
    }

    @Test
    public void clearAllNews_clearsAllNews_True() {
        News n1 = setupGeneralNews();
        News n2 = setupGeneralNews();

        newsDao.addGeneralNews(n1);
        newsDao.addGeneralNews(n2);

        DepartmentNews dn1 = setupDepartmentNews();
        DepartmentNews dn2 = setupDepartmentNews();

        newsDao.addDepartmentNews(dn1);
        newsDao.addDepartmentNews(dn2);

        newsDao.clearAllNews();
        assertEquals(0,newsDao.getAllNews().size());
    }

    @Test
    public void clearGeneralNews_clearsGeneralNewsOnly_True() {
        News n1 = setupGeneralNews();
        News n2 = setupGeneralNews();

        newsDao.addGeneralNews(n1);
        newsDao.addGeneralNews(n2);

        DepartmentNews dn1 = setupDepartmentNews();
        DepartmentNews dn2 = setupDepartmentNews();

        newsDao.addDepartmentNews(dn1);
        newsDao.addDepartmentNews(dn2);

        newsDao.clearGeneralNews();
        assertEquals(0,newsDao.getGeneralNews().size());
        assertEquals(2,newsDao.getDepartmentNews().size());
    }

    @Test
    public void clearDepartmentNews_clearsDepartmentNewsOnly_True() {
        News n1 = setupGeneralNews();
        News n2 = setupGeneralNews();

        newsDao.addGeneralNews(n1);
        newsDao.addGeneralNews(n2);

        DepartmentNews dn1 = setupDepartmentNews();
        DepartmentNews dn2 = setupDepartmentNews();

        newsDao.addDepartmentNews(dn1);
        newsDao.addDepartmentNews(dn2);

        newsDao.clearDepartmentNews();
        assertEquals(2,newsDao.getGeneralNews().size());
        assertEquals(0,newsDao.getDepartmentNews().size());
    }

    private static News setupGeneralNews(){
        return new News(-1,1,Sql2oNewsDao.GENERAL_NEWS,"Space Travel",new Timestamp(new Date().getTime()));
    }

    private static DepartmentNews setupDepartmentNews(){
        return new DepartmentNews(-1,1,Sql2oNewsDao.DEPARTMENT_NEWS,"Space Travel",new Timestamp(new Date().getTime()),1);
    }
}