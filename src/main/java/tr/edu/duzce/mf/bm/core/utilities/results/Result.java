package tr.edu.duzce.mf.bm.core.utilities.results;

import lombok.Getter;

public class Result {
    @Getter
    private boolean success;
    @Getter
    private String message;

    public Result(boolean success) {
        this.success = success;
    }

    public Result(String message, boolean success) {
        this(success);
        this.message = message;
    }
}
