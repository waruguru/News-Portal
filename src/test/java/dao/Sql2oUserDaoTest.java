package dao;

import models.User;
import org.junit.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.Arrays;

import static org.junit.Assert.*;

public class Sql2oUserDaoTest {

    private static  Sql2oUserDao userDao;
    private static Connection con;
    @BeforeClass
    public static void setUp() throws Exception {
        String connectionStr="jdbc:postgresql://localhost:5432/newsportal_test";
        Sql2o sql2o = new Sql2o(connectionStr,"pkminor","password");

        userDao = new Sql2oUserDao(sql2o);
        con = sql2o.open();
        userDao.clearAllUsers(); //start with empty table
    }

    @After
    public void tearDown() throws Exception { userDao.clearAllUsers(); }

    @AfterClass
    public static void shutDown() throws Exception { con.close(); }

    @Test
    public void getAllUsers_ReturnsAllUsers_True() {
        User u1 = setUpUser();
        User u2 = setUpUser();

        userDao.addUser(u1);
        userDao.addUser(u2);

        assertEquals(2,userDao.getAllUsers().size());
        assertTrue(userDao.getAllUsers().containsAll(Arrays.asList(u1,u2)));

    }

    @Test
    public void addUser_AddsUserSetsId_True() {
        User u1 = setUpUser();
        User u2 = setUpUser();

        int u1_id = u1.getId();
        int u2_id = u2.getId();

        userDao.addUser(u1);
        userDao.addUser(u2);

        assertTrue(u1_id!=u1.getId());
        assertTrue(u2_id!=u2.getId());
        assertTrue(u2.getId()>u1.getId());
        assertTrue(1==u2.getId()-u1.getId());
        assertTrue(userDao.getAllUsers().containsAll(Arrays.asList(u1,u2)));
    }

    @Test
    public void findUserById_ReturnsCorrectUser_True() {
        User u1 = setUpUser();
        User u2 = setUpUser();
        userDao.addUser(u1);
        userDao.addUser(u2);

        User foundUser = userDao.findUserById(u1.getId());
        assertEquals(u1,foundUser);

    }

    @Test
    public void updateUser_UpdatesUserNamePositionRoleDepartmentId_True() {
        User u1 = setUpUser();
        User u2 = setUpUser();
        userDao.addUser(u1);
        userDao.addUser(u2);

        String ol_name = u1.getName();
        String ol_position=u1.getPosition();
        String ol_role=u1.getRole();
        int ol_department=u1.getDepartmentId();

        userDao.updateUser(u1,"John doe","Senior","CFO",2);

        assertNotEquals(ol_name, u1.getName());
        assertNotEquals(ol_position, u1.getPosition());
        assertNotEquals(ol_role, u1.getRole());
        assertNotEquals(ol_department, u1.getDepartmentId());
        assertEquals(ol_name, u2.getName());
        assertEquals(ol_position, u2.getPosition());
        assertEquals(ol_role, u2.getRole());
        assertEquals(ol_department, u2.getDepartmentId());
    }

    @Test
    public void clearAllUsers_clearsAllUsers_True() {
        User u1 = setUpUser();
        User u2 = setUpUser();
        userDao.addUser(u1);
        userDao.addUser(u2);
        userDao.clearAllUsers();

        assertEquals(0, userDao.getAllUsers().size());
    }

    private User setUpUser(){return  new User(0,"Ann Lyn","Junior","Admin",1); }
}