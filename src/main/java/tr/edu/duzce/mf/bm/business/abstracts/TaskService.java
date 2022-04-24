package tr.edu.duzce.mf.bm.business.abstracts;

import jakarta.inject.Scope;
import tr.edu.duzce.mf.bm.core.utilities.results.DataResult;
import tr.edu.duzce.mf.bm.core.utilities.results.Result;
import tr.edu.duzce.mf.bm.entities.concretes.Task;

import java.util.List;

public interface TaskService {
    public DataResult<List<Task>> getAll();

    public DataResult<Task> getById(int id);

    public Result add(Task task);

    public Result update(Task task);

    public Result delete(Task task);
}
