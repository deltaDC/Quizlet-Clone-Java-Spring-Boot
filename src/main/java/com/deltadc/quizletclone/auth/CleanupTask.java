package com.deltadc.quizletclone.auth;

import com.deltadc.quizletclone.auth.authtoken.ConfirmationToken;
import com.deltadc.quizletclone.auth.authtoken.ConfirmationTokenRepository;
import com.deltadc.quizletclone.auth.authtoken.ConfirmationTokenService;
import com.deltadc.quizletclone.user.User;
import com.deltadc.quizletclone.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CleanupTask {

    private final ConfirmationTokenService confirmationTokenService;
    private final UserRepository userRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Scheduled(fixedRate = 60000 * 5)
    public void deleteExpiredUsers() {
        List<ConfirmationToken> expiredTokens = confirmationTokenService.findExpiredTokens(LocalDateTime.now());

        for (ConfirmationToken token : expiredTokens) {
            User user = token.getUser();
            if (user != null && !user.getEnabled()) {
                userRepository.delete(user);
                // Optionally, you can also delete the expired token from the database
                confirmationTokenService.deleteConfirmationToken(token);
            }
        }
    }

}
