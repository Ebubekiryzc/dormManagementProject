package tr.edu.duzce.mf.bm.business.abstracts;

import tr.edu.duzce.mf.bm.core.entities.concrete.User;
import tr.edu.duzce.mf.bm.core.utilities.results.DataResult;
import tr.edu.duzce.mf.bm.core.utilities.results.Result;
import tr.edu.duzce.mf.bm.entities.concretes.Gender;

import java.util.List;

public interface UserService {
    public DataResult<List<User>> getAll();
    public DataResult<User> getById(int id);
    public DataResult<User> getByUsername(String username);
    public Result add(User user);
    public Result update(User user);
    public Result delete(User user);
}
