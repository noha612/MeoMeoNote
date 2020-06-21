package nhom7.thh.meomeonote.model;

public class LineNote {
    private String title;
    private String shortContent;
    private String lastModified;
    private int catAvt;

    public LineNote() {
    }

    public LineNote(String title, String shortContent, String lastModified, int catAvt) {
        this.title = title;
        this.shortContent = shortContent;
        this.lastModified = lastModified;
        this.catAvt = catAvt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortContent() {
        return shortContent;
    }

    public void setShortContent(String shortContent) {
        this.shortContent = shortContent;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public int getCatAvt() {
        return catAvt;
    }

    public void setCatAvt(int catAvt) {
        this.catAvt = catAvt;
    }

    @Override
    public String toString() {
        return "LineNote{" +
                "title='" + title + '\'' +
                ", shortContent='" + shortContent + '\'' +
                ", lastModified=" + lastModified +
                ", catAvt='" + catAvt + '\'' +
                '}';
    }
}
