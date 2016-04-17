package hello;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by craigleclair on 2016-04-16.
 */
@Entity
public class File {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    private int userHash;
    private String fileName;

    protected File() {}

    public File(int userHash, String fileName) {
        this.userHash = userHash;
        this.fileName = fileName;
    }

    public int getUserHash() {
        return userHash;
    }

    public void setUserHash(int userHash) {
        this.userHash = userHash;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%d, userHash='%d', name='%s']",
                id, userHash, fileName);
    }
}
