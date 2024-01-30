package ca.tetervak.studentdata.passwords;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class PasswordGenerator {

    final private static String SYMBOLS
            = "ABCDEFGHIJKMNPQRSTUVWXYZ"
            + "abcdefghijkmnpqrstuvwxyz"
            + "23456789";


    public String randomPassword() {
        return randomPassword(8);
    }


    public String randomPassword(int length) {
        StringBuilder buf = new StringBuilder(length);
        SecureRandom rand = new SecureRandom();

        for (int i = 0; i < length; ++i) {
            buf.append(SYMBOLS.charAt(rand.nextInt(SYMBOLS.length())));
        }

        return buf.toString();
    }

}

