package com.weatherstation.util;


import org.jasypt.util.text.BasicTextEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class PasswordEncrypter {
    /**
     * Use this class to encrypt your password. Remember the key value and put the encrypted password
     * in com.weatherstation.rest.RestCall as a "private String encryptedPassword"
     */

    public static final Logger LOGGER = LoggerFactory.getLogger(PasswordEncrypter.class);

    public static void main(String[] args) {
        PasswordDecrypter passwordDecrypter = new PasswordDecrypter(new BasicTextEncryptor());
        String plainTextPassword = passwordDecrypter.getNewLine("plain text password", new Scanner(System.in));
        String key = passwordDecrypter.getNewLine("key", new Scanner(System.in));
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPasswordCharArray(key.toCharArray());

        System.out.println("-------------------------------------------------------");
        System.out.println(String.format("Original password: %s", plainTextPassword));
        System.out.println(String.format("Key: %s", key));
        System.out.println(String.format("Encrypted password: %s", textEncryptor.encrypt(plainTextPassword)));
        System.out.println("");
    }
}
