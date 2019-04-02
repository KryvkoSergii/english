package ua.ksa.english.core.web;

import org.springframework.security.access.AccessDeniedException;
import ua.ksa.english.core.entity.User;
import ua.ksa.english.core.utils.ProtectedResource;

public interface AuthenticationResolver {
    User getLoggedUser();

    default boolean securityCheck(ProtectedResource resource, User user) {
        if (resource.isAccessDeniedToResourceForUser(user))
            throw new AccessDeniedException("access denied");
        return true;
    }
}
