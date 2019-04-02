package ua.ksa.english.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DictionaryNotFoundException extends RuntimeException {
    public DictionaryNotFoundException(String message) {
        super(message);
    }

    public DictionaryNotFoundException(UUID uuid) {
        super("dictionary with dictionaryId not found " + uuid.toString());
    }
}
