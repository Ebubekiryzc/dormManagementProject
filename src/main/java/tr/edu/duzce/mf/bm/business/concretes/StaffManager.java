package tr.edu.duzce.mf.bm.business.concretes;

import tr.edu.duzce.mf.bm.business.abstracts.StaffService;
import tr.edu.duzce.mf.bm.core.dataAccess.constants.Messages;
import tr.edu.duzce.mf.bm.core.utilities.business.BusinessRules;
import tr.edu.duzce.mf.bm.core.utilities.results.*;
import tr.edu.duzce.mf.bm.dataAccess.abstracts.StaffDao;
import tr.edu.duzce.mf.bm.entities.concretes.Gender;
import tr.edu.duzce.mf.bm.entities.concretes.Staff;

import java.util.List;

public class StaffManager implements StaffService {

    private StaffDao staffDao;

    public StaffManager(StaffDao staffDao) {
        this.staffDao = staffDao;
    }

    @Override
    public DataResult<List<Staff>> getAll() {
        return new SuccessDataResult<>(staffDao.getAll(), Messages.OperationSuccessful);
    }

    @Override
    public DataResult<List<Staff>> getByFullName(String firstName, String lastName) {
        List<Staff> staffList = staffDao.getByFullName(firstName, lastName);
        if (staffList == null) return new ErrorDataResult<>(null, Messages.OperationFailed);
        return new SuccessDataResult<>(staffList, Messages.OperationSuccessful);
    }

    @Override
    public DataResult<List<Staff>> getByFirstName(String firstName) {
        List<Staff> staffList = staffDao.getByFirstName(firstName);
        if (staffList == null) return new ErrorDataResult<>(null, Messages.OperationFailed);
        return new SuccessDataResult<>(staffList, Messages.OperationSuccessful);
    }

    @Override
    public DataResult<List<Staff>> getByLastName(String lastName) {
        List<Staff> staffList = staffDao.getByLastName(lastName);
        if (staffList == null) return new ErrorDataResult<>(null, Messages.OperationFailed);
        return new SuccessDataResult<>(staffList, Messages.OperationSuccessful);
    }

    @Override
    public DataResult<Staff> getById(int id) {
        Staff staff = staffDao.getById(String.valueOf(id));
        var result = BusinessRules.check(isNull(staff));
        if (!result.isSuccess()) {
            return (ErrorDataResult<Staff>) result;
        }
        return new SuccessDataResult<>(staff, Messages.OperationSuccessful);
    }

    @Override
    public Result add(Staff staff) {
        boolean isAdded = staffDao.add(staff);
        if (isAdded) return new SuccessResult(Messages.OperationSuccessful);
        return new ErrorResult(Messages.OperationFailed);
    }

    @Override
    public Result update(Staff staff) {
        boolean isUpdated = staffDao.update(staff);
        if (isUpdated) return new SuccessResult(Messages.OperationSuccessful);
        return new ErrorResult(Messages.OperationFailed);
    }

    @Override
    public Result delete(Staff staff) {
        boolean isDeleted = staffDao.delete(staff);
        if (isDeleted) return new SuccessResult(Messages.OperationSuccessful);
        return new ErrorResult(Messages.OperationFailed);
    }

    private DataResult<Staff> isNull(Staff staff) {
        if (staff == null) {
            return new ErrorDataResult<>(Messages.OperationFailed);
        }
        return new SuccessDataResult<>(Messages.OperationSuccessful);
    }
}
