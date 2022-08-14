package dislinkt.accountservice.exceptions;

public class InconsistentData extends RuntimeException {

    private static final long serialVersionUID = -6741589782625053235L;

    public InconsistentData(String message) {
        super(message);
    }
}