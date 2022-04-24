package tr.edu.duzce.mf.bm.core.utilities.security.hashing;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashingHelper {

    public static void generateHash(String passwordToHash, SaltAndPepperModel saltAndHash) throws NoSuchAlgorithmException {
        generateSalt(saltAndHash);

        MessageDigest digest = MessageDigest.getInstance("SHA-512");
        digest.reset();
        digest.update(saltAndHash.getSalt());

        saltAndHash.setHash(digest.digest(passwordToHash.getBytes(StandardCharsets.UTF_8)));
    }

    public static boolean verifyHash(String passwordToHash, SaltAndPepperModel saltAndHash) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
        messageDigest.reset();
        messageDigest.update(saltAndHash.getSalt());

        var computedHash = messageDigest.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
        for (int index = 0; index < saltAndHash.getHash().length; index++) {
            if(computedHash[index] != saltAndHash.getHash()[index]){
                return false;
            }
        }
        return true;
    }

    private static void generateSalt(SaltAndPepperModel saltAndHash){
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(saltAndHash.getSalt());
    }
}
