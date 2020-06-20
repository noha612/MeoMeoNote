package nhom7.thh.meomeonote.entity;

public class User {
    private int id;
    private String phoneNumber;
    private String password;
    private String created;

    public User() {
    }

    public User(int id, String phoneNumber, String password, String created) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.created = created;
    }

    public int getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
