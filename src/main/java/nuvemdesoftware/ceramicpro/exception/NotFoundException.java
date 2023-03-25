package nuvemdesoftware.ceramicpro.exception;

public class NotFoundException extends RuntimeException{

    private static final long serialVersionUID = 14233124567445L;

    public NotFoundException(String s) {
        super(s);
    }
}
