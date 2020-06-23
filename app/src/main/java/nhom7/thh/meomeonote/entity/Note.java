package nhom7.thh.meomeonote.entity;

import java.io.Serializable;

public class Note implements Serializable {
    private int id;
    private String password;
    private String title;
    private String content;
    private String created;
    private String last_modified;
    private String timer;
    private String catName;
    private int status;
    private int user_id;

    public Note(int id, String password,
                String title, String content, String created,
                String last_modified, String timer, int status, int user_id, String catName) {

        this.id = id;
        this.password = password;
        this.title = title;
        this.content = content;
        this.created = created;
        this.last_modified = last_modified;
        this.timer = timer;
        this.status = status;
        this.user_id = user_id;
        this.catName=catName;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public Note() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getLast_modified() {
        return last_modified;
    }

    public void setLast_modified(String last_modified) {
        this.last_modified = last_modified;
    }

    public String getTimer() {
        return timer;
    }

    public void setTimer(String timer) {
        this.timer = timer;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", created='" + created + '\'' +
                ", last_modified='" + last_modified + '\'' +
                ", timer='" + timer + '\'' +
                ", catName='" + catName + '\'' +
                ", status=" + status +
                ", user_id=" + user_id +
                '}';
    }
}
