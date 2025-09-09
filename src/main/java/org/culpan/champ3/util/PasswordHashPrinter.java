package org.culpan.champ3.util;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

// @Component
// @Profile("dev")
public class PasswordHashPrinter implements CommandLineRunner {
    private final PasswordEncoder encoder;

    public PasswordHashPrinter(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) {
        String rawPassword = "abc123"; // change to what you want
        String hash = encoder.encode(rawPassword);
        System.out.println("BCrypt hash: " + hash);
    }
}