package tr.edu.duzce.mf.bm.business.concretes;

import tr.edu.duzce.mf.bm.business.abstracts.StaffTaskService;
import tr.edu.duzce.mf.bm.core.dataAccess.constants.Messages;
import tr.edu.duzce.mf.bm.core.utilities.business.BusinessRules;
import tr.edu.duzce.mf.bm.core.utilities.results.*;
import tr.edu.duzce.mf.bm.dataAccess.abstracts.StaffTaskDao;
import tr.edu.duzce.mf.bm.entities.concretes.StaffTask;

import java.util.List;

public class StaffTaskManager implements StaffTaskService {

    private StaffTaskDao staffTaskDao;

    public StaffTaskManager(StaffTaskDao staffTaskDao) {
        this.staffTaskDao = staffTaskDao;
    }

    @Override
    public DataResult<List<StaffTask>> getAll() {
        return new SuccessDataResult<>(staffTaskDao.getAll(), Messages.OperationSuccessful);
    }

    @Override
    public DataResult<StaffTask> getById(int id) {
        StaffTask staffTask = staffTaskDao.getById(id);
        var result = BusinessRules.check(isNull(staffTask));
        if (!result.isSuccess()) {
            return (ErrorDataResult<StaffTask>) result;
        }
        return new SuccessDataResult<>(staffTask, Messages.OperationSuccessful);
    }

    @Override
    public Result add(StaffTask staffTask) {
        boolean isAdded = staffTaskDao.add(staffTask);
        if (isAdded) return new SuccessResult(Messages.OperationSuccessful);
        return new ErrorResult(Messages.OperationFailed);
    }

    @Override
    public Result update(StaffTask staffTask) {
        boolean isUpdated = staffTaskDao.update(staffTask);
        if (isUpdated) return new SuccessResult(Messages.OperationSuccessful);
        return new ErrorResult(Messages.OperationFailed);
    }

    @Override
    public Result delete(StaffTask staffTask) {
        boolean isDeleted = staffTaskDao.delete(staffTask);
        if (isDeleted) return new SuccessResult(Messages.OperationSuccessful);
        return new ErrorResult(Messages.OperationFailed);
    }

    public DataResult<StaffTask> isNull(StaffTask staffTask) {
        if (staffTask == null) {
            return new ErrorDataResult<>(Messages.OperationFailed);
        }
        return new SuccessDataResult<>(Messages.OperationSuccessful);
    }
}
