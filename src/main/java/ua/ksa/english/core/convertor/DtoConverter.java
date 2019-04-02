package ua.ksa.english.core.convertor;

import ua.ksa.english.core.dto.DictionaryDTO;
import ua.ksa.english.core.dto.WordDTO;
import ua.ksa.english.core.entity.Container;
import ua.ksa.english.core.entity.Dictionary;
import ua.ksa.english.core.entity.Word;

public class DtoConverter {

    public static Word wordDtoToWord(WordDTO wordDTO) {
        return new Word(null, wordDTO.getForeign(), wordDTO.getTranslate());
    }

    public static Container wordDtoToContainer(WordDTO wordDTO) {
        Word word = DtoConverter.wordDtoToWord(wordDTO);
        return new Container(wordDTO.getWordId(), null, word, null, null);
    }

    public static Dictionary dictionaryDtoToDictionary(DictionaryDTO dto) {
        return Dictionary.builder()
                .dictionaryId(dto.getDictionaryId())
                .containers(null)
                .name(dto.getName())
                .build();
    }

    public static DictionaryDTO dictionaryToDictionaryDto(Dictionary dictionary) {
        return DictionaryDTO.builder()
                .dictionaryId(dictionary.getDictionaryId())
                .name(dictionary.getName())
                .build();
    }

    public static WordDTO containerToWordDTO(Container entity) {
        return WordDTO.builder()
                .foreign(entity.getWord().getForeign())
                .translate(entity.getWord().getTranslate())
                .dictionaryId(entity.getDictionary().getDictionaryId())
                .wordId(entity.getContainerId())
                .build();
    }

    public static WordDTO containerToWordDTO(Container entity, Dictionary dictionary) {
        return WordDTO.builder()
                .foreign(entity.getWord().getForeign())
                .translate(entity.getWord().getTranslate())
                .dictionaryId(dictionary.getDictionaryId())
                .wordId(entity.getContainerId())
                .build();
    }

}
