package pks.exceptions;

public class ReadDataException extends RuntimeException {
    public ReadDataException(final String message, final Throwable throwable) {
        super(message, throwable);
    }
}
