package tr.edu.duzce.mf.bm.core.dataAccess.abstracts;

import tr.edu.duzce.mf.bm.core.entities.concrete.OperationClaim;
import tr.edu.duzce.mf.bm.core.entities.concrete.User;

import java.util.List;

public interface OperationClaimDao extends BaseDao<OperationClaim>{
    public List<OperationClaim> getClaims(User user);
    public OperationClaim getClaimByName(String name);
}
