package nhom7.thh.meomeonote.entity;

public class Checklist {
    private String checklist;
    private String id;
    private String password;
    private String content;
    private String created;
    private String last_modified;
    private String status;
    private String user_id;

    public Checklist(String checklist, String id, String password,
                     String content, String created, String last_modified, String status, String user_id) {
        this.checklist = checklist;
        this.id = id;
        this.password = password;
        this.content = content;
        this.created = created;
        this.last_modified = last_modified;
        this.status = status;
        this.user_id = user_id;
    }

    public Checklist() {
    }

    public String getChecklist() {
        return checklist;
    }

    public void setChecklist(String checklist) {
        this.checklist = checklist;
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
