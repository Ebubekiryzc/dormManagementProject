package tr.edu.duzce.mf.bm.core.utilities.results;

public class ErrorDataResult<TEntity> extends DataResult<TEntity> {
    public ErrorDataResult() {
        super(null, false);
    }

    public ErrorDataResult(String message) {
        super(null, message, false);
    }

    public ErrorDataResult(TEntity entity) {
        super(entity, false);
    }

    public ErrorDataResult(TEntity entity, String message) {
        super(entity, message, false);
    }
}
