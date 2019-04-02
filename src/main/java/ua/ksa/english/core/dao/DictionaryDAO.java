package ua.ksa.english.core.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import ua.ksa.english.core.entity.Dictionary;
import ua.ksa.english.core.entity.User;

import java.util.UUID;

public interface DictionaryDAO extends PagingAndSortingRepository<Dictionary, UUID> {
    @Query(value = "select d from Dictionary d where d.owner.userId=:id",
            countQuery = "select count(d) from Dictionary d where d.owner=:user")
    Page<Dictionary> findDictionariesByOwnerUserId(@Param("user") User user, Pageable pageable);
}
