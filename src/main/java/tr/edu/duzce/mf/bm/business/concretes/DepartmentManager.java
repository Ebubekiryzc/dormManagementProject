package tr.edu.duzce.mf.bm.business.concretes;

import tr.edu.duzce.mf.bm.business.abstracts.DepartmentService;
import tr.edu.duzce.mf.bm.core.dataAccess.constants.Messages;
import tr.edu.duzce.mf.bm.core.utilities.business.BusinessRules;
import tr.edu.duzce.mf.bm.core.utilities.results.*;
import tr.edu.duzce.mf.bm.dataAccess.abstracts.DepartmentDao;
import tr.edu.duzce.mf.bm.entities.concretes.Department;

import java.util.List;

public class DepartmentManager implements DepartmentService {

    private DepartmentDao departmentDao;

    public DepartmentManager(DepartmentDao departmentDao) {
        this.departmentDao = departmentDao;
    }

    @Override
    public DataResult<List<Department>> getAll() {
        return new SuccessDataResult<>(departmentDao.getAll(), Messages.OperationSuccessful);
    }

    @Override
    public DataResult<Department> getById(int id) {
        Department department = departmentDao.getById(String.valueOf(id));
        var result = BusinessRules.check(isNull(department));
        if (!result.isSuccess()) {
            return (ErrorDataResult<Department>) result;
        }
        return new SuccessDataResult<>(department, Messages.OperationSuccessful);
    }

    @Override
    public Result add(Department department) {
        boolean isAdded = departmentDao.add(department);
        if (isAdded) return new SuccessResult(Messages.OperationSuccessful);
        return new ErrorResult(Messages.OperationFailed);
    }

    @Override
    public Result update(Department department) {
        boolean isUpdated = departmentDao.update(department);
        if (isUpdated) return new SuccessResult(Messages.OperationSuccessful);
        return new ErrorResult(Messages.OperationFailed);
    }

    @Override
    public Result delete(Department department) {
        boolean isDeleted = departmentDao.delete(department);
        if (isDeleted) return new SuccessResult(Messages.OperationSuccessful);
        return new ErrorResult(Messages.OperationFailed);
    }

    public DataResult<Department> isNull(Department department) {
        if (department == null) {
            return new ErrorDataResult<>(Messages.OperationFailed);
        }
        return new SuccessDataResult<>(Messages.OperationSuccessful);
    }
}
