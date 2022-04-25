package tr.edu.duzce.mf.bm.business.concretes;

import tr.edu.duzce.mf.bm.business.abstracts.StudentService;
import tr.edu.duzce.mf.bm.business.abstracts.UserOperationClaimService;
import tr.edu.duzce.mf.bm.core.dataAccess.constants.Messages;
import tr.edu.duzce.mf.bm.core.utilities.business.BusinessRules;
import tr.edu.duzce.mf.bm.core.utilities.results.*;
import tr.edu.duzce.mf.bm.dataAccess.abstracts.StudentDao;
import tr.edu.duzce.mf.bm.entities.concretes.Gender;
import tr.edu.duzce.mf.bm.entities.concretes.Student;

import java.util.List;

public class StudentManager implements StudentService {

    private StudentDao studentDao;

    public StudentManager(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Override
    public DataResult<List<Student>> getAll() {
        return new SuccessDataResult<>(studentDao.getAll(),Messages.OperationSuccessful);
    }

    @Override
    public DataResult<Student> getById(int id) {
        Student student = studentDao.getById(id);
        var result = BusinessRules.check(isNull(student));
        if (!result.isSuccess()) {
            return (ErrorDataResult<Student>) result;
        }
        return new SuccessDataResult<>(student, Messages.OperationSuccessful);
    }

    @Override
    public Result add(Student student) {
        boolean isAdded = studentDao.add(student);
        if (isAdded) return new SuccessResult(Messages.OperationSuccessful);
        return new ErrorResult(Messages.OperationFailed);
    }

    @Override
    public Result update(Student student) {
        boolean isUpdated = studentDao.update(student);
        if (isUpdated) return new SuccessResult(Messages.OperationSuccessful);
        return new ErrorResult(Messages.OperationFailed);
    }

    @Override
    public Result delete(Student student) {
        boolean isDeleted = studentDao.delete(student);
        if (isDeleted) return new SuccessResult(Messages.OperationSuccessful);
        return new ErrorResult(Messages.OperationFailed);
    }

    private DataResult<Student> isNull(Student student) {
        if (student == null) {
            return new ErrorDataResult<>(Messages.OperationFailed);
        }
        return new SuccessDataResult<>(Messages.OperationSuccessful);
    }
}
