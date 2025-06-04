package pl.edu.s30174.tpo10.Services;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;
import pl.edu.s30174.tpo10.DTOs.GetLinkDTO;
import pl.edu.s30174.tpo10.DTOs.PatchLinkDTO;
import pl.edu.s30174.tpo10.DTOs.PostLinkDTO;
import pl.edu.s30174.tpo10.Entities.Link;
import pl.edu.s30174.tpo10.Repositories.LinkRepository;

import java.util.Set;

@Service
public class LinkService {
    private final LinkRepository repository;
    private final Validator validator;

    public LinkService(LinkRepository repo, Validator validator) {
        repository = repo;
        this.validator = validator;
    }

    public GetLinkDTO shortenLink(PostLinkDTO link) throws ConstraintViolationException {
        Set<ConstraintViolation<PostLinkDTO>> violations = validator.validate(link);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        Link ent = new Link();
        ent.id = Link.generateId();
        while (repository.existsId(ent.id))
            ent.id = Link.generateId();
        ent.name = link.name;
        ent.password = link.password;
        ent.redirectUrl = "http://localhost:8080/red/" + ent.id;
        ent.targetUrl = link.targetUrl;
        ent.visits = 0;


        repository.saveLink(ent);

        GetLinkDTO dto = new GetLinkDTO();
        dto.id = ent.id;
        dto.name = ent.name;
        dto.redirectUrl = ent.redirectUrl;
        dto.targetUrl = ent.targetUrl;
        dto.visits = ent.visits;

        return dto;
    }

    public GetLinkDTO getLink(String id) {
        Link ent = repository.getLink(id);

        GetLinkDTO dto = new GetLinkDTO();
        dto.id = ent.id;
        dto.name = ent.name;
        dto.redirectUrl = ent.redirectUrl;
        dto.targetUrl = ent.targetUrl;
        dto.visits = ent.visits;

        return dto;
    }

    public void patchLink(PatchLinkDTO request) {
        Link ent = repository.getLink(request.id, request.password);

        if (request.targetUrl != null) {
            ent.targetUrl = request.targetUrl;
        }

        if (request.name != null) {
            ent.name = request.name;
        }

        repository.updateLink(ent);
    }

    public void removeLink(String id, String password) {
        repository.removeLink(id, password);
    }

    public void addVisit(String id) {
        repository.addVisit(id);
    }
}
