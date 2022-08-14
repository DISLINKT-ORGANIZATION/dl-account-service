package dislinkt.accountservice.exceptions;

public class ResumeAlreadyExists extends RuntimeException {

    private static final long serialVersionUID = -6741589782625053235L;

    public ResumeAlreadyExists(String message) {
        super(message);
    }
}