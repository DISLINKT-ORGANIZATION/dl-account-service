package dislinkt.accountservice.exceptions;

public class InvalidToken extends RuntimeException {

    private static final long serialVersionUID = -6741589782625053235L;

    public InvalidToken(String message) {
        super(message);
    }
}