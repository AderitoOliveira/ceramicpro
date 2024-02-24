package nuvemdesoftware.ceramicpro.exception;

public class AlreadyExistsException extends RuntimeException{

    private static final long serialVersionUID = 142339988967445L;

    public AlreadyExistsException(String s) {
        super(s);
    }
}
