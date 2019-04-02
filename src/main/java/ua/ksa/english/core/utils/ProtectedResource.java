package ua.ksa.english.core.utils;

import ua.ksa.english.core.entity.User;

@FunctionalInterface
public interface ProtectedResource {
    boolean isAccessDeniedToResourceForUser(User loggedUser);
}
