package ua.ksa.english.core.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import ua.ksa.english.core.entity.Word;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface WordDAO extends PagingAndSortingRepository<Word, UUID> {

    Optional<Word> getWordByForeignAndTranslate(String foreign, String translate);
}
