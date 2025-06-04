package pl.edu.s30174.tpo10.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.s30174.tpo10.Annotations.Password;

import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

@Entity
public class Link {
    private static final char[] dictionary;

    static {
        dictionary = new char[52];
        for (int i = 0; i < 52; i++) {
            if (i < 26) {
                dictionary[i] = (char) ('a' + i);
            } else {
                dictionary[i] = (char) ('A' + (i - 26));
            }
        }
    }

    public Link() {}

    @Id
    public String id;

    public String name;

    public String password;

    @Column(unique = true)
    public String targetUrl;

    public String redirectUrl;
    public int visits;

    public static String generateId () {
        StringBuilder id = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            id.append(dictionary[(int) (Math.random() * 52)]);
        }
        return id.toString();
    }

    @Override
    public String toString() {
        return id + " " + targetUrl;
    }
}
