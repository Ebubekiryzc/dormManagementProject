package tr.edu.duzce.mf.bm.business.abstracts;

import tr.edu.duzce.mf.bm.core.entities.concrete.OperationClaim;
import tr.edu.duzce.mf.bm.core.entities.concrete.User;
import tr.edu.duzce.mf.bm.core.utilities.results.DataResult;
import tr.edu.duzce.mf.bm.core.utilities.results.Result;
import tr.edu.duzce.mf.bm.entities.concretes.Staff;

import java.util.List;

public interface OperationClaimService {
    public DataResult<List<OperationClaim>> getAll();
    public DataResult<List<OperationClaim>> getUserClaims(User user);
    public DataResult<OperationClaim> getById(int id);
    public Result add(OperationClaim operationClaim);
    public Result update(OperationClaim operationClaim);
    public Result delete(OperationClaim operationClaim);
}
