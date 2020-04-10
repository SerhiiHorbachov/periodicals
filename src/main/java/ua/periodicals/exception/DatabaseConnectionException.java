package ua.periodicals.exception;

public class DatabaseConnectionException extends Exception {
    public DatabaseConnectionException(String errorMessage) {
        super(errorMessage);
    }

    public DatabaseConnectionException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

}
