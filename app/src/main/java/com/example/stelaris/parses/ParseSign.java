package com.example.stelaris.parses;

import android.content.Context;

import com.example.stelaris.R;
import com.example.stelaris.exceptions.StringException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseSign {

    public static boolean parseUserName(Context context, String username) throws StringException {

        if (username == null || username.trim().isEmpty()) {
            throw new StringException(context.getString(R.string.userNameEmpty));
        } else if (username.length() < 6 || username.length() > 12) {
            throw new StringException(context.getString(R.string.userNameLength));
        } else if (checkSpecialCharacter(username)) {
            throw new StringException(context.getString(R.string.userNameSpecials));
        }

        return true;
    }

    public static boolean parseEmail(Context context, String email) throws StringException {

        if (email == null || email.trim().isEmpty()) {
            throw new StringException(context.getString(R.string.emailEmpty));
        } else if (!checkEmail(email)) {
            throw new StringException(context.getString(R.string.emailFormat));
        }

        return true;
    }

    public static boolean parsePassword(Context context, String password) throws StringException {

        if (password == null || password.trim().isEmpty()) {
            throw new StringException(context.getString(R.string.passwordEmpty));
        } else if (password.length() < 6 || password.length() > 12) {
            throw new StringException(context.getString(R.string.passwordLength));
        } else if (!checkNumbers(password)) {
            throw new StringException(context.getString(R.string.passwordNumber));
        }

        return true;
    }

    private static boolean checkSpecialCharacter(String string) {

        Pattern pattern = Pattern.compile("[A-Za-z0-9]");
        Matcher matcher = pattern.matcher(string);

        return matcher.find();
    }

    private static boolean checkEmail(String email) {

        boolean arroba = false, punto = false;

        for (Character c : email.toCharArray()) {
            if (c.equals('@'))
                arroba = true;
            if (c.equals('.'))
                punto = true;
        }

        return arroba && punto;
    }

    private static boolean checkNumbers(String string) {

        boolean number = false;

        for (Character c : string.toCharArray()) {
            if (Character.isDigit(c))
                number = true;
            break;
        }

        return number;
    }
}
