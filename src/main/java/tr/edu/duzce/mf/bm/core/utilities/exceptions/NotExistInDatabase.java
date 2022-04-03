package tr.edu.duzce.mf.bm.core.utilities.exceptions;

public class NotExistInDatabase extends Exception {

    public NotExistInDatabase() {
        super("Bu kayıt veri tabanında bulunmuyor.");
    }
}
