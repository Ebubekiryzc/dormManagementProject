package tr.edu.duzce.mf.bm.business.concretes;

import tr.edu.duzce.mf.bm.business.abstracts.OperationClaimService;
import tr.edu.duzce.mf.bm.core.dataAccess.abstracts.OperationClaimDao;
import tr.edu.duzce.mf.bm.core.dataAccess.constants.Messages;
import tr.edu.duzce.mf.bm.core.entities.concrete.OperationClaim;
import tr.edu.duzce.mf.bm.core.entities.concrete.User;
import tr.edu.duzce.mf.bm.core.utilities.business.BusinessRules;
import tr.edu.duzce.mf.bm.core.utilities.results.*;
import tr.edu.duzce.mf.bm.entities.concretes.Gender;

import java.util.List;

public class OperationClaimManager implements OperationClaimService {

    private OperationClaimDao operationClaimDao;

    public OperationClaimManager(OperationClaimDao operationClaimDao) {
        this.operationClaimDao = operationClaimDao;
    }

    @Override
    public DataResult<List<OperationClaim>> getAll() {
        return new SuccessDataResult<>(operationClaimDao.getAll(), Messages.OperationSuccessful);
    }

    @Override
    public DataResult<List<OperationClaim>> getUserClaims(User user) {
        return new SuccessDataResult<>(operationClaimDao.getClaims(user), Messages.OperationSuccessful);
    }

    @Override
    public DataResult<OperationClaim> getById(int id) {
        OperationClaim operationClaim = operationClaimDao.getById(String.valueOf(id));
        var result = BusinessRules.check(isNull(operationClaim));
        if (!result.isSuccess()) {
            return (ErrorDataResult<OperationClaim>) result;
        }
        return new SuccessDataResult<>(operationClaim, Messages.OperationSuccessful);
    }

    @Override
    public DataResult<OperationClaim> getByName(String name) {
        OperationClaim operationClaim = operationClaimDao.getClaimByName(name);
        var result = BusinessRules.check(isNull(operationClaim));
        if(!result.isSuccess()){
            return (ErrorDataResult<OperationClaim>) result;
        }
        return new SuccessDataResult<>(operationClaim, Messages.OperationSuccessful);
    }

    @Override
    public Result add(OperationClaim operationClaim) {
        boolean isAdded = operationClaimDao.add(operationClaim);
        if (isAdded) return new SuccessResult(Messages.OperationSuccessful);
        return new ErrorResult(Messages.OperationFailed);
    }

    @Override
    public Result update(OperationClaim operationClaim) {
        boolean isUpdated = operationClaimDao.update(operationClaim);
        if (isUpdated) return new SuccessResult(Messages.OperationSuccessful);
        return new ErrorResult(Messages.OperationFailed);
    }

    @Override
    public Result delete(OperationClaim operationClaim) {
        boolean isDeleted = operationClaimDao.delete(operationClaim);
        if (isDeleted) return new SuccessResult(Messages.OperationSuccessful);
        return new ErrorResult(Messages.OperationFailed);
    }

    private DataResult<Gender> isNull(OperationClaim operationClaim) {
        if (operationClaim == null) {
            return new ErrorDataResult<>(Messages.OperationFailed);
        }
        return new SuccessDataResult<>(Messages.OperationSuccessful);
    }
}
