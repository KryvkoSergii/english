package ua.ksa.english.core.dao;

import org.springframework.data.repository.CrudRepository;
import ua.ksa.english.core.entity.Word;

import java.util.UUID;

public interface WordDAO extends CrudRepository<Word, UUID> {
}
