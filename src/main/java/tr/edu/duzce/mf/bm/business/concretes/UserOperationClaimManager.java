package tr.edu.duzce.mf.bm.business.concretes;

import tr.edu.duzce.mf.bm.business.abstracts.UserOperationClaimService;
import tr.edu.duzce.mf.bm.core.dataAccess.abstracts.UserOperationClaimDao;
import tr.edu.duzce.mf.bm.core.dataAccess.constants.Messages;
import tr.edu.duzce.mf.bm.core.entities.concrete.User;
import tr.edu.duzce.mf.bm.core.entities.concrete.UserOperationClaim;
import tr.edu.duzce.mf.bm.core.utilities.business.BusinessRules;
import tr.edu.duzce.mf.bm.core.utilities.results.*;

import java.util.List;

public class UserOperationClaimManager implements UserOperationClaimService {
    private UserOperationClaimDao userOperationClaimDao;

    public UserOperationClaimManager(UserOperationClaimDao userOperationClaimDao) {
        this.userOperationClaimDao = userOperationClaimDao;
    }

    @Override
    public DataResult<List<UserOperationClaim>> getAll() {
        return new SuccessDataResult<>(userOperationClaimDao.getAll(), Messages.OperationSuccessful);
    }

    @Override
    public DataResult<UserOperationClaim> getById(int id) {
        UserOperationClaim userOperationClaim = userOperationClaimDao.getById(id);
        var result = BusinessRules.check(isNull(userOperationClaim));
        if (!result.isSuccess()) {
            return (ErrorDataResult<UserOperationClaim>) result;
        }
        return new SuccessDataResult<>(userOperationClaim, Messages.OperationSuccessful);
    }

    @Override
    public Result add(UserOperationClaim userOperationClaim) {
        boolean isAdded = userOperationClaimDao.add(userOperationClaim);
        if (isAdded) return new SuccessResult(Messages.OperationSuccessful);
        return new ErrorResult(Messages.OperationFailed);
    }

    @Override
    public Result update(UserOperationClaim userOperationClaim) {
        boolean isUpdated = userOperationClaimDao.update(userOperationClaim);
        if (isUpdated) return new SuccessResult(Messages.OperationSuccessful);
        return new ErrorResult(Messages.OperationFailed);
    }

    @Override
    public Result delete(UserOperationClaim userOperationClaim) {
        boolean isDeleted = userOperationClaimDao.delete(userOperationClaim);
        if (isDeleted) return new SuccessResult(Messages.OperationSuccessful);
        return new ErrorResult(Messages.OperationFailed);
    }

    private DataResult<UserOperationClaim> isNull(UserOperationClaim userOperationClaim) {
        if (userOperationClaim == null) {
            return new ErrorDataResult<>(Messages.OperationFailed);
        }
        return new SuccessDataResult<>(Messages.OperationSuccessful);
    }
}
