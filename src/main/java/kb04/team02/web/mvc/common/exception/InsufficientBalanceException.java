package kb04.team02.web.mvc.common.exception;

public class InsufficientBalanceException extends RuntimeException{
    public InsufficientBalanceException() {
    }

    public InsufficientBalanceException(String message) {
        super(message);
    }
}
