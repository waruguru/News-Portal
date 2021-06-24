package MODELS;

import java.sql.Timestamp;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        News news = (News) o;
        return id == news.id && userid == news.userid && Objects.equals(type, news.type) && Objects.equals(content, news.content) && Objects.equals(postdate, news.postdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userid, type, content, postdate);
    }

    public int getUserId() {
        return userid;
    }

    public void setUserId(int userId) {
    }
}
