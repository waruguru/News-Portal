package DAO;

import java.util.List;

public interface User {
    List<User> getAllUsers();
    void addUser(User user);
    User findUserById(int id);
    void updateUser(User user, String name, String position,String role,int departmentId);
    void clearAllUsers();
}
