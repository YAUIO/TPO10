package pl.edu.s30174.tpo10.DTOs;

public class GetLinkDTO {
    public String id;
    public String name;
    public String targetUrl;
    public String redirectUrl;
    public int visits;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public int getVisits() {
        return visits;
    }
}
