package tr.edu.duzce.mf.bm.core.utilities.results;

import lombok.Getter;


public class DataResult<TEntity> extends Result {
    @Getter
    TEntity entity;

    public DataResult(TEntity entity, boolean success) {
        super(success);
        this.entity = entity;
    }

    public DataResult(TEntity entity, String message, boolean success) {
        super(message, success);
        this.entity = entity;
    }
}
