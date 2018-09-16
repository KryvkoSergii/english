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
import java.util.UUID;

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

    @PutMapping(params = URI)
    public ResponseEntity<WordDTO> update(@PathVariable(value = "id") UUID id, @RequestBody WordDTO wordDTO) {
        log.debug("request_id:{} request:{}", wordDTO);
        return null;
    }

    @GetMapping(params = URI)
    public ResponseEntity<Collection<WordDTO>> getAll() {
        log.debug("request:");
        return null;
    }

    @DeleteMapping(params = URI)
    public ResponseEntity<WordDTO> delete(@PathVariable(value = "id") UUID id) {
        log.debug("request_id:{}", id);
        return null;
    }

    final Converter<Word, WordDTO> converter = new Converter<Word, WordDTO>() {
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
