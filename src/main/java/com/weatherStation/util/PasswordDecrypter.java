package com.weatherstation.util;

import org.jasypt.util.text.BasicTextEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Scanner;


public class PasswordDecrypter {

    public static final Logger LOGGER = LoggerFactory.getLogger(PasswordDecrypter.class);
    private BasicTextEncryptor basicTextEncryptor;

    public PasswordDecrypter(BasicTextEncryptor basicTextEncryptor) {
        this.basicTextEncryptor = basicTextEncryptor;
    }

    public void initSaltAndPrepareBasicTextEncryptor(Scanner scanner) {
        String key = getNewLine("key", scanner);
        basicTextEncryptor.setPasswordCharArray(key.toCharArray());
    }

    public String decrypt(String encryptedString) {
        return basicTextEncryptor.decrypt(encryptedString);
    }

    public String getNewLine(String element, Scanner scanner){
        String str = "";
        while(str.equals("")) {
            LOGGER.info(String.format("Enter the %s:", element));
            try {
                str = scanner.nextLine();
            } catch (Exception ex) {
                LOGGER.error(String.format("Wrong %s or input data type, %s", element, ex.getCause()));
            }
        }
        return str;
    }
}
