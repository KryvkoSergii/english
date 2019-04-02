package ua.ksa.english.core.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ua.ksa.english.core.entity.User;
import ua.ksa.english.core.web.AuthenticationResolver;

@Component
public class DefaultAuthenticationResolver implements AuthenticationResolver {
    @Override
    public User getLoggedUser() {
        SecurityContextHolder.getContext().getAuthentication();
        return null;
    }
}
