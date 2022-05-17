package tr.edu.duzce.mf.bm.business.concretes;

import tr.edu.duzce.mf.bm.business.abstracts.DayOffService;
import tr.edu.duzce.mf.bm.business.abstracts.OperationClaimService;
import tr.edu.duzce.mf.bm.core.dataAccess.constants.Messages;
import tr.edu.duzce.mf.bm.core.entities.concrete.OperationClaim;
import tr.edu.duzce.mf.bm.core.utilities.business.BusinessRules;
import tr.edu.duzce.mf.bm.core.utilities.results.*;
import tr.edu.duzce.mf.bm.dataAccess.abstracts.DayOffDao;
import tr.edu.duzce.mf.bm.entities.concretes.DayOff;
import tr.edu.duzce.mf.bm.entities.dtos.DayOffDetailDto;

import java.util.List;

public class DayOffManager implements DayOffService {

    private OperationClaimService operationClaimService;
    private DayOffDao dayOffDao;

    public DayOffManager(OperationClaimService operationClaimService, DayOffDao dayOffDao) {
        this.operationClaimService = operationClaimService;
        this.dayOffDao = dayOffDao;
    }

    @Override
    public DataResult<List<DayOff>> getAll() {
        return new SuccessDataResult<>(dayOffDao.getAll(), Messages.OperationSuccessful);
    }

    @Override
    public DataResult<List<DayOffDetailDto>> getAllWithRole(String role) {
        OperationClaim operationClaim = operationClaimService.getByName(role).getEntity();
        if (operationClaim == null) {
            return new ErrorDataResult<>(null, Messages.OperationFailed);
        }
        return new SuccessDataResult<>(dayOffDao.getDayOffsWithAccessLevel(operationClaim.getName(), "0"), Messages.OperationSuccessful);
    }

    @Override
    public DataResult<List<DayOffDetailDto>> getAllActiveWithRole(String role) {
        OperationClaim operationClaim = operationClaimService.getByName(role).getEntity();
        if (operationClaim == null) {
            return new ErrorDataResult<>(null, Messages.OperationFailed);
        }
        return new SuccessDataResult<>(dayOffDao.getDayOffsWithAccessLevel(operationClaim.getName(), "1"), Messages.OperationSuccessful);
    }

    @Override
    public DataResult<List<DayOff>> getAllByUserId(String userId) {
        return new SuccessDataResult<>(dayOffDao.getDayOffsByUserId(userId), Messages.OperationSuccessful);
    }

    @Override
    public DataResult<List<DayOff>> getAllActivesByUserId(String userId) {
        return new SuccessDataResult<>(dayOffDao.getActiveDayOffsByUserId(userId));
    }


    @Override
    public DataResult<DayOff> getByDateRangeAndUserId(String dateOfStart, String dateOfEnd, String userId) {
        return new SuccessDataResult<>(dayOffDao.getDayOffWithUserIdAtRange(dateOfStart, dateOfEnd, userId), Messages.OperationSuccessful);
    }

    @Override
    public DataResult<DayOff> getById(int id) {
        DayOff dayOff = dayOffDao.getById(String.valueOf(id));
        var result = BusinessRules.check(isNull(dayOff));
        if (!result.isSuccess()) {
            return (ErrorDataResult<DayOff>) result;
        }
        return new SuccessDataResult<>(dayOff, Messages.OperationSuccessful);
    }

    @Override
    public Result add(DayOff dayOff) {
        var result = BusinessRules.check(isNull(dayOffDao.getDayOffWithUserIdAtRange(dayOff.getDateOfStart(), dayOff.getDateOfEnd(), dayOff.getUserId().toString())));
        if (result.isSuccess()) {
            return new ErrorResult(Messages.RangeInvalid);
        }

        boolean isAdded = dayOffDao.add(dayOff);
        if (isAdded) return new SuccessResult(Messages.OperationSuccessful);
        return new ErrorResult(Messages.OperationFailed);
    }

    @Override
    public Result update(DayOff dayOff) {
        boolean isUpdated = dayOffDao.update(dayOff);
        if (isUpdated) return new SuccessResult(Messages.OperationSuccessful);
        return new ErrorResult(Messages.OperationFailed);
    }

    @Override
    public Result delete(DayOff dayOff) {
        boolean isDeleted = dayOffDao.delete(dayOff);
        if (isDeleted) return new SuccessResult(Messages.OperationSuccessful);
        return new ErrorResult(Messages.OperationFailed);
    }

    private DataResult<DayOff> isNull(DayOff dayOff) {
        if (dayOff == null) {
            return new ErrorDataResult<>(Messages.OperationFailed);
        }
        return new SuccessDataResult<>(Messages.OperationSuccessful);
    }
}
