package tr.edu.duzce.mf.bm.core.dataAccess.abstracts;

import tr.edu.duzce.mf.bm.core.entities.concrete.User;

public interface UserDao extends BaseDao<User> {
    public User getByUsername(String username);
}
