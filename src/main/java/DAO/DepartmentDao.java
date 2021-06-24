package DAO;

import MODELS.Department;

import java.util.List;

public interface DepartmentDao {
    List<Department> getAllDepartments();
    List<User> getDepartmentUsersById(int id);
    List<DepartmentNews> getDepartmentNewsById(int id);
    void addDepartment(Department department);
    Department findDepartmentById(int id);
    void updateDepartment(Department department, String name, String description);
    void clearAllDepartments();
}
