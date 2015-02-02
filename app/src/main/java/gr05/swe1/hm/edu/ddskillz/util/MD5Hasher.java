package gr05.swe1.hm.edu.ddskillz.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Fabian on 25.11.2014.
 * Help-Method to create MD5-Hash from given String.
 */
public class MD5Hasher {
    public static String hash(String encrString) {
        MessageDigest mdEnc = null;
        String md5 = "";
        try {
            mdEnc = MessageDigest.getInstance("MD5");
            mdEnc.update(encrString.getBytes(), 0, encrString.length());
            md5 = new BigInteger(1, mdEnc.digest()).toString(16);
            while (md5.length() < 32) {
                md5 = "0" + md5;
            }
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Exception while encrypting to md5");
            e.printStackTrace();
        } // Encryption algorithm
        return md5;
    }
}
