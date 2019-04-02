package ua.ksa.english.core.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.ksa.english.core.convertor.DtoConverter;
import ua.ksa.english.core.dao.ContainerDAO;
import ua.ksa.english.core.dao.DictionaryDAO;
import ua.ksa.english.core.dao.WordDAO;
import ua.ksa.english.core.dto.WordDTO;
import ua.ksa.english.core.entity.Container;
import ua.ksa.english.core.entity.Dictionary;
import ua.ksa.english.core.entity.User;
import ua.ksa.english.core.entity.Word;
import ua.ksa.english.core.exception.DictionaryNotFoundException;
import ua.ksa.english.core.exception.WordNotFoundException;

import javax.persistence.EntityNotFoundException;

import java.util.UUID;

import static ua.ksa.english.core.web.WordsController.WORDS_URL;


@RequiredArgsConstructor
@Controller
@Slf4j
@RequestMapping(path = WORDS_URL)
public class WordsController {
    public static final String WORDS_URL = "/words";
    public static final String BY_UUID_URL = "/{uuid}";
    private final ContainerDAO containerDAO;
    private final WordDAO wordDAO;
    private final AuthenticationResolver authenticationResolver;

    @PutMapping(path = BY_UUID_URL,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<WordDTO> update(@PathVariable("uuid") UUID uuid, WordDTO dto) {
        User user = authenticationResolver.getLoggedUser();
        log.info("{} uuid={} user={}", AccessCode.WORDS_UPDATE.name(), uuid,
                user.getUserId());
        Container container = containerDAO.findById(dto.getWordId())
                .orElseThrow(() -> new WordNotFoundException(dto.getWordId()));
        Hibernate.initialize(container.getDictionary());
        authenticationResolver.securityCheck(container.getDictionary(), user);
        Word word = wordDAO.getWordByForeignAndTranslate(dto.getForeign(), dto.getTranslate())
                .orElseGet(() -> new Word(dto.getForeign(), dto.getTranslate()));
        container.setWord(word);
        return ResponseEntity.ok(DtoConverter.containerToWordDTO(containerDAO.save(container)));
    }
}
