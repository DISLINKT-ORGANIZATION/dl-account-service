package dislinkt.accountservice.exceptions;

public class AccountAlreadyExists extends RuntimeException {

    private static final long serialVersionUID = -6741589782625053235L;

    public AccountAlreadyExists(String message) {
        super(message);
    }
}