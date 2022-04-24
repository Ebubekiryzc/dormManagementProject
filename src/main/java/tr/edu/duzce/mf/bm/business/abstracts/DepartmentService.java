package tr.edu.duzce.mf.bm.business.abstracts;

import jakarta.inject.Scope;
import tr.edu.duzce.mf.bm.core.utilities.results.DataResult;
import tr.edu.duzce.mf.bm.core.utilities.results.Result;
import tr.edu.duzce.mf.bm.entities.concretes.Department;

import java.util.List;

public interface DepartmentService {
    public DataResult<List<Department>> getAll();

    public DataResult<Department> getById(int id);

    public Result add(Department department);

    public Result update(Department department);

    public Result delete(Department department);
}
