package tr.edu.duzce.mf.bm.business.concretes;

import jakarta.inject.Scope;
import jakarta.inject.Singleton;
import tr.edu.duzce.mf.bm.business.abstracts.GenderService;
import tr.edu.duzce.mf.bm.core.dataAccess.constants.Messages;
import tr.edu.duzce.mf.bm.core.utilities.business.BusinessRules;
import tr.edu.duzce.mf.bm.core.utilities.results.*;
import tr.edu.duzce.mf.bm.dataAccess.abstracts.GenderDao;
import tr.edu.duzce.mf.bm.entities.concretes.Gender;

import java.util.List;

public class GenderManager implements GenderService {

    GenderDao genderDao;

    public GenderManager(GenderDao genderDao) {
        this.genderDao = genderDao;
    }

    @Override
    public DataResult<List<Gender>> getAll() {
        return new SuccessDataResult<>(genderDao.getAll(), Messages.OperationSuccessful);
    }

    @Override
    public DataResult<Gender> getById(int id) {
        Gender gender = genderDao.getById(id);
        var result = BusinessRules.check(isNull(gender));
        if (!result.isSuccess()) {
            return (ErrorDataResult<Gender>) result;
        }
        return new SuccessDataResult<>(gender, Messages.OperationSuccessful);
    }

    @Override
    public Result add(Gender gender) {
        boolean isAdded = genderDao.add(gender);
        if (isAdded) return new SuccessResult(Messages.OperationSuccessful);
        return new ErrorResult(Messages.OperationFailed);
    }

    @Override
    public Result update(Gender gender) {
        boolean isUpdated = genderDao.update(gender);
        if (isUpdated) return new SuccessResult(Messages.OperationSuccessful);
        return new ErrorResult(Messages.OperationFailed);
    }

    @Override
    public Result delete(Gender gender) {
        boolean isDeleted = genderDao.delete(gender);
        if (isDeleted) return new SuccessResult(Messages.OperationSuccessful);
        return new ErrorResult(Messages.OperationFailed);
    }

    public DataResult<Gender> isNull(Gender gender) {
        if (gender == null) {
            return new ErrorDataResult<>(Messages.OperationFailed);
        }
        return new SuccessDataResult<>(Messages.OperationSuccessful);
    }
}
