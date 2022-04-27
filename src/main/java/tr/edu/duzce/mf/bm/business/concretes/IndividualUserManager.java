package tr.edu.duzce.mf.bm.business.concretes;

import jakarta.inject.Scope;
import jakarta.inject.Singleton;
import tr.edu.duzce.mf.bm.business.abstracts.IndividualUserService;
import tr.edu.duzce.mf.bm.business.abstracts.UserService;
import tr.edu.duzce.mf.bm.core.dataAccess.constants.Messages;
import tr.edu.duzce.mf.bm.core.utilities.business.BusinessRules;
import tr.edu.duzce.mf.bm.core.utilities.results.*;
import tr.edu.duzce.mf.bm.dataAccess.abstracts.IndividualUserDao;
import tr.edu.duzce.mf.bm.entities.concretes.IndividualUser;

import java.util.List;

public class IndividualUserManager implements IndividualUserService {

    private IndividualUserDao individualUserDao;

    public IndividualUserManager(IndividualUserDao individualUserDao) {
        this.individualUserDao = individualUserDao;
    }

    @Override
    public DataResult<List<IndividualUser>> getAll() {
        return new SuccessDataResult<>(individualUserDao.getAll(), Messages.OperationSuccessful);
    }

    @Override
    public DataResult<IndividualUser> getById(int id) {
        IndividualUser individualUser = individualUserDao.getById(String.valueOf(id));
        var result = BusinessRules.check(isNull(individualUser));
        if (!result.isSuccess()) {
            return (ErrorDataResult<IndividualUser>) result;
        }
        return new SuccessDataResult<>(individualUser, Messages.OperationSuccessful);
    }

    @Override
    public Result add(IndividualUser individualUser) {
        boolean isAdded = individualUserDao.add(individualUser);
        if (isAdded) return new SuccessResult(Messages.OperationSuccessful);
        return new ErrorResult(Messages.OperationFailed);
    }

    @Override
    public Result update(IndividualUser individualUser) {
        boolean isUpdated = individualUserDao.update(individualUser);
        if (isUpdated) return new SuccessResult(Messages.OperationSuccessful);
        return new ErrorResult(Messages.OperationFailed);
    }

    @Override
    public Result delete(IndividualUser individualUser) {
        boolean isDeleted = individualUserDao.delete(individualUser);
        if (isDeleted) return new SuccessResult(Messages.OperationSuccessful);
        return new ErrorResult(Messages.OperationFailed);
    }

    public DataResult<IndividualUser> isNull(IndividualUser individualUser) {
        if (individualUser == null) {
            return new ErrorDataResult<>(Messages.OperationFailed);
        }
        return new SuccessDataResult<>(Messages.OperationSuccessful);
    }
}
