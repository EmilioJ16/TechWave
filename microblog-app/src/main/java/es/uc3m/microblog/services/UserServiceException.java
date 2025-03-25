package es.uc3m.microblog.services;

public class UserServiceException extends Exception {

    private static final long serialVersionUID = 1L;

    public UserServiceException() { }

    public UserServiceException(String message) {
        super(message);
    }
}

