package hello;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by craigleclair on 2016-04-16.
 */
@Entity
public class Setting {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    private String key;
    private int value;

    public Setting(){};

    public Setting(String key, int value){
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
