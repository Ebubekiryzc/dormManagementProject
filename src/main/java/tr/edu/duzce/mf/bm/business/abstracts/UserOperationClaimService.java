package tr.edu.duzce.mf.bm.business.abstracts;

import tr.edu.duzce.mf.bm.core.entities.concrete.UserOperationClaim;
import tr.edu.duzce.mf.bm.core.utilities.results.DataResult;
import tr.edu.duzce.mf.bm.core.utilities.results.Result;
import tr.edu.duzce.mf.bm.entities.concretes.Student;

import java.util.List;

public interface UserOperationClaimService {
    public DataResult<List<UserOperationClaim>> getAll();

    public DataResult<UserOperationClaim> getById(int id);

    public Result add(UserOperationClaim userOperationClaim);

    public Result update(UserOperationClaim userOperationClaim);

    public Result delete(UserOperationClaim userOperationClaim);
}
