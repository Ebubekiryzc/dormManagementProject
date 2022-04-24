package tr.edu.duzce.mf.bm.core.dataAccess.concretes;

import tr.edu.duzce.mf.bm.core.dataAccess.abstracts.UserOperationClaimDao;
import tr.edu.duzce.mf.bm.core.entities.concrete.UserOperationClaim;

public class JDBCUserOperationClaimDao extends BaseDaoJDBCRepository<UserOperationClaim> implements UserOperationClaimDao {

    public JDBCUserOperationClaimDao() {
        super(UserOperationClaim.class);
    }

}
