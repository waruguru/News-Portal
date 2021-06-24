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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getPostdate() {
        return postdate;
    }

    public void setPostdate(Timestamp postdate) {
        this.postdate = postdate;
    }

}
