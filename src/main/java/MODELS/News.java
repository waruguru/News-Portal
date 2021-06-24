package MODELS;

import java.sql.Timestamp;

public class News {
    private int id;
    private int userid;
    private String type;
    private String content;
    private Timestamp postdate;

    public News(int id, int userid, String type, String content, Timestamp postdate) {
        this.id = id;
        this.userid = userid;
        this.type = type;
        this.content = content;
        this.postdate = postdate;
    }

}
