package pl.edu.s30174.tpo10.Repositories;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import pl.edu.s30174.tpo10.Entities.Link;
import pl.edu.s30174.tpo10.Exceptions.KeyNotFoundException;
import pl.edu.s30174.tpo10.Exceptions.WrongPasswordException;

@Repository
public class LinkRepositoryH2 implements LinkRepository {
    private final EntityManager em;

    public LinkRepositoryH2(EntityManager em) {
        this.em = em;
    }

    @Override
    public boolean existsId(String id) {
        return em.createQuery("SELECT l from Link l where l.id = :id", Link.class)
                .setParameter("id", id)
                .getResultStream()
                .findFirst().isPresent();
    }

    @Override
    @Transactional
    public void saveLink(Link link) {
        em.persist(link);
    }

    @Override
    public Link getLink(String id) {
        return em.createQuery("SELECT l from Link l where l.id = :id", Link.class)
                .setParameter("id", id)
                .getResultStream()
                .findFirst()
                .orElseThrow(KeyNotFoundException::new);
    }

    @Override
    public Link getLink(String id, String password) {
        if (password == null || password.isBlank()) throw new WrongPasswordException();
        Link link = em.createQuery("SELECT l from Link l where l.id = :id", Link.class)
                .setParameter("id", id)
                .getResultStream()
                .findFirst()
                .orElseThrow(KeyNotFoundException::new);
        if (!link.password.equals(password)) throw new WrongPasswordException();
        return link;
    }

    @Override
    @Transactional
    public void updateLink(Link link) {
        em.merge(link);
    }

    @Override
    @Transactional
    public void removeLink(String id, String password) {
        Link link = getLink(id, password);
        em.remove(link);
    }

    @Override
    @Transactional
    public void addVisit(String id) {
        Link link = getLink(id);
        link.visits++;
        em.merge(link);
    }
}
