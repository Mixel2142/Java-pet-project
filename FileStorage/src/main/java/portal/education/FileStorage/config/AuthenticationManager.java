package portal.education.FileStorage.config;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            SecurityContextHolder
                    .getContext()
                    .setAuthentication(authentication);
            return Mono.just(authentication);
        }
        return Mono.empty();
    }
}
