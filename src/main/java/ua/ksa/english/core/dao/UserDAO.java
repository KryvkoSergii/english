package ua.ksa.english.core.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import ua.ksa.english.core.entity.User;

import java.util.UUID;

public interface UserDAO extends PagingAndSortingRepository<User, UUID> {
}
