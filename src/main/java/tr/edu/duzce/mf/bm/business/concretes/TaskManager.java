package tr.edu.duzce.mf.bm.business.concretes;

import jakarta.inject.Scope;
import jakarta.inject.Singleton;
import tr.edu.duzce.mf.bm.business.abstracts.TaskService;
import tr.edu.duzce.mf.bm.core.dataAccess.constants.Messages;
import tr.edu.duzce.mf.bm.core.utilities.business.BusinessRules;
import tr.edu.duzce.mf.bm.core.utilities.results.*;
import tr.edu.duzce.mf.bm.dataAccess.abstracts.TaskDao;
import tr.edu.duzce.mf.bm.entities.concretes.Task;

import java.util.List;

public class TaskManager implements TaskService {
    private TaskDao taskDao;

    public TaskManager(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @Override
    public DataResult<List<Task>> getAll() {
        return new SuccessDataResult<>(taskDao.getAll(), Messages.OperationSuccessful);
    }

    @Override
    public DataResult<Task> getById(int id) {
        Task task = taskDao.getById(String.valueOf(id));
        var result = BusinessRules.check(isNull(task));
        if (!result.isSuccess()) {
            return (ErrorDataResult<Task>) result;
        }
        return new SuccessDataResult<>(task, Messages.OperationSuccessful);
    }

    @Override
    public Result add(Task task) {
        boolean isAdded = taskDao.add(task);
        if (isAdded) return new SuccessResult(Messages.OperationSuccessful);
        return new ErrorResult(Messages.OperationFailed);
    }

    @Override
    public Result update(Task task) {
        boolean isUpdated = taskDao.update(task);
        if (isUpdated) return new SuccessResult(Messages.OperationSuccessful);
        return new ErrorResult(Messages.OperationFailed);
    }

    @Override
    public Result delete(Task task) {
        boolean isDeleted = taskDao.delete(task);
        if (isDeleted) return new SuccessResult(Messages.OperationSuccessful);
        return new ErrorResult(Messages.OperationFailed);
    }

    public DataResult<Task> isNull(Task task) {
        if (task == null) {
            return new ErrorDataResult<>(Messages.OperationFailed);
        }
        return new SuccessDataResult<>(Messages.OperationSuccessful);
    }
}
