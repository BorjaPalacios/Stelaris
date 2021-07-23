package com.example.stelaris.utils;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class Security {

    private static SecretKey key;
    private static Cipher cipher;
    private static String algoritmo = "AES";
    private static int keysize = 16;
    public static String nasaKey = "cmEtd8km04mPGdNGRuRaStwZpgbi6kkl8seYUX1K";

    public static void addKey(String value) {

        byte[] valuebytes = value.getBytes();
        key = new SecretKeySpec(Arrays.copyOf(valuebytes, keysize), algoritmo);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String encriptar(String texto) {

        String value = "";
        try {
            addKey(algoritmo);
            cipher = Cipher.getInstance(algoritmo);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] textobytes = texto.getBytes();
            byte[] cipherbytes = cipher.doFinal(textobytes);
            value = Base64.getEncoder().encodeToString(cipherbytes);
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        return value;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String desencriptar(String texto) {

        String str = "";
        try {
            byte[] value = Base64.getDecoder().decode(texto);
            cipher = Cipher.getInstance(algoritmo);
            addKey(algoritmo);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] cipherbytes = cipher.doFinal(value);
            str = new String(cipherbytes);
        } catch (InvalidKeyException ex) {
            str = ex.getMessage();
        } catch (IllegalBlockSizeException ex) {
            str = ex.getMessage();
        } catch (BadPaddingException ex) {
            str = ex.getMessage();
        } catch (NoSuchAlgorithmException ex) {
            str = ex.getMessage();
        } catch (NoSuchPaddingException ex) {
            str = ex.getMessage();
        }

        return str;
    }
}
