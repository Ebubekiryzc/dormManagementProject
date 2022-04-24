package tr.edu.duzce.mf.bm.business.concretes;

import tr.edu.duzce.mf.bm.business.abstracts.UserService;
import tr.edu.duzce.mf.bm.core.dataAccess.abstracts.UserDao;
import tr.edu.duzce.mf.bm.core.dataAccess.concretes.JDBCUserDao;
import tr.edu.duzce.mf.bm.core.dataAccess.constants.Messages;
import tr.edu.duzce.mf.bm.core.entities.concrete.User;
import tr.edu.duzce.mf.bm.core.utilities.business.BusinessRules;
import tr.edu.duzce.mf.bm.core.utilities.results.*;
import tr.edu.duzce.mf.bm.entities.concretes.Student;

import java.util.List;

public class UserManager implements UserService {

    private UserDao userDao;

    public UserManager(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public DataResult<List<User>> getAll() {
        return new SuccessDataResult<>(userDao.getAll(), Messages.OperationSuccessful);
    }

    @Override
    public DataResult<User> getById(int id) {
        User user = userDao.getById(id);
        var result = BusinessRules.check(isNull(user));
        if (!result.isSuccess()) {
            return (ErrorDataResult<User>) result;
        }
        return new SuccessDataResult<>(user, Messages.OperationSuccessful);
    }

    @Override
    public DataResult<User> getByUsername(String username) {
        return new SuccessDataResult<>(userDao.getByUsername(username), Messages.OperationSuccessful);
    }

    @Override
    public Result add(User user) {
        boolean isAdded = userDao.add(user);
        if (isAdded) return new SuccessResult(Messages.OperationSuccessful);
        return new ErrorResult(Messages.OperationFailed);
    }

    @Override
    public Result update(User user) {
        boolean isUpdated = userDao.update(user);
        if (isUpdated) return new SuccessResult(Messages.OperationSuccessful);
        return new ErrorResult(Messages.OperationFailed);
    }

    @Override
    public Result delete(User user) {
        boolean isDeleted = userDao.delete(user);
        if (isDeleted) return new SuccessResult(Messages.OperationSuccessful);
        return new ErrorResult(Messages.OperationFailed);
    }

    private DataResult<User> isNull(User user) {
        if (user == null) {
            return new ErrorDataResult<>(Messages.OperationFailed);
        }
        return new SuccessDataResult<>(Messages.OperationSuccessful);
    }
}
