package MODELS;

import java.sql.Timestamp;

public class DepartmentNews extends News{
    private int departmentId;


    public DepartmentNews(int id, int userid, String type, String content, Timestamp postdate,int departmentId) {
        super(id, userid, type, content, postdate);
        this.departmentId=departmentId;
    }
}
