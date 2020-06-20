package nhom7.thh.meomeonote.entity;

public class Note {
    private String note;
    private String id;
    private String password;
    private String title;
    private String content;
    private String created;
    private String last_modified;
    private String timer;
    private String status;
    private String user_id;

    public Note(String note, String id, String password,
                String title, String content, String created,
                String last_modified, String timer, String status, String user_id) {
        this.note = note;
        this.id = id;
        this.password = password;
        this.title = title;
        this.content = content;
        this.created = created;
        this.last_modified = last_modified;
        this.timer = timer;
        this.status = status;
        this.user_id = user_id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
