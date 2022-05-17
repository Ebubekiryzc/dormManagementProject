package tr.edu.duzce.mf.bm.business.abstracts;

import jakarta.inject.Scope;
import tr.edu.duzce.mf.bm.core.utilities.results.DataResult;
import tr.edu.duzce.mf.bm.core.utilities.results.Result;
import tr.edu.duzce.mf.bm.entities.concretes.Gender;

import java.util.List;

public interface GenderService {
    public DataResult<List<Gender>> getAll();

    public DataResult<Gender> getById(int id);

    public DataResult<Gender> getByName(String name);

    public Result add(Gender gender);

    public Result update(Gender gender);

    public Result delete(Gender gender);
}
