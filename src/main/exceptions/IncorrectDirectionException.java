package main.exceptions;

public class IncorrectDirectionException extends Exception {
    public IncorrectDirectionException() {
    }

    public IncorrectDirectionException(String message) {
        super(message);
    }
}
