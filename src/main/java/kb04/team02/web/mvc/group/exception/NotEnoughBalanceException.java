package kb04.team02.web.mvc.group.exception;

public class NotEnoughBalanceException extends Throwable {
    public NotEnoughBalanceException() {

    }

    public NotEnoughBalanceException(String message) {
        super(message);
    }
}
