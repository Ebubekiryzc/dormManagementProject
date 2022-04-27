package tr.edu.duzce.mf.bm.core.dataAccess.abstracts;

import java.util.List;

public interface BaseDao<TEntity> {
    public List<TEntity> getAll();

    public TEntity getById(String id);

    public boolean add(TEntity entity);

    public boolean update(TEntity entity);

    public boolean delete(TEntity entity);
}
