package pl.edu.s30174.tpo10.Controllers;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.edu.s30174.tpo10.Services.Language;
import pl.edu.s30174.tpo10.DTOs.GetLinkDTO;
import pl.edu.s30174.tpo10.DTOs.PatchLinkDTO;
import pl.edu.s30174.tpo10.DTOs.PostLinkDTO;
import pl.edu.s30174.tpo10.Exceptions.KeyNotFoundException;
import pl.edu.s30174.tpo10.Exceptions.WrongPasswordException;
import pl.edu.s30174.tpo10.Services.LinkService;
import pl.edu.s30174.tpo10.Validators.PasswordValidator;

import java.util.Locale;
import java.util.ResourceBundle;

@Controller
@RequestMapping("/links")
public class LinkController {
    public final LinkService service;
    private final PasswordValidator passwordValidator;
    public ResourceBundle bundle = null;
    public final Language lang;

    public LinkController (LinkService serv, Language language, PasswordValidator passwordValidator) {
        service = serv;
        lang = language;
        this.passwordValidator = passwordValidator;
    }

    private void setBundle (String s, Model model) {
        if (s != null) {
            lang.locale = Locale.forLanguageTag(s);
        }
        if (lang.locale == null) lang.locale = Locale.forLanguageTag("en");
        bundle = ResourceBundle.getBundle("messages", lang.locale);
        if (model != null) setUpModel(model);
    }

    @GetMapping("/create")
    public String showCreate(Model model, @RequestParam(required = false) String lang) {
        setBundle(lang, model);
        model.addAttribute("currentLocale", this.lang.locale);
        model.addAttribute("postLinkDTO", new PostLinkDTO());
        return "createLink";
    }

    @PostMapping
    public String shortenLink (
            @ModelAttribute PostLinkDTO request,
            @RequestParam(required = false) String lang,
            Model model
            ) {
        try {
            setBundle(lang, model);
            GetLinkDTO link = service.shortenLink(request);
            return "redirect:/links/" + link.id;
        } catch (KeyNotFoundException e) {
            model.addAttribute("errorTitle", e.getClass());
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("errorHtml", 404);
            return "status";
        } catch (ConstraintViolationException e) {
            String mainMessage = null;
            String field = e.getConstraintViolations().stream().findFirst().orElseThrow(NullPointerException::new).getPropertyPath().toString();
            if (field.equals("name"))
                mainMessage = bundle.getString("validation.name.size");
            else if (field.equals("targetUrl") || field.equals("redirectUrl"))
                mainMessage = bundle.getString("validation.url.invalid");
            else
                mainMessage = bundle.getString("password.error.header");

            StringBuilder sb = new StringBuilder(mainMessage).append("\n");
            for (ConstraintViolation<?> ex : e.getConstraintViolations()) {
                sb.append(ex.getMessage()).append(";\n");
            }

            model.addAttribute("errorTitle", e.getClass());
            model.addAttribute("errorMessage", sb.toString());
            model.addAttribute("errorHtml", 400);
            return "status";
        }
    }

    @GetMapping("{id}")
    public Object getLink (@PathVariable String id,
                                           @RequestParam(required = false) String lang,
                                           Model model) {
        try {
            setBundle(lang, model);
            model.addAttribute("getLinkDTO", service.getLink(id));
            return "viewLink";
        } catch (KeyNotFoundException e) {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    @PostMapping("/edit")
    public String patchLink (
            @ModelAttribute PatchLinkDTO dto,
            Model model,
            @RequestParam(required = false) String lang
            ) {
        try {
            setBundle(lang, model);
            service.patchLink(dto);
            return "redirect:/links/" + dto.id;
        } catch (WrongPasswordException e) {
            model.addAttribute("errorTitle", e.getClass());
            model.addAttribute("errorMessage",  bundle.getString("password.error.header"));
            model.addAttribute("errorHtml", HttpStatus.FORBIDDEN);
            return "status";
        } catch (KeyNotFoundException e) {
            model.addAttribute("errorTitle", e.getClass());
            model.addAttribute("errorMessage", "Not Found");
            model.addAttribute("errorHtml", HttpStatus.NOT_FOUND);
            return "status";
        }
    }

    @DeleteMapping("{id}")
    public String deleteLink(@PathVariable String id,
                                             Model model,
                                             @RequestParam(required = false) String pass,
                                             @RequestParam(required = false) String lang) {
        try {
            setBundle(lang, null);
            if (pass == null) throw new WrongPasswordException();
            service.removeLink(id, pass);
            model.addAttribute("errorTitle", "200 OK");
            return "status";
        } catch (WrongPasswordException e) {
            model.addAttribute("errorTitle", e.getClass());
            model.addAttribute("errorMessage", bundle.getString("validation.failed"));
            model.addAttribute("errorHtml", HttpStatus.FORBIDDEN);
            return "status";
        } catch (KeyNotFoundException e) {
            model.addAttribute("errorTitle", "200 OK");
            return "status";
        }
    }

    private void setUpModel (Model model) {
        model.addAttribute("currentLang", lang.locale.toString());

        model.addAttribute("english", bundle.getString("language_english"));
        model.addAttribute("polish", bundle.getString("language_polish"));
        model.addAttribute("german", bundle.getString("language_german"));

        model.addAttribute("label_name", bundle.getString("label_name"));
        model.addAttribute("label_url", bundle.getString("label_url"));
        model.addAttribute("label_password", bundle.getString("label_password"));

        model.addAttribute("button_create", bundle.getString("button_create"));
        model.addAttribute("button_save", bundle.getString("button_save"));
        model.addAttribute("button_delete", bundle.getString("button_delete"));
    }
}
