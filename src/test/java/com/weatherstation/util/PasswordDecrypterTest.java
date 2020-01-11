package com.weatherstation.util;

import org.jasypt.util.text.BasicTextEncryptor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PasswordDecrypterTest {

    @Mock
    Scanner scanner;

    @Mock
    BasicTextEncryptor basicTextEncryptor;

    @Test
    void shouldReturnCorrectKeyValue() {
        PasswordDecrypter passwordDecrypter = new PasswordDecrypter(basicTextEncryptor);
        when(scanner.nextLine()).thenReturn("testKey");

        String key = passwordDecrypter.getNewLine("key", scanner);

        assertThat(key).isEqualTo("testKey");
    }

    @Test
    void shouldCallSetPasswordCharArrayMethod() {
        PasswordDecrypter passwordDecrypter = new PasswordDecrypter(basicTextEncryptor);
        when(scanner.nextLine()).thenReturn("testKey");

        passwordDecrypter.initSaltAndPrepareBasicTextEncryptor(scanner);

        verify(basicTextEncryptor, times(1)).setPasswordCharArray("testKey".toCharArray());
    }

    @Test
    void shouldCallDecryptMethod() {
        PasswordDecrypter passwordDecrypter = new PasswordDecrypter(basicTextEncryptor);
        when(scanner.nextLine()).thenReturn("testKey");
        passwordDecrypter.initSaltAndPrepareBasicTextEncryptor(scanner);

        passwordDecrypter.decrypt("password");

        verify(basicTextEncryptor, times(1)).decrypt("password");
    }

    @Test
    void shouldDecryptGivenPassword() {
        PasswordDecrypter passwordDecrypter = new PasswordDecrypter(new BasicTextEncryptor());
        when(scanner.nextLine()).thenReturn("testKey");
        passwordDecrypter.initSaltAndPrepareBasicTextEncryptor(scanner);

        String decryptedString = passwordDecrypter.decrypt("lbwOb4nrsmW7GZZ2tgdE4zfK28eSYIEi");

        assertThat(decryptedString).isEqualTo("password");
    }
}
