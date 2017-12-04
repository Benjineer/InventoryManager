/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cardinalstone.utils;

import java.util.Random;
import javax.ejb.Stateless;

/**
 *
 * @author NiyiO
 */
@Stateless
public class PasswordManager {

    private static final String PASSWORD_STRING
            = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static final Random RANDOM = new Random();

    public String generatePassword(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(PASSWORD_STRING.charAt(RANDOM.nextInt(PASSWORD_STRING.length())));
        }
        return sb.toString();
    }

    public String hashPasword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean checkPassword(String password, String passwordHash) {
        return BCrypt.checkpw(password, passwordHash);
    }

}
