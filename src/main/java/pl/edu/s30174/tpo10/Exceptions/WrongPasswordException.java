package pl.edu.s30174.tpo10.Exceptions;

public class WrongPasswordException extends RuntimeException {
    public WrongPasswordException(String message) {
        super(message);
    }

  public WrongPasswordException() {
    super();
  }
}
