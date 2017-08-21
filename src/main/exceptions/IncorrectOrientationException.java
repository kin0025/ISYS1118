package main.exceptions;

public class IncorrectOrientationException extends Exception {
    public IncorrectOrientationException() {
    }

    public IncorrectOrientationException(String e) {
        super(e);
    }
}
