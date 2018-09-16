package ua.ksa.english.core.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.ksa.english.core.convertor.Converter;
import ua.ksa.english.core.dao.WordDAO;
import ua.ksa.english.core.dto.WordDTO;
import ua.ksa.english.core.entity.Word;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequiredArgsConstructor
@Slf4j
public class WordController {
    static final String URI = "/words";
    private final WordDAO wordDAO;

    @PostMapping(path = URI,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<WordDTO> create(@RequestBody WordDTO wordDTO) {
        log.debug("request:{}", wordDTO);
        Word response = wordDAO.save(converter.convert(wordDTO));
        log.debug("created:{}", wordDTO);
        return ResponseEntity.ok(converter.inverse(response));
    }

    @PutMapping(path = URI + "/{id}",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<WordDTO> update(@PathVariable UUID id, @RequestBody WordDTO wordDTO) {
        log.debug("request_id:{} request:{}", wordDTO);
        if (Objects.equals(wordDTO.getId(), id))
            return ResponseEntity.badRequest().build();
        return Optional.ofNullable(converter.convert(wordDTO))
                .map(wordDAO::save)
                .filter(word -> {
                    log.debug("updated:{}", word);
                    return true;
                })
                .map(converter::inverse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(path = URI, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Collection<WordDTO>> getAll() {
        log.debug("request:");
        Collection<WordDTO> response = StreamSupport.stream(wordDAO.findAll().spliterator(), false)
                .map(converter::inverse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = URI + "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<WordDTO> delete(@PathVariable UUID id) {
        log.debug("request_id:{}", id);
        return wordDAO.findById(id)
                .map(word -> {
                    log.debug("persisted:{}", word);
                    return ResponseEntity.ok(converter.inverse(word));
                })
                .orElseGet(() -> {
                    log.debug("persisted:{}", "not found");
                    return ResponseEntity.notFound().build();
                });
    }

    private final Converter<Word, WordDTO> converter = new Converter<Word, WordDTO>() {
        @Override
        public Word convert(WordDTO wordDTO) {
            Word word = Word.builder()
                    .english(wordDTO.getEnglish())
                    .translate(wordDTO.getTranslate())
                    .build();
            word.setId(wordDTO.getId());
            return word;
        }

        @Override
        public WordDTO inverse(Word entity) {
            return WordDTO.builder()
                    .english(entity.getEnglish())
                    .translate(entity.getTranslate())
                    .id(entity.getId()).build();
        }
    };
}
