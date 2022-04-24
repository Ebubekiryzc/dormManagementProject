package tr.edu.duzce.mf.bm.business.concretes;

import jakarta.inject.Scope;
import jakarta.inject.Singleton;
import tr.edu.duzce.mf.bm.business.abstracts.FacultyService;
import tr.edu.duzce.mf.bm.core.dataAccess.constants.Messages;
import tr.edu.duzce.mf.bm.core.utilities.business.BusinessRules;
import tr.edu.duzce.mf.bm.core.utilities.results.*;
import tr.edu.duzce.mf.bm.dataAccess.abstracts.FacultyDao;
import tr.edu.duzce.mf.bm.entities.concretes.Faculty;

import java.util.List;

public class FacultyManager implements FacultyService {

    private FacultyDao facultyDao;

    public FacultyManager(FacultyDao facultyDao) {
        this.facultyDao = facultyDao;
    }

    @Override
    public DataResult<List<Faculty>> getAll() {
        return new SuccessDataResult<>(facultyDao.getAll(), Messages.OperationSuccessful);
    }

    @Override
    public DataResult<Faculty> getById(int id) {
        Faculty faculty = facultyDao.getById(id);
        var result = BusinessRules.check(isNull(faculty));
        if (!result.isSuccess()) {
            return (ErrorDataResult<Faculty>) result;
        }
        return new SuccessDataResult<>(faculty, Messages.OperationSuccessful);
    }

    @Override
    public Result add(Faculty faculty) {
        boolean isAdded = facultyDao.add(faculty);
        if (isAdded) return new SuccessResult(Messages.OperationSuccessful);
        return new ErrorResult(Messages.OperationFailed);
    }

    @Override
    public Result update(Faculty faculty) {
        boolean isUpdated = facultyDao.update(faculty);
        if (isUpdated) return new SuccessResult(Messages.OperationSuccessful);
        return new ErrorResult(Messages.OperationFailed);
    }

    @Override
    public Result delete(Faculty faculty) {
        boolean isDeleted = facultyDao.delete(faculty);
        if (isDeleted) return new SuccessResult(Messages.OperationSuccessful);
        return new ErrorResult(Messages.OperationFailed);
    }

    public DataResult<Faculty> isNull(Faculty faculty) {
        if (faculty == null) {
            return new ErrorDataResult<>(Messages.OperationFailed);
        }
        return new SuccessDataResult<>(Messages.OperationSuccessful);
    }
}
