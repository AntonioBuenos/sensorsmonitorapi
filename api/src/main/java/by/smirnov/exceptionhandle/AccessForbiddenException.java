package by.smirnov.exceptionhandle;

import static by.smirnov.constants.ResponseEntityConstants.FORBIDDEN_MESSAGE;

public class AccessForbiddenException extends RuntimeException{

    public AccessForbiddenException(String message) {
        super(message);
    }

    public AccessForbiddenException() {
        super(FORBIDDEN_MESSAGE);
    }

    @Override
    public String toString() {
        return "AccessForbiddenException{}" + super.toString();
    }
}
