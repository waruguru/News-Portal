package DAO;

import java.util.List;

public interface User {
    List<User> getAllUsers();
    void addUser(User user);
    User findUserById(int id);
    void updateUser(User user, String name, String position,String role,int departmentId);
    void clearAllUsers();

    int getDepartmentId();

    String getName();

    String getPosition();

    String getRole();

    void setName(String name);

    void setPosition(String position);

    void setRole(String role);

    void setDepartmentId(int departmentId);

    int getId();

    void setId(int id);
}
