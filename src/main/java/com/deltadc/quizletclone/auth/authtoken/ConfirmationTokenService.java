package com.deltadc.quizletclone.auth.authtoken;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    public void saveConfirmationToken(ConfirmationToken token) {
        confirmationTokenRepository.save(token);
    }

    public Optional<ConfirmationToken> getToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }

    public void setConfirmedAt(String token) {
        confirmationTokenRepository.updateConfirmedAt(
                token, LocalDateTime.now());
    }

    public List<ConfirmationToken> findExpiredTokens(LocalDateTime now) {
        List<ConfirmationToken> allTokens = confirmationTokenRepository.findAll();

        return allTokens.stream()
                .filter(token -> token.getExpiresAt().isBefore(now))
                .collect(Collectors.toList());
    }

    public void deleteConfirmationToken(ConfirmationToken token) {
        Optional<ConfirmationToken> c = confirmationTokenRepository.findByToken(String.valueOf(token));
        c.ifPresent(confirmationTokenRepository::delete);
    }
}
