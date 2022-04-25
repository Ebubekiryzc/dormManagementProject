package tr.edu.duzce.mf.bm.core.utilities.security.hashing;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

public class HashingHelper {

    public static void generateHash(String passwordToHash, SaltAndPepperModel saltAndHash){
        generateSalt(saltAndHash);
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-512");

        digest.reset();
        digest.update(saltAndHash.getSalt());

        saltAndHash.setHash(digest.digest(passwordToHash.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException exception) {
            System.err.println(exception.getMessage()+ "/21 HashingHelper");
        }
    }

    public static boolean verifyHash(String passwordToHash, SaltAndPepperModel saltAndHash) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-512");
            messageDigest.reset();
            messageDigest.update(saltAndHash.getSalt());

            var computedHash = messageDigest.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
            if(Arrays.equals(saltAndHash.getHash(),computedHash)) return true;

            return false;
        } catch (NoSuchAlgorithmException exception) {
            System.err.println(exception.getMessage()+"/35 HashingHelper");
            return false;
        }
    }

    private static void generateSalt(SaltAndPepperModel saltAndHash){
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(saltAndHash.getSalt());
    }
}
