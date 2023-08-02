package com.csctracker.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
@UtilityClass
public class SecretsReader {
    private static String readSecret(String secretName) {
        String secret = null;
        try {
            secret = new String(Files.readAllBytes(Paths.get("/run/secrets/" + secretName))).trim();
        } catch (IOException e) {
            throw new RuntimeException("Error reading secret " + secretName, e);
        }
        return secret;
    }
}
