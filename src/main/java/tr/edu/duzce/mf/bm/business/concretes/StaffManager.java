package tr.edu.duzce.mf.bm.business.concretes;

import tr.edu.duzce.mf.bm.business.abstracts.StaffService;
import tr.edu.duzce.mf.bm.core.dataAccess.constants.Messages;
import tr.edu.duzce.mf.bm.core.utilities.business.BusinessRules;
import tr.edu.duzce.mf.bm.core.utilities.results.*;
import tr.edu.duzce.mf.bm.dataAccess.abstracts.StaffDao;
import tr.edu.duzce.mf.bm.entities.concretes.Staff;
import tr.edu.duzce.mf.bm.entities.dtos.StaffDetailDto;

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
    public DataResult<List<StaffDetailDto>> getAllStaffDetails() {
        return new SuccessDataResult<>(staffDao.getAllStaffDetails(), Messages.OperationSuccessful);
    }

    @Override
    public DataResult<List<StaffDetailDto>> getByFullName(String firstName, String lastName) {
        List<StaffDetailDto> staffList = staffDao.getStaffDetailsByFullName(firstName, lastName);
        if (staffList == null) return new ErrorDataResult<>(null, Messages.OperationFailed);
        return new SuccessDataResult<>(staffList, Messages.OperationSuccessful);
    }

    @Override
    public DataResult<List<StaffDetailDto>> getByFirstName(String firstName) {
        List<StaffDetailDto> staffList = staffDao.getStaffDetailsByFirstName(firstName);
        if (staffList == null) return new ErrorDataResult<>(null, Messages.OperationFailed);
        return new SuccessDataResult<>(staffList, Messages.OperationSuccessful);
    }

    @Override
    public DataResult<List<StaffDetailDto>> getByLastName(String lastName) {
        List<StaffDetailDto> staffList = staffDao.getStaffDetailsByLastName(lastName);
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
