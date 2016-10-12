package com.glooory.flatreader.util;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Glooory on 2016/10/12 0012 19:37.
 */

public class ITHomeUtils {

    private ITHomeUtils() {
    }

    public static String getMinNewsId(String id) {
        byte[] bytes = new byte[]{-86, -7, -69, -102, -83, -124, -87, -14};
        int i = 0;
        while (i < bytes.length) {
            bytes[i] = (byte) (-38 ^ bytes[i]);
            i++;
        }
        try {
            return des(id, new String(bytes));
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String des(String id, String key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        int index = 0;
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "DES");
        Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
        cipher.init(1, secretKeySpec);
        int i = id.length();
        if (i < 8) {
            i = 8 - i;
        } else {
            i %= 8;
            i = i != 0 ? 8 - i : 0;
        }
        while (index < i) {
            id = String.valueOf(id) + "\u0000";
            index++;
        }
        return a(cipher.doFinal(id.getBytes()));
    }

    public static String a(byte[] arg5) {
        StringBuilder v1 = new StringBuilder();
        int v0;
        for (v0 = 0; v0 < arg5.length; ++v0) {
            String v2 = Integer.toHexString(arg5[v0] & 255);
            if (v2.length() == 1) {
                v1.append("0").append(v2);
            } else {
                v1.append(v2);
            }
        }

        return v1.toString();
    }

    public static String getSplitNewsId(String newsid) {
        String s1 = newsid.substring(0, 3);
        String s2 = newsid.substring(3);
        return s1 + "/" + s2;
    }
}
