package ua.periodicals.exception;

public class LogicException extends RuntimeException {
    public LogicException() {
        super();
    }


    public LogicException(String message) {
        super(message);
    }


    public LogicException(String message, Throwable cause) {
        super(message, cause);
    }


    public LogicException(Throwable cause) {
        super(cause);
    }
}