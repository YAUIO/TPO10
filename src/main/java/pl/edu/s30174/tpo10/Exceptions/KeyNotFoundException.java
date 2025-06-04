package pl.edu.s30174.tpo10.Exceptions;

public class KeyNotFoundException extends RuntimeException {
    public KeyNotFoundException(String message) {
        super(message);
    }

    public KeyNotFoundException() {
        super();
    }
}
