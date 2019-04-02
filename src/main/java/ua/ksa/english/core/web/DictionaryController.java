package ua.ksa.english.core.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.ksa.english.core.convertor.DtoConverter;
import ua.ksa.english.core.dao.ContainerDAO;
import ua.ksa.english.core.dao.DictionaryDAO;
import ua.ksa.english.core.dao.WordDAO;
import ua.ksa.english.core.dto.DictionaryDTO;
import ua.ksa.english.core.dto.WordDTO;
import ua.ksa.english.core.entity.Container;
import ua.ksa.english.core.entity.Dictionary;
import ua.ksa.english.core.entity.User;
import ua.ksa.english.core.entity.Word;
import ua.ksa.english.core.exception.DictionaryNotFoundException;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import static ua.ksa.english.core.web.DictionaryController.DICTIONARY_URL;

@RequiredArgsConstructor
@Controller
@RequestMapping(path = DICTIONARY_URL)
@Slf4j
public class DictionaryController {
    public static final String DICTIONARY_URL = "/dictionary";
    public static final String BY_UUID_URL = "/{uuid}";
    public static final String CONTAINERS_URL = "/{uuid}/words";
    private final ContainerDAO containerDAO;
    private final DictionaryDAO dictionaryDao;
    private final WordDAO wordDAO;
    private final AuthenticationResolver authenticationResolver;

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Page> findAllByUser(PageRequest pageRequest) {
        User user = authenticationResolver.getLoggedUser();
        log.info("{} user={}", AccessCode.DICTIONARY_FIND_ALL.name(), user.getUserId());
        // TODO: 21.10.18 how get user id
        return ResponseEntity.ok(dictionaryDao.findDictionariesByOwnerUserId(user, pageRequest));
    }

    @GetMapping(params = BY_UUID_URL, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<DictionaryDTO> findByUuid(@PathVariable("uuid") UUID dictionaryId) {
        User user = authenticationResolver.getLoggedUser();
        log.info("{} uuid={} user={}", AccessCode.DICTIONARY_FIND_BY_UUID.name(), dictionaryId, user.getUserId());
        DictionaryDTO dto = dictionaryDao.findById(dictionaryId)
                .filter(dic ->  authenticationResolver.securityCheck(dic, user))
                .map(DtoConverter::dictionaryToDictionaryDto)
                .orElseThrow(() -> new DictionaryNotFoundException(dictionaryId));
        return ResponseEntity.ok(dto);
    }

    @PostMapping(path = "/",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<DictionaryDTO> create(DictionaryDTO dto) {
        User user = authenticationResolver.getLoggedUser();
        log.info("{} request={} user={}", AccessCode.DICTIONARY_CREATE.name(), dto, user.getUserId());
        dto.setDictionaryId(null);
        Dictionary entity = DtoConverter.dictionaryDtoToDictionary(dto);
        entity.setOwner(user);
        entity = dictionaryDao.save(entity);
        URI location = URI.create(DICTIONARY_URL + "/" + entity.getDictionaryId().toString());
        return ResponseEntity.created(location).body(DtoConverter.dictionaryToDictionaryDto(entity));

    }

    @PutMapping(path = BY_UUID_URL,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<DictionaryDTO> update(@PathVariable("uuid") UUID dictionaryId, DictionaryDTO dto) {
        User user = authenticationResolver.getLoggedUser();
        log.info("{} uuid={} request={} user={}", AccessCode.DICTIONARY_UPDATE.name(), dictionaryId.toString(),
                dto, user.getUserId());
        if (Objects.isNull(dto.getDictionaryId()) || !dto.getDictionaryId().equals(dictionaryId)) {
            return ResponseEntity.badRequest().build();
        }
        Dictionary entity = dictionaryDao.findById(dictionaryId)
                .orElseThrow(() -> new DictionaryNotFoundException(dictionaryId));
        authenticationResolver.securityCheck(entity, user);
        entity.setName(dto.getName());
        return ResponseEntity.ok(DtoConverter.dictionaryToDictionaryDto(dictionaryDao.save(entity)));
    }

    @PutMapping(path = CONTAINERS_URL,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<WordDTO> addWordToDictionary(@PathVariable("uuid") UUID dictionaryId,
                                                       WordDTO dto) {
        User user = authenticationResolver.getLoggedUser();
        log.info("{} uuid={} request={} user={}", AccessCode.DICTIONARY_WORDS_ADD.name(), dictionaryId.toString(),
                dto, user.getUserId());
        Dictionary entity = dictionaryDao.findById(dictionaryId)
                .orElseThrow(() -> new DictionaryNotFoundException(dictionaryId));
        authenticationResolver.securityCheck(entity, user);
        Word word = wordDAO.getWordByForeignAndTranslate(dto.getForeign(), dto.getTranslate())
                .orElseGet(Word::new);
        Container container = Container.builder()
                .dictionary(entity)
                .word(word)
                .build();
        dto.setWordId(containerDAO.save(container).getContainerId());
        URI location = URI.create(DICTIONARY_URL + "/" + entity.getDictionaryId().toString());
        return ResponseEntity.created(location).body(dto);
    }

    @GetMapping(path = CONTAINERS_URL,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Page> findWordByDictionary(@PathVariable("uuid") UUID dictionaryId, PageRequest pageRequest) {
        User user = authenticationResolver.getLoggedUser();
        log.info("{} uuid={} user={}", AccessCode.DICTIONARY_WORDS_GET.name(), dictionaryId.toString(),
                user.getUserId());
        Dictionary entity = dictionaryDao.findById(dictionaryId)
                .orElseThrow(() -> new DictionaryNotFoundException(dictionaryId));
        authenticationResolver.securityCheck(entity, user);
        Page<Container> page = containerDAO.findContainsByDictionary(dictionaryId, pageRequest);
        List<WordDTO> content = page.getContent()
                .stream()
                .map(con -> DtoConverter.containerToWordDTO(con, entity))
                .collect(Collectors.toList());
        return ResponseEntity.ok(new PageImpl<>(content,page.getPageable(),page.getTotalElements()));
    }

    @DeleteMapping(path = BY_UUID_URL,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<DictionaryDTO> delete(@PathVariable("uuid") UUID dictionaryId) {
        User user = authenticationResolver.getLoggedUser();
        log.info("{} uuid={} request={} user={}", AccessCode.DICTIONARY_DELETE.name(), dictionaryId.toString(),
                user.getUserId());
        Dictionary entity = dictionaryDao.findById(dictionaryId)
                .orElseThrow(() -> new DictionaryNotFoundException(dictionaryId));
        authenticationResolver.securityCheck(entity, user);
        dictionaryDao.delete(entity);
        return ResponseEntity.ok(DtoConverter.dictionaryToDictionaryDto(entity));
    }



}
