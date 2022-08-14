package dislinkt.accountservice.exceptions;

public class EntityNotFound extends RuntimeException {

    private static final long serialVersionUID = -6741589782625053235L;

    public EntityNotFound(String message) {
        super(message);
    }
}