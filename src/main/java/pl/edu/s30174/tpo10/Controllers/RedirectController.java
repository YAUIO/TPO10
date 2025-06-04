package pl.edu.s30174.tpo10.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.view.RedirectView;
import pl.edu.s30174.tpo10.DTOs.GetLinkDTO;
import pl.edu.s30174.tpo10.Exceptions.KeyNotFoundException;
import pl.edu.s30174.tpo10.Services.LinkService;

@Controller
@RequestMapping("/red")
public class RedirectController {
    public final LinkService service;

    public RedirectController(LinkService serv) {
        service = serv;
    }

    @GetMapping("{id}")
    public Object redirectToTarget(@PathVariable String id) {
        try {
            GetLinkDTO link = service.getLink(id);
            service.addVisit(id);

            return new RedirectView(link.targetUrl);
        } catch (KeyNotFoundException e) {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }
}