package com.deltadc.quizletclone.auth.email;

public interface EmailSender {
    void send(String to, String email);
}
