package ua.ksa.english.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class WordNotFoundException extends RuntimeException {
    public WordNotFoundException(String message) {
        super(message);
    }

    public WordNotFoundException(UUID uuid) {
        super("dictionary with dictionaryId not found " + uuid.toString());
    }
}
