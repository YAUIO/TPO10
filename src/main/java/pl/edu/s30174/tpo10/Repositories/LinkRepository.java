package pl.edu.s30174.tpo10.Repositories;


import pl.edu.s30174.tpo10.Entities.Link;

public interface LinkRepository {
    public boolean existsId (String id);
    public void saveLink (Link link);
    public Link getLink (String id);
    public Link getLink (String id, String password);
    public void updateLink (Link link);
    public void removeLink (String id, String password);
    public void addVisit (String id);
}
