package tr.edu.duzce.mf.bm.business.abstracts;

import jakarta.inject.Scope;
import tr.edu.duzce.mf.bm.core.utilities.results.DataResult;
import tr.edu.duzce.mf.bm.core.utilities.results.Result;
import tr.edu.duzce.mf.bm.entities.concretes.StaffTask;

import java.util.List;

public interface StaffTaskService {
    public DataResult<List<StaffTask>> getAll();

    public DataResult<StaffTask> getById(int id);

    public Result add(StaffTask staffTask);

    public Result update(StaffTask staffTask);

    public Result delete(StaffTask staffTask);
}
