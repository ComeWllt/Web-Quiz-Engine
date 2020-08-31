package engine.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class NotCreatorException extends RuntimeException {

    public NotCreatorException(long id) {
        super(String.format("You are not the creator of Quiz %d", id));
    }
}
