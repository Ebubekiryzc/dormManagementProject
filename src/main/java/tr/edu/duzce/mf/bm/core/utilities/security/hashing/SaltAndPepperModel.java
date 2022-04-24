package tr.edu.duzce.mf.bm.core.utilities.security.hashing;

import lombok.Data;

@Data
public class SaltAndPepperModel {
    private byte[] salt;
    private byte[] hash;

    public SaltAndPepperModel() {
        this.salt = new byte[16];
    }
}
