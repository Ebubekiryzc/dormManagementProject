package tr.edu.duzce.mf.bm.business.abstracts;

import jakarta.inject.Scope;
import tr.edu.duzce.mf.bm.core.utilities.results.DataResult;
import tr.edu.duzce.mf.bm.core.utilities.results.Result;
import tr.edu.duzce.mf.bm.entities.concretes.Faculty;

import java.util.List;

public interface FacultyService {
    public DataResult<List<Faculty>> getAll();

    public DataResult<Faculty> getById(int id);

    public Result add(Faculty faculty);

    public Result update(Faculty faculty);

    public Result delete(Faculty faculty);
}
