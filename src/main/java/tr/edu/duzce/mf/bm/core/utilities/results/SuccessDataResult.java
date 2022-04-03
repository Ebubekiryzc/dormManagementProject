package tr.edu.duzce.mf.bm.core.utilities.results;

public class SuccessDataResult<TEntity> extends DataResult<TEntity> {

    public SuccessDataResult() {
        super(null, true);
    }

    public SuccessDataResult(String message) {
        super(null, message, true);
    }


    public SuccessDataResult(TEntity entity) {
        super(entity, true);
    }

    public SuccessDataResult(TEntity entity, String message) {
        super(entity, message, true);
    }
}
