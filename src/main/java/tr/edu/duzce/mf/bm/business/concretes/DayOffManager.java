package tr.edu.duzce.mf.bm.business.concretes;

import tr.edu.duzce.mf.bm.business.abstracts.DayOffService;
import tr.edu.duzce.mf.bm.core.dataAccess.constants.Messages;
import tr.edu.duzce.mf.bm.core.utilities.business.BusinessRules;
import tr.edu.duzce.mf.bm.core.utilities.results.*;
import tr.edu.duzce.mf.bm.dataAccess.abstracts.DayOffDao;
import tr.edu.duzce.mf.bm.entities.concretes.DayOff;

import java.util.List;

public class DayOffManager implements DayOffService {

    private DayOffDao dayOffDao;

    public DayOffManager(DayOffDao dayOffDao) {
        this.dayOffDao = dayOffDao;
    }

    @Override
    public DataResult<List<DayOff>> getAll() {
        return new SuccessDataResult<>(dayOffDao.getAll(), Messages.OperationSuccessful);
    }

    @Override
    public DataResult<DayOff> getById(int id) {
        DayOff dayOff = dayOffDao.getById(id);
        var result = BusinessRules.check(isNull(dayOff));
        if (!result.isSuccess()) {
            return (ErrorDataResult<DayOff>) result;
        }
        return new SuccessDataResult<>(dayOff, Messages.OperationSuccessful);
    }

    @Override
    public Result add(DayOff dayOff) {
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
