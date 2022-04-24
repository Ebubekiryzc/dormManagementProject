package tr.edu.duzce.mf.bm.business.abstracts;

import jakarta.inject.Scope;
import tr.edu.duzce.mf.bm.core.utilities.results.DataResult;
import tr.edu.duzce.mf.bm.core.utilities.results.Result;
import tr.edu.duzce.mf.bm.entities.concretes.IndividualUser;

import java.util.List;

public interface IndividualUserService {
    public DataResult<List<IndividualUser>> getAll();

    public DataResult<IndividualUser> getById(int id);

    public Result add(IndividualUser individualUser);

    public Result update(IndividualUser individualUser);

    public Result delete(IndividualUser individualUser);
}
