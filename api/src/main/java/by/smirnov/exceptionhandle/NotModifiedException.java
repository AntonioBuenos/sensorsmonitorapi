package by.smirnov.exceptionhandle;

import static by.smirnov.constants.ResponseEntityConstants.ALREADY_DELETED_MESSAGE;

public class NotModifiedException extends RuntimeException{

    public NotModifiedException(String message) {
        super(message);
    }

    public NotModifiedException() {
        super(ALREADY_DELETED_MESSAGE);
    }

    @Override
    public String toString() {
        return "NotModifiedException{}" + super.toString();
    }
}
