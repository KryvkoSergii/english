package ua.ksa.english.core.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import ua.ksa.english.core.entity.Container;

import java.util.UUID;

public interface ContainerDAO extends PagingAndSortingRepository<Container, UUID> {

    @Query(value = "select c from Container c where c.dictionary.dictionaryId=:id",
            countQuery = "select count(c) from Container c where c.dictionary.dictionaryId=:id")
    Page<Container> findContainsByDictionary(@Param("id") UUID dictionaryId, Pageable pageable);
}
