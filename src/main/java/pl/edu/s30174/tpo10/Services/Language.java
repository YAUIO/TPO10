package pl.edu.s30174.tpo10.Services;

import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class Language {
    public Locale locale = Locale.ENGLISH;

    public Locale locale () {
        return locale;
    }
}
