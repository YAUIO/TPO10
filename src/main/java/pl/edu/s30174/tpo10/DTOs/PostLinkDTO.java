package pl.edu.s30174.tpo10.DTOs;

import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;
import pl.edu.s30174.tpo10.Annotations.Password;

public class PostLinkDTO {
    @Size(min = 5, max = 20)
    public String name;

    @URL(protocol = "https")
    public String targetUrl;

    @Password
    public String password;

    public String getName() {
        return name;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
